
let errors = {
    "INVALID_PATH" : "The entered path is incorrect.",
    "INVALID_QUERY": "Sent query has been rejected by the server.",
    "INVALID_ISO"  : "You have entered an invalid ISO query."
}

function getError(prompt) {
    return errors[prompt]
}

function validateObject(object, entries){
    // returns 1 if valid, 0 otherwise
    for(let entry of entries){
        if(!object[entry]) return 0;
    }
    return 1;
}

module.exports = {
    getError,
    validateObject
}
