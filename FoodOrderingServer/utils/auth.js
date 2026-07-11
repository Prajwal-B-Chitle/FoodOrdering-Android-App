const jwt = require('jsonwebtoken')
const config = require('./config')
const result = require('./result')

function authUser(req, res, next) {
    const path = req.url
    if (path == '/user/signup' || path == '/user/signin' || path == '/food/menu')
        next()
    else {
        const token = req.headers.token
        if (!token)
            res.send(result.errorResult('Token is Missing'))
        else {
            try {
                const payload = jwt.verify(token, config.SECRET)
                req.headers.uid = payload.uid
                next()
            } catch (error) {
                res.send(result.errorResult('Invalid Token'))
            }
        }
    }
}

module.exports = authUser