module.exports = async function createExam(res, body, util){
    /*
        lbz: string
    */
        let objectType = [
            "lbz"
        ]
        if(!util.errorHandler.validateObject(body, objectType)){
            res.statusCode = 400;
            res.setHeader('Content-Type', 'application/json');
            let ResponseObject = {"message" : "Invalid body data!"};
            res.end(JSON.stringify(ResponseObject));
            return res;
        }

        let result = await util.dbApi.readExamsByLbz(body.lbz);
        console.log('result: ' + result)

        if(result.length > 0){
            res.statusCode = 200;
            res.setHeader('Content-Type', 'application/json');
            let ResponseObject = filterResult(
                objectType,
                body,
                result
            );
            console.log(ResponseObject);
            res.end(JSON.stringify(ResponseObject));
            return res;
        }else{
            res.statusCode = 404;
            res.setHeader('Content-Type', 'application/json');
            let ResponseObject = {"message" : "No exams found!"};
            res.end(JSON.stringify(ResponseObject));
            return res;
        }
    
        return res;
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

function filterResult(byObject, object, toFilter) {
    let finalArray = [];

    for(let item of toFilter){
        let add = true;
        for(let key of byObject){
            if(object[key] != item.value[key]) add = false;
        }
        if(isToday(item.value.dateAndTime) && ["CEKA", "TRENUTNO"].includes(item.value.type)){
            if(add){
                finalArray.push(item);
            }
        }
    }
    
    return finalArray;
}
