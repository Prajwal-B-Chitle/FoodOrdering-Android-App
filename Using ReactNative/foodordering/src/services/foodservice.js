import axios from "axios";
import config from "../utils/config";

export async function getFoodItems() {
    const url = config.BASE_URL + '/food/menu'
    try {
        const response = await axios.get(url)
        return response.data
    } catch (error) {
        console.log(error)
    }
}

