import axios from "axios"
import config from "../utils/config"

export async function placeOrder(total_amount, foodItems, token) {
    const url = config.BASE_URL + '/order'
    const headers = { token }
    const body = { total_amount, foodItems }
    try {
        const response = await axios.post(url, body, { headers })
        return response.data
    } catch (error) {
        console.log(error)
    }
}

export async function getAllOrders(token) {
    const url = config.BASE_URL + '/order'
    const headers = { token }
    try {
        const response = await axios.get(url, { headers })
        return response.data
    } catch (error) {
        console.log(error)
    }

}