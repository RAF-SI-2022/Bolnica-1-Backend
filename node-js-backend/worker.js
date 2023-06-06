const http = require('http');
const process = require('process');
const config = require('./config.json');
const covidApi = require('./lib/covid');
const errorHandler = require('./lib/errorHandler');
const validator = require('./lib/validator');

let instance = parseInt(process.argv[2])
let port = parseInt(config.localPort) + instance;

console.log(`Scrapper instance : ${instance}`);

const server = http.createServer((req, res) => {
    let { method, url } = req;
    let getHandler = ( method, path) => {
        const fs = require('fs');
        const paths = require('path');

        if(url.endsWith('/')) url = url.substring(0, url.length-1); // make sure that random '/' at the doesn't break pathing
        const filePath = './paths/' + method + url + ".js";

        console.log(filePath);

        if (fs.existsSync(paths.resolve(filePath))) {
            const stats = fs.statSync(paths.resolve(filePath));
            if (stats.isFile()) {
                loadScriptWithoutCache(filePath)(res);
                return res;
            } else if (stats.isDirectory()) {
                // Load a script with input?
            }
        } else {
            res.statusCode = 404;
            res.setHeader('Content-Type', 'text/plain');
            res.end('404 Not Found!');
        }

        return res;
    }

    getHandler(method, url);

});

server.listen(port, () => {
  console.log(`Server running on port ${port}`);
});


function loadScriptWithoutCache(scriptPath) {
  delete require.cache[require.resolve(scriptPath)];
  return require(scriptPath);
}