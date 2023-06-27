module.exports = async function createExam(res, body, util){
    /*
        today: Date
    */
    
        let objectType = [
            "today"
        ]
        if(!util.errorHandler.validateObject(body, objectType)){
            res.statusCode = 400;
            res.setHeader('Content-Type', 'application/json');
            let ResponseObject = {"message" : "Invalid body data!"};
            res.end(JSON.stringify(ResponseObject));
            return res;
        }

        let result = await util.dbApi.readAll("summaries");

        if(result.length > 0){
            res.statusCode = 200;
            res.setHeader('Content-Type', 'application/json');
            let ResponseObject = filterResult(
                body,
                result
            );
            let formattedResult = formatResult(ResponseObject);
            res.end(JSON.stringify(formattedResult));
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

function areDatesOnSameDay(date1, date2) {
    const sameYear = date1.getFullYear() === date2.getFullYear();
    const sameMonth = date1.getMonth() === date2.getMonth();
    const sameDay = date1.getDate() === date2.getDate();
  
    return sameYear && sameMonth && sameDay;
}  

function filterResult(body, toFilter) {
    let finalArray = [];

    for(let item of toFilter){
        let add = true;
        if(areDatesOnSameDay(item.value.dateAndTime, body.today)){
            if(add){
                finalArray.push(item);
            }
        }
    }

    return finalArray;
}


function formatResult(filterData) {
    let formattedResult = {
        numberOfTestedPatients : 0,
        numberOfHospitalizedPatients : 0,
        numberOfPositivePatients : 0,
        numberOfCuredPatients : 0,
        numberOfPatientsOnRespirator : 0,
        numberOfDeadPatients : 0
    }
    for(let exam of filterData){
        if(exam.value.saturation < 95) {
            formattedResult.numberOfPatientsOnRespirator = formattedResult.numberOfPatientsOnRespirator + 1;
            formattedResult.numberOfHospitalizedPatients = formattedResult.numberOfHospitalizedPatients + 1;
        }
        if(exam.value.duration > 14) {
            formattedResult.numberOfDeadPatients = formattedResult.numberOfDeadPatients + 1;
        }
        if(exam.value.duration <= 14) {
            formattedResult.numberOfCuredPatients = formattedResult.numberOfCuredPatients + 1;
        }
        formattedResult.numberOfTestedPatients = formattedResult.numberOfTestedPatients + 1;
    }

    return formattedResult;
}