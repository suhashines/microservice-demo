const customerDb = db.getSiblingDB("customer");

customerDb.createUser({
    user: "customer_user",
    pwd: "customer_pass",
    roles: [{ role: "readWrite", db: "customer" }]
});

const notificationDb = db.getSiblingDB("notification");

notificationDb.createUser({
    user: "notification_user",
    pwd: "notification_pass",
    roles: [{ role: "readWrite", db: "notification" }]
});
