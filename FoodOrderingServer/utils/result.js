function createResult(data, error) {
    if (data)
        return successResult(data)
    else
        return errorResult(error)
}

function successResult(data) {
    return { status: "success", data: data }
}

function errorResult(error) {
    return { status: "error", error: error }
}

module.exports = { createResult, successResult, errorResult }