const http = require('http');
const process = require('process');
const config = require('./config.json');
const mongoApi = require('./lib/mongoApi');

const covidApi = require('./lib/covid');
const validator = require('./lib/validator');
const errorHandler = require('./lib/errorHandler');

let instance = parseInt(process.argv[2])
let port = parseInt(config.localPort) + instance;

console.log(`Scrapper instance : ${instance}`);

let util = {
    "errorHandler" : errorHandler,
    "dbApi" : mongoApi,
    "config" : config
}

const server = http.createServer( async (req, res) => {
    let { method, url } = req;
    let body;
    try{
        body = JSON.parse(await getBody(req));
    }catch(_){
        body = {};
    }

    let getHandler = async ( method, path) => {
        const fs = require('fs');
        const paths = require('path');

        if(url.endsWith('/')) url = url.substring(0, url.length-1); // make sure that random '/' at the doesn't break pathing
        const filePath = './paths/' + method + url + ".js";

        if (fs.existsSync(paths.resolve(filePath))) {
            const stats = fs.statSync(paths.resolve(filePath));
            if (stats.isFile()) {
                await loadScriptWithoutCache(filePath)(res, body, util);
            } else if (stats.isDirectory()) {
                // Load a script with input?
            }
        } else {
            res.statusCode = 422;
            res.setHeader('Content-Type', 'text/plain');
            res.end('Invalid page request!');
        }
    }

    await getHandler(method, url);

});

server.listen(port, () => {
  console.log(`Server running on port ${port}`);
});


function loadScriptWithoutCache(scriptPath) {
  delete require.cache[require.resolve(scriptPath)];
  return require(scriptPath);
}

function getBody(request) {
  return new Promise((resolve) => {
    const bodyParts = [];
    let body;
    request.on('data', (chunk) => {
      bodyParts.push(chunk);
    }).on('end', () => {
      body = Buffer.concat(bodyParts).toString();
      resolve(body)
    });
  });
}