const { MongoClient, ServerApiVersion } = require('mongodb');
const uri = require('fs').readFileSync(require('path').resolve("./mongo.key"), 'utf-8');
const client = new MongoClient(uri, { useNewUrlParser: true, useUnifiedTopology: true, serverApi: ServerApiVersion.v1 });
const allowedCacheHold = minutesToMiliseconds(5);
let collection;

client.connect().then(_ =>{
  console.log("Connected to the database!");
  collection = client.db("cache").collection("data")
}).catch(_=>{
  console.log("Error connecting to the database :");
  console.log(_);
})


async function setCache(key, value) {
  const now = new Date();
  const expirationDate = new Date(now.getTime() + allowedCacheHold); // expiration time in seconds
  const result = await collection.updateOne(
    { key },
    { $set: { value, lastAccessed: now, expirationDate } },
    { upsert: true }
  );
  return result;
}

async function getCache(key) {
  const result = await collection.findOneAndUpdate(
    { key, expirationDate: { $gt: new Date() } }, // only return if not expired
    { $set: { lastAccessed: new Date() } },
    { returnOriginal: false } // return updated document
  );
  return result.value;
}

function hoursToMiliseconds(hours) {
  return minutesToMiliseconds(hours * 60)
}

function minutesToMiliseconds(minutes) {
  return secondsToMiliseconds(minutes * 60);
}

function secondsToMiliseconds(seconds) {
  return seconds * 1000;
}



module.exports = {
  getCache,
  setCache
}