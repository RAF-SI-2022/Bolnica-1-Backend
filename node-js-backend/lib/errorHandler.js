
let errors = {
    "INVALID_PATH" : "The entered path is incorrect.",
    "INVALID_QUERY": "Sent query has been rejected by the server.",
    "INVALID_ISO"  : "You have entered an invalid ISO query."
}

function getError(prompt) {
    return errors[prompt]
}

module.exports = {
    getError
}
