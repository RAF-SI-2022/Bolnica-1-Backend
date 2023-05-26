const http = require('http');
const { createServer } = require('http');
const config = require('./config.json');

const handleRequest = (req, res) => {

    let proxyTo = getAvailableLoad();

    const proxyReq = http.request(proxyTo + getPath(req), proxyRes => {
        res.writeHead(proxyRes.statusCode, proxyRes.headers);
        proxyRes.pipe(res);
    });

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
    portArray = Array.from({ length : config['instance-count'] }, (_, i) => "http://localhost:" + (config.localPort + i) )
}
function getAvailableLoad() {
    let port = portArray.shift();
    portArray.push(port)

    return port;
}

function getPath(req) {
    return req.url;
}
initializeLoad()