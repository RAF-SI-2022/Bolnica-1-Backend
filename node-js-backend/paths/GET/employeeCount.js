module.exports = function employeeCount(res){
    let eCount = 0;

    res.statusCode = 200;
    res.setHeader('Content-Type', 'json');
    res.end(`{
        "employeeCount" : ${eCount}
    }`);

    return res;
}