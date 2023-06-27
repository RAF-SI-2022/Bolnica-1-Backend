const { MongoClient, ServerApiVersion } = require('mongodb');
// const uri = require('fs').readFileSync(require('path').resolve("./mongo.key"), 'utf-8');
require('dotenv').config();
console.log('mongo uri: ' + process.env.MONGO_URI);
const client = new MongoClient(process.env.MONGO_URI, { useNewUrlParser: true, useUnifiedTopology: true, serverApi: ServerApiVersion.v1 });
let db;
let exams;

client.connect().then(_ =>{
  console.log("Connected to the database!");
  db = client.db("covid");
  exams = db.collection('exams');
}).catch(_=>{
  console.log("Error connecting to the database :");
  console.log(_);
})


async function write(key, value, collection) {
  const result = await db.collection(collection).updateOne(
    { key },
    { $set: { value } },
    { upsert: true }
  );
  return result;
}

async function read(key, collection) {
  const result = await db.collection(collection).findOne(
    { key } // find key
  );
  return result.value;
}

async function readExamsByLbz(lbz){
  const result = await exams.find(
    { "value.lbz": {$in:[lbz]}}
  );
  return result.toArray();
}

async function readAll(collection) {
  const result = await db.collection(collection).find({});
  return result.toArray().then( items =>{
    return items;
  })
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
  write,
  read,
  readAll,
  readExamsByLbz
}
