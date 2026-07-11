const express = require('express')
const cryptojs = require('crypto-js')
const jwt = require('jsonwebtoken')

const pool = require('../db/pool')
const result = require('../utils/result')
const config = require('../utils/config')

const router = express.Router()

router.post('/signin', async (req, res) => {
    const { email, password } = req.body
    const hashedPassword = cryptojs.SHA256(password)
    const sql = `SELECT * FROM user WHERE email = ? AND password = ?`
    try {
        const data = await pool.query(sql, [email, hashedPassword])
        if (data[0].length == 0)
            res.send(result.errorResult("Invalid email or password"))
        else {
            const user = data[0][0];
            const payload = {
                uid: user.uid
            }
            const token = jwt.sign(payload, config.SECRET);
            const userData = {
                token,
                name: user.name,
                email: user.email,
                mobile: user.mobile
            }
            res.send(result.createResult(userData))
        }
    } catch (error) {
        res.send(result.errorResult(error))
    }
})

router.post('/signup', async (req, res) => {
    const { name, email, password, mobile } = req.body
    const sql = `INSERT INTO user(name,email,password,mobile) VALUES(?,?,?,?)`
    try {
        const hashedPassword = cryptojs.SHA256(password)
        const [data] = await pool.query(sql, [name, email, hashedPassword, mobile])
        res.send(result.successResult(data))
    } catch (error) {
        res.send(result.errorResult(error))
    }
})

router.get('/', async (req, res) => {
    const sql = `SELECT * FROM user WHERE uid = ?`
    try {
        const data = await pool.query(sql, [req.headers.uid])
        res.send(result.successResult(data[0][0]))
    } catch (error) {
        res.send(result.errorResult(error))
    }
})


router.delete('/', async (req, res) => {
    const sql = `DELETE FROM user WHERE uid = ?`
    try {
        const data = await pool.query(sql, [req.headers.uid])
        res.send(result.successResult(data[0]))
    } catch (error) {
        res.send(result.errorResult(error))
    }
})

module.exports = router