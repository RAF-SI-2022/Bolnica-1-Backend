module.exports = function getAllStats(res){
    res.statusCode = 500;
    res.setHeader('Content-Type', 'application/json');
    let ResponseObject = {"message" : "Not Implemented!"};
    res.end(JSON.stringify(ResponseObject));
    return res;
}