#!/usr/bin/env bash
set -euo pipefail

# Directories
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
LOG_DIR="$SCRIPT_DIR/logs"
RUN_DIR="$SCRIPT_DIR/run"
mkdir -p "$LOG_DIR" "$RUN_DIR"

wait_for_port_open() {
  local host="$1" port="$2" timeout_seconds="${3:-60}" name="${4:-service}"
  local start_ts end_ts
  start_ts=$(date +%s)
  end_ts=$((start_ts + timeout_seconds))
  printf "Waiting for %s on %s:%s to be ready...\n" "$name" "$host" "$port"
  while true; do
    if (exec 3<>"/dev/tcp/${host}/${port}" 2>/dev/null); then
      exec 3>&-
      printf "%s is up on %s:%s\n" "$name" "$host" "$port"
      break
    fi
    if [ "$(date +%s)" -ge "$end_ts" ]; then
      printf "Timed out waiting for %s on %s:%s after %ss\n" "$name" "$host" "$port" "$timeout_seconds" >&2
      exit 1
    fi
    sleep 1
  done
}

start_service() {
  local name="$1" service_dir="$2" port="$3"
  local log_out="$LOG_DIR/${name}.out" log_err="$LOG_DIR/${name}.err" pid_file="$RUN_DIR/${name}.pid"

  if [ -f "$pid_file" ] && kill -0 "$(cat "$pid_file")" 2>/dev/null; then
    printf "%s appears to be already running with PID %s\n" "$name" "$(cat "$pid_file")"
    return 0
  fi

  printf "Starting %s...\n" "$name"
  (cd "$service_dir" && nohup ./mvnw -q -Dspring-boot.run.fork=false spring-boot:run >"$log_out" 2>"$log_err" & echo $! >"$pid_file")

  # Give the process a moment to initialize before checking port
  sleep 2
  wait_for_port_open "localhost" "$port" 90 "$name"
}

# Ports inferred from configuration
CONFIG_PORT=8888
DISCOVERY_PORT=8761
CUSTOMER_PORT=8090

# Paths
CONFIG_DIR="$SCRIPT_DIR/services/config-server"
DISCOVERY_DIR="$SCRIPT_DIR/services/discovery"
CUSTOMER_DIR="$SCRIPT_DIR/services/customer"

# Start in order
start_service "config-server" "$CONFIG_DIR" "$CONFIG_PORT"
start_service "discovery-service" "$DISCOVERY_DIR" "$DISCOVERY_PORT"
start_service "customer-service" "$CUSTOMER_DIR" "$CUSTOMER_PORT"

printf "All services started. Logs in %s, PIDs in %s\n" "$LOG_DIR" "$RUN_DIR" 