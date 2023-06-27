db.createUser(
{
    user: "root",
    pwd: "admin",
    roles: [
      { role: "readWrite", db: "covid" }
    ]
})
