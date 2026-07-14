import React from 'react'
import { Image, StyleSheet, View } from 'react-native'
import { Button, Text } from 'react-native-paper'
import config from '../utils/config'
import { useDispatch } from 'react-redux'
import { AddToCartAction } from '../slices/CartSlice'



function FoodListCard({ fid, name, description, price, image }) {
    const dispatch = useDispatch()

    const handleAddToCartPress = () => {
        dispatch(AddToCartAction({ fid, name, description, price, image }))
    }
    return (
        <View style={styles.card}>
            <Image source={{ uri: config.BASE_URL + '/foodimage/' + image }} style={{ width: '100%', height: 100 }} />
            <Text variant="headlineSmall">{name}</Text>
            <Text variant="titleSmall" style={{ height: 50 }}>{description.slice(0, 50) + ' ...more'}</Text>
            <Text variant="titleMedium">Rs. {price}</Text>
            <Button mode='contained' textColor='white' style={{ margin: 5 }} onPress={handleAddToCartPress} >Add To Cart</Button>
        </View>
    )
}

const styles = StyleSheet.create({
    card: {
        marginTop: 5,
        padding: 5,
        width: '50%',
        borderWidth: 1,
        borderRadius: 10
    }
})

export default FoodListCard
