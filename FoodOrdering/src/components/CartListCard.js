import React from 'react'
import { Image, StyleSheet, View } from 'react-native'
import { Button, Text } from 'react-native-paper'
import config from '../utils/config'
import { useDispatch } from 'react-redux'
import { DecrementAction, IncrementAction } from '../slices/CartSlice'

function CartListCard({ fid, name, price, qty, image }) {
    const dispatch = useDispatch()
    return (
        <View style={styles.card}>
            <Image source={{ uri: config.BASE_URL + '/foodimage/' + image }} style={styles.image} />
            <View>
                <Text variant="titleLarge">{name}</Text>
                <Text variant="titleLarge">Rs. {price * qty}</Text>
                <View style={styles.qtyContainer}>
                    <Button onPress={() => dispatch(IncrementAction(fid))} compact={true} mode='outlined' style={[styles.btn, { marginRight: 10 }]}>+</Button>
                    <Text variant="titleLarge">{qty}</Text>
                    <Button onPress={() => dispatch(DecrementAction(fid))} compact={true} mode='outlined' style={[styles.btn, { marginLeft: 10 }]}>-</Button>
                </View>
            </View>
        </View>
    )
}

const styles = StyleSheet.create({
    card: {
        padding: 8,
        flexDirection: 'row',
        borderBottomWidth: 1
    },
    image: { width: 100, height: 100, marginRight: 10 },
    btn: { width: 40, height: 40 },
    qtyContainer: { flexDirection: 'row', alignItems: 'center' }

})

export default CartListCard
