#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
RUN_DIR="$SCRIPT_DIR/run"

wait_for_port_closed() {
  local host="$1" port="$2" timeout_seconds="${3:-60}" name="${4:-service}"
  local start_ts end_ts
  start_ts=$(date +%s)
  end_ts=$((start_ts + timeout_seconds))
  printf "Waiting for %s on %s:%s to stop...\n" "$name" "$host" "$port"
  while true; do
    if (exec 3<>"/dev/tcp/${host}/${port}" 2>/dev/null); then
      exec 3>&-
      if [ "$(date +%s)" -ge "$end_ts" ]; then
        printf "Timed out waiting for %s port %s to close after %ss\n" "$name" "$port" "$timeout_seconds" >&2
        return 1
      fi
      sleep 1
    else
      printf "%s has stopped listening on %s:%s\n" "$name" "$host" "$port"
      break
    fi
  done
}

find_pids_for_port() {
  local port="$1"
  local pids=""
  if command -v lsof >/dev/null 2>&1; then
    pids=$(lsof -ti tcp:"$port" 2>/dev/null || true)
  fi
  if [ -z "$pids" ] && command -v fuser >/dev/null 2>&1; then
    # fuser outputs like: 8090/tcp: 1234 5678
    pids=$(fuser -n tcp "$port" 2>/dev/null | awk '{for (i=2;i<=NF;i++) print $i}')
  fi
  if [ -z "$pids" ] && command -v ss >/dev/null 2>&1; then
    # Parse ss -ltnp to extract pid from users:(...) field
    pids=$(ss -ltnp 2>/dev/null | awk -v p=":$port" '$4 ~ p {print $NF}' | sed -n 's/.*pid=\([0-9]\+\).*/\1/p' | sort -u)
  fi
  echo "$pids"
}

kill_listeners_on_port() {
  local port="$1" name="$2" force="${3:-false}"
  local pids
  pids=$(find_pids_for_port "$port")
  if [ -z "$pids" ]; then
    return 0
  fi
  for pid in $pids; do
    if kill -0 "$pid" 2>/dev/null; then
      if [ "$force" = true ]; then
        kill -9 "$pid" 2>/dev/null || true
      else
        kill "$pid" 2>/dev/null || true
      fi
    fi
  done
}

stop_service() {
  local name="$1" port="$2"
  local pid_file="$RUN_DIR/${name}.pid"

  if [ -f "$pid_file" ]; then
    local pid
    pid="$(cat "$pid_file")"
    if kill -0 "$pid" 2>/dev/null; then
      printf "Stopping %s (PID %s)...\n" "$name" "$pid"
      kill "$pid" 2>/dev/null || true
      for i in $(seq 1 20); do
        if ! kill -0 "$pid" 2>/dev/null; then
          break
        fi
        sleep 1
      done
      if kill -0 "$pid" 2>/dev/null; then
        printf "%s did not stop gracefully; forcing...\n" "$name"
        kill -9 "$pid" 2>/dev/null || true
      fi
    else
      printf "%s not running (stale PID %s).\n" "$name" "$pid"
    fi
    rm -f "$pid_file"
  else
    printf "No PID file for %s; will attempt port-based shutdown.\n" "$name"
  fi

  # If port still open, kill listeners found on the port (graceful then force)
  if (exec 3<>"/dev/tcp/localhost/${port}" 2>/dev/null); then
    exec 3>&-
    printf "%s still listening on port %s; attempting graceful kill by port...\n" "$name" "$port"
    kill_listeners_on_port "$port" "$name" false
    sleep 2
  fi

  if (exec 3<>"/dev/tcp/localhost/${port}" 2>/dev/null); then
    exec 3>&-
    printf "%s still listening on port %s; forcing kill by port...\n" "$name" "$port"
    kill_listeners_on_port "$port" "$name" true
  fi

  wait_for_port_closed "localhost" "$port" 60 "$name" || true
}

CONFIG_PORT=8888
DISCOVERY_PORT=8761
CUSTOMER_PORT=8090

# Stop in reverse order
stop_service "customer-service" "$CUSTOMER_PORT"
stop_service "discovery-service" "$DISCOVERY_PORT"
stop_service "config-server" "$CONFIG_PORT"

printf "All services stopped.\n" 