const express = require('express')

const pool = require('../db/pool')
const result = require('../utils/result')

const router = express.Router()

router.post("/", async (req, res) => {
    const { total_amount, foodItems } = req.body

    const uid = req.headers.uid
    const sql1 = `INSERT INTO orders(uid,total_amount) VALUES(?,?)`
    const sql2 = `INSERT INTO orderdetails(oid,fid,quantity) VALUES(?,?,?)`
    try {
        const [data] = await pool.query(sql1, [uid, total_amount])
        const oid = data.insertId
        for (const f of foodItems)
            await pool.query(sql2, [oid, f.fid, f.qty])
        res.send(result.successResult(data))
    } catch (error) {
        res.send(result.errorResult(error))
    }
    res.send('ok')
})

router.get('/', async (req, res) => {
    const sql = `SELECT * FROM orders WHERE uid = ?`
    try {
        const [data] = await pool.query(sql, [req.headers.uid])
        res.send(result.successResult(data))
    } catch (error) {
        res.send(result.errorResult(error))
    }
})

module.exports = router