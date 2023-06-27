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
        const connectOptions = {
            host: 'localhost', // Replace with the hostname or IP address of the ActiveMQ broker
            port: 61616,       // Replace with the port number of the ActiveMQ broker
            connectHeaders: {
              host: '/',
              login: '',        // No login is required as per the provided configuration
              passcode: '',     // No password is required as per the provided configuration
              'heart-beat': '5000,5000' // Optional heart-beat configuration
            }
        };
    
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


            /// STOMP ACTIVE MQ
            stompit.connect(connectOptions, (error, client) => {
                if (error) {
                  console.error('Connection error:', error.message);
                  return;
                }
              
                // Destination where you want to send the message
                const destination = '/queue/<queue-name>'; // Replace with your desired queue name
              
                // Create a STOMP frame to send the message
                const frame = client.send({ destination });
              
                // Convert the object to a JSON string and set it as the message body
                const messageBody = JSON.stringify(body);
                frame.write(messageBody);

                // Send the message
                frame.end();
              
                console.log('Message sent successfully');
              
                // Disconnect the client and close the connection
                client.disconnect();
              });

            return res;
        }else{
            res.statusCode = 500;
            res.setHeader('Content-Type', 'application/json');
            let ResponseObject = {"message" : "Internal Server Error!"};
            res.end(JSON.stringify(ResponseObject));
            return res;
        }
    }