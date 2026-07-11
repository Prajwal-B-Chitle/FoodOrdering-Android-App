import React, { useEffect, useState } from 'react'
import { FlatList, StyleSheet, View } from 'react-native'
import { getAllOrders } from '../../services/orderservice'
import AsyncStorage from '@react-native-async-storage/async-storage'
import config from '../../utils/config'
import { Text } from 'react-native-paper'

function OrdersTabScreen() {
    const [orderItems, setOrderItems] = useState([])
    const getOrders = async () => {
        try {
            const token = await AsyncStorage.getItem(config.KEY_TOKEN)
            const response = await getAllOrders(token)
            if (response.status == 'success') {
                setOrderItems(response.data)
            }
        } catch (error) {
            alert(error)
        }
    }

    useEffect(() => {
        getOrders()
    }, [])

    return <FlatList
        data={orderItems}
        keyExtractor={item => item.oid}
        renderItem={({ item }) => {
            return <View style={styles.container}>
                <Text variant="titleLarge">oid: {item.oid}</Text>
                <Text variant="titleLarge">OrderDate: {item.odate.split('T')[0]}</Text>
                <Text variant="titleLarge">Total: {item.total_amount}</Text>
            </View>
        }}
    />
}

const styles = StyleSheet.create({
    container: {
        padding: 10,
        borderBottomWidth: 1
    }
})
export default OrdersTabScreen
