module.exports = async function createExam(res, body, util){
/*
    dateAndTime: Date, // ovo je timestamp
    patientArrival: PatientArrival,
    type: CovidExaminationType,
    lbz: string,
    lbp: string
*/

    let objectType = [
        "dateAndTime",
        "patientArrival",
        "type",
        "lbz",
        "lbp"
    ]
    if(!util.errorHandler.validateObject(body, objectType)){
        res.statusCode = 400;
        res.setHeader('Content-Type', 'application/json');
        let ResponseObject = {"message" : "Invalid body data!"};
        res.end(JSON.stringify(ResponseObject));
        return res;
    }

    let result = await util.dbApi.write(body['lbp'], body, "exams");
    if(result.acknowledged){
        res.statusCode = 200;
        res.setHeader('Content-Type', 'application/json');
        let ResponseObject = {"message" : result.modifiedCount?"Appointment Scheduled Again!":"Appointment Scheduled!"};
        res.end(JSON.stringify(ResponseObject));
        return res;
    }else{
        res.statusCode = 500;
        res.setHeader('Content-Type', 'application/json');
        let ResponseObject = {"message" : "Internal Server Error!"};
        res.end(JSON.stringify(ResponseObject));
        return res;
    }

    return res;
}

