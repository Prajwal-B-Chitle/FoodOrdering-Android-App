import React, { useEffect, useState } from 'react'
import { FlatList, Image, StyleSheet, View } from 'react-native'
import { Button, Text } from 'react-native-paper'
import { useDispatch, useSelector } from 'react-redux'
import config from '../../utils/config'
import CartListCard from '../../components/CartListCard'
import { placeOrder } from '../../services/orderservice'
import AsyncStorage from '@react-native-async-storage/async-storage'
import Toast from 'react-native-toast-message'
import { ClearCartAction } from '../../slices/CartSlice'

function CartTabScreen() {
    const dispatch = useDispatch()
    const cartItems = useSelector(store => store.CartSlice.cartItems)
    const [quantity, setQuantity] = useState(0)
    const [totalAmount, setTotalAmount] = useState(0)

    const handlePlaceOrderPress = async () => {
        try {
            const token = await AsyncStorage.getItem(config.KEY_TOKEN);
            const response = await placeOrder(totalAmount, cartItems, token)
            if (response.status == 'success') {
                Toast.show({
                    text1: 'order placed !!!',
                    type: 'info'
                })
                dispatch(ClearCartAction())
            }
        } catch (error) {
            alert(error)
        }
    }

    const calculateBillSummary = () => {
        let qty = 0, total = 0
        for (const f of cartItems) {
            qty += f.qty
            total += (f.price * f.qty)
        }
        setQuantity(qty)
        setTotalAmount(total)
    }

    useEffect(() => {
        calculateBillSummary()
    }, [cartItems])

    return (
        <View style={{ flex: 1 }}>
            <FlatList
                data={cartItems}
                keyExtractor={item => item.fid}
                renderItem={({ item }) => <CartListCard fid={item.fid} name={item.name} price={item.price} qty={item.qty} image={item.image} />}
            />

            {cartItems.length != 0 && <View style={styles.billSummary}>
                <Text variant="headlineMedium" style={{ textAlign: 'center' }}>Bill Summary</Text>
                <Text variant="titleLarge">#items : {cartItems.length}</Text>
                <Text variant="titleLarge">#qty : {quantity}</Text>
                <Text variant="titleLarge">#total : {totalAmount} </Text>
                <Button onPress={handlePlaceOrderPress} mode='contained' buttonColor='#5871cc' style={{ marginTop: 8 }}>Place Order</Button>
            </View>
            }
        </View>
    )
}

const styles = StyleSheet.create({
    billSummary: {
        borderWidth: 1,
        borderRadius: 5,
        padding: 8,
        margin: 5
    }
})


export default CartTabScreen
