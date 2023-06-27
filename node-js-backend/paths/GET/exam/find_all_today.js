module.exports = async function createExam(res, body, util){
    /*
        page: number
        size: number
    */
    
        let objectType = [
            "page",
            "size"
        ]
        if(!util.errorHandler.validateObject(body, objectType)){
            res.statusCode = 400;
            res.setHeader('Content-Type', 'application/json');
            let ResponseObject = {"message" : "Invalid body data!"};
            res.end(JSON.stringify(ResponseObject));
            return res;
        }

        let result = await util.dbApi.readAll("exams");


        if(result.length > 0){
            res.statusCode = 200;
            res.setHeader('Content-Type', 'application/json');
            let ResponseObject = filterResult(
                body,
                result
            );
            res.end(JSON.stringify(ResponseObject));
            return res;
        }else{
            res.statusCode = 404;
            res.setHeader('Content-Type', 'application/json');
            let ResponseObject = {"message" : "No exams found!"};
            res.end(JSON.stringify(ResponseObject));
            return res;
        }
    }
    
    
function isToday(timestamp) {
    const date = new Date(timestamp);
    const today = new Date();
    
    // Extract the year, month, and day from the timestamp and today's date
    const timestampYear = date.getFullYear();
    const timestampMonth = date.getMonth();
    const timestampDay = date.getDate();
    
    const todayYear = today.getFullYear();
    const todayMonth = today.getMonth();
    const todayDay = today.getDate();
    
    // Compare the year, month, and day
    return timestampYear === todayYear && timestampMonth === todayMonth && timestampDay === todayDay;
}

function filterResult(pagination, toFilter) {
    let finalArray = [];

    for(let item of toFilter){
        let add = true;
        if(isToday(item.value.dateAndTime) && ["CEKA"].includes(item.value.type)){
            if(add){
                finalArray.push(item);
            }
        }
    }

    finalArray = finalArray.slice(pagination.size * ( pagination.page - 1 ), pagination.size * ( pagination.page - 1 ) + pagination.size );
    return finalArray;
}