module.exports = function createUser(res){
    let cCount = 1;

    res.statusCode = 200;
    res.setHeader('Content-Type', 'json');
    res.end(`{
        "newCount" : ${cCount}
    }`);

    return res;
}