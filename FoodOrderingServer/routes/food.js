const fs = require('fs/promises')
const express = require('express')
const multer = require('multer')

const pool = require('../db/pool')
const result = require('../utils/result')

const router = express.Router()
const upload = multer({ dest: 'images/' })

router.post('/', upload.single('image'), async (req, res) => {
    const newPath = req.file.destination + req.file.originalname
    await fs.rename(req.file.path, newPath)
    const { name, description, price } = req.body
    const sql = `INSERT INTO food(name,description,price,image) VALUES(?,?,?,?)`
    try {
        const [data] = await pool.query(sql, [name, description, price, req.file.originalname])
        res.send(result.successResult(data))
    } catch (error) {
        res.send(result.errorResult(error))
    }

    res.send('OK')
})

router.get('/menu', async (req, res) => {
    const sql = `SELECT * FROM food`
    try {
        const data = await pool.query(sql)
        res.send(result.successResult(data[0]))
    } catch (error) {
        res.send(result.errorResult(error))
    }
})

module.exports = router