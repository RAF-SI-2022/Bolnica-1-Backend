const http = require('http');

module.exports = async function createExam(res, body, util){
    /*
        type: PrescriptionType.LABORATORIJA,
        doctorLbz: doctorLbz,
        departmentFromId: departmentFromId,
        departmentToId: departmentToId,
        lbp: lbp,
        creationDateTime: new Date(), //ovde je timestamp
        status: PrescriptionStatus.NEREALIZOVAN,
        comment: comment,
        prescriptionAnalysisDtos: prescriptionAnalysisDtos
    */
    
        let objectType = [
            "type",
            "doctorLbz",
            "departmentFromId",
            "departmentToId",
            "lbp",
            "creationDateTime",
            "status",
            "comment",
            "prescriptionAnalysisDtos"
        ]
        if(!util.errorHandler.validateObject(body, objectType)){
            res.statusCode = 400;
            res.setHeader('Content-Type', 'application/json');
            let ResponseObject = {"message" : "Invalid body data!"};
            res.end(JSON.stringify(ResponseObject));
            return res;
        }

        const requestBody = JSON.stringify(body);
        
        // Request options <---- ovo popunite vi, ne znam sta su vam host i port i sta smatrate pod uput
        const options = {
          hostname: 'your-server.com',
          port: 80,
          path: '/your-endpoint',
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Content-Length': Buffer.byteLength(requestBody)
          }
        };
        
        const request = http.request(options, (response) => {

          let responseData = '';
        
          response.on('data', (chunk) => {

            responseData += chunk;

          });
        
          response.on('end', () => {

            console.log('Response:', responseData);

          });

        });

        request.on('error', (error) => {

          console.error('Error:', error);

        });

        request.write(requestBody);

        request.end();
        
        return res;
    }
    
    