import React, { useEffect, useState } from 'react'
import { FlatList, Image, StyleSheet, View } from 'react-native'
import { getFoodItems } from '../../services/foodservice'
import { Button, Text } from 'react-native-paper'
import FoodListCard from '../../components/FoodListCard'

function FoodListTabScreen() {
    const [foodItems, setFoodItems] = useState([])

    const loadFoodItems = async () => {
        try {
            const response = await getFoodItems();
            setFoodItems(response.data)
        } catch (error) {
            alert(error)
        }
    }

    useEffect(() => {
        loadFoodItems()
    }, [])

    return <FlatList
        data={foodItems}
        numColumns={2}
        keyExtractor={item => item.fid}
        renderItem={({ item }) => {
            return <FoodListCard
                fid={item.fid}
                name={item.name}
                description={item.description}
                price={item.price}
                image={item.image} />
        }}
    />
}



export default FoodListTabScreen
