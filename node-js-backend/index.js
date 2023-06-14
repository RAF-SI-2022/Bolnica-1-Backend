const path = require('path');
const fs = require('fs');
const { argv } = require('process');
let config = require("./config.json");

let instanceCount = config['instance-count'];

if(argv[2])
    instanceCount = parseInt(argv[2])

    console.log(instanceCount);

console.log("Booting backend service...");
const spawn = require('child_process').spawn;

let filepath = path.join(__dirname, "ScrapperInstance" + '_(' + Date.now() + ')' + '.log');
let file = fs.createWriteStream(filepath, {highWaterMark: 1024*1024});


for(let x = 0; x< instanceCount; x++){

    const child = spawn(process.argv[0], ['worker.js', ''+x], {
        stdio: ['ignore'],
        detached: true
    });
    child.stdout.setEncoding('utf-8')
    child.stdout.on('data', function(data){
        file.write(data)
    })

    child.unref();


}

console.log("Booting load balancer...");
require("./balancer");


console.log("Start-up complete...");