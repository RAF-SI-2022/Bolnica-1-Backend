const http = require('http');
const { createServer } = require('http');
const config = require('./config.json');

const handleRequest = async (req, res) => {

    let proxyTo = getAvailableLoad();
    let body;
    try{
        body = JSON.parse(await getBody(req));
    }catch(_){
        body = {};
    }
    const options = {
      hostname: proxyTo.split(":")[0],
      port: proxyTo.split(":")[1],
      path: getPath(req),
      method: req.method,
      headers: req.headers
    };

    const proxyReq = http.request(options, proxyRes => {
        res.writeHead(proxyRes.statusCode, proxyRes.headers);
        proxyRes.pipe(res);
    });
    
    proxyReq.write( JSON.stringify(body) );

    req.pipe(proxyReq);
};

const server = createServer(handleRequest);
const port = config.masterPort;

server.listen(port, () => {
  console.log(`Load balancer running on port ${port}`);
});

let startingPort;
let portArray;
function initializeLoad(){
    startingPort = config.localPort;
    portArray = Array.from({ length : config['instance-count'] }, (_, i) => "localhost:" + (config.localPort + i) )
}
function getAvailableLoad() {
    let port = portArray.shift();
    portArray.push(port)

    return port;
}

function getPath(req) {
    return req.url;
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

initializeLoad()