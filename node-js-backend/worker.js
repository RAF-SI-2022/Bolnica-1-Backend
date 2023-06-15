const http = require('http');
const process = require('process');
const config = require('./config.json');
const mongoApi = require('./lib/mongoApi');

const covidApi = require('./lib/covid');
const validator = require('./lib/validator');
const errorHandler = require('./lib/errorHandler');

let instance = parseInt(process.argv[2])
let port = 9000;

console.log(`Scrapper instance : ${instance}`);

let util = {
    "errorHandler" : errorHandler,
    "dbApi" : mongoApi,
    "config" : config
}

const server = http.createServer( async (req, res) => {
    let { method, url } = req;
    let body = {};
    
    res.setHeader('Access-Control-Allow-Origin', '*'); // Replace * with your desired origin or specify multiple origins
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS');
    res.setHeader('Access-Control-Allow-Headers', 'Content-Type, Authorization');

    if (req.method === 'OPTIONS') {
        // Respond to preflight request
        res.writeHead(200);
        res.end();
        return;
    }

    if(req.method == "POST"){
      try{
          body = JSON.parse(await getBody(req));
      }catch(_){
          body = {};
      }
    }else if(req.method == "GET"){
      body = getParams(url);
      url = url.split("?")[0];
    }

    let getHandler = async ( method, path) => {
        const fs = require('fs');
        const paths = require('path');

        if(url.endsWith('/')) url = url.substring(0, url.length-1); // make sure that random '/' at the doesn't break pathing
        url = url.substring(4, url.length-1); // removes /api
        
        console.log('url: ' + url);
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

function getParams(path) {
  let params = {}
  const qString = path.split('?')[1];

  if (qString) {
    const pairs = qString.split('&');
    for (let pair of pairs) {
      const [key, value] = pair.split('=');
      params[key] = value;
    }
  }

  return params;
}
