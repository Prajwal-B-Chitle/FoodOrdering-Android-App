import config from "../utils/config";
import axios from 'axios';

export async function loginUser(email, password) {
    const url = config.BASE_URL + '/user/signin'
    const body = { email, password }
    try {
        const response = await axios.post(url, body)
        return response.data
    } catch (error) {
        console.log(error)
    }
}

export async function registerUser(body) {
    const url = config.BASE_URL + '/user/signup'
    try {
        const response = await axios.post(url, body)
        return response.data
    } catch (error) {
        console.log(error)
    }
}

export async function getUser(token) {
    const url = config.BASE_URL + '/user'
    const headers = { token }
    try {
        const response = await axios.get(url, { headers })
        return response.data
    } catch (error) {
        console.log(error)
    }

}