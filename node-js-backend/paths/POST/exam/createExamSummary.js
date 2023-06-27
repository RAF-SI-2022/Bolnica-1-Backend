module.exports = async function createExam(res, body, util){
    /*
        examId: number,
        lbp: string,
        examDate: Date,
        lbz: string, // doktor koji je pregleda
        symptoms: string,
        duration: string,
        bodyTemperature: string,
        bloodPressure: string,
        saturation: string,
        lungCondition: string,
        therapy: string


        // posalji na pacijent
    */
    
        let objectType = [
            "examId",
            "lbp",
            "examDate",
            "lbz", 
            "symptoms",
            "duration",
            "bodyTemperature",
            "bloodPressure",
            "saturation",
            "lungCondition",
            "therapy"
        ]
        if(!util.errorHandler.validateObject(body, objectType)){
            res.statusCode = 400;
            res.setHeader('Content-Type', 'application/json');
            let ResponseObject = {"message" : "Invalid body data!"};
            res.end(JSON.stringify(ResponseObject));
            return res;
        }

        let result = await util.dbApi.write(body['examId'], body, "summaries");
        if(result.acknowledged){
            res.statusCode = 200;
            res.setHeader('Content-Type', 'application/json');
            let ResponseObject = {"message" : result.modifiedCount?"Summary Updated!":"Summary Created!"};
            res.end(JSON.stringify(ResponseObject));
            return res;
        }else{
            res.statusCode = 500;
            res.setHeader('Content-Type', 'application/json');
            let ResponseObject = {"message" : "Internal Server Error!"};
            res.end(JSON.stringify(ResponseObject));
            return res;
        }
    }