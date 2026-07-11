// depependeny modules imports
const express = require('express')

// user defined modules imports
const authUser = require('./utils/auth')
const userRouter = require('./routes/user')
const foodRouter = require('./routes/food')
const orderRouter = require('./routes/order')

const app = express()

// middlewares
app.use('/foodimage', express.static('images'))
app.use(express.json())
app.use(authUser) // authorization of the user
app.use('/user', userRouter)
app.use('/food', foodRouter)
app.use('/order', orderRouter)


app.listen(4000, '0.0.0.0', () => {
    console.log('server started on port 4000')
})