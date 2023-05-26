module.exports = function About(res){
    res.statusCode = 200;
    res.setHeader('Content-Type', 'text/plain');
    res.end('About Page!');

    return res;
}