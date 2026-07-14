import { createBottomTabNavigator } from '@react-navigation/bottom-tabs'
import React from 'react'
import { TouchableOpacity, View } from 'react-native'
import FoodListTabScreen from './tabs/FoodListTabScreen'
import CartTabScreen from './tabs/CartTabScreen'
import OrdersTabScreen from './tabs/OrdersTabScreen'
import ProfileTabsScreen from './tabs/ProfileTabsScreen'
import Ionicons from '@react-native-vector-icons/ionicons'
import AsyncStorage from '@react-native-async-storage/async-storage'
import config from '../utils/config'
import { useDispatch, useSelector } from 'react-redux'
import { ClearCartAction } from '../slices/CartSlice'

const Tab = createBottomTabNavigator()
function HomeScreen({ navigation }) {
    const cartItems = useSelector(store => store.CartSlice.cartItems)
    const disptach = useDispatch()
    const handleLogoutPress = async () => {
        await AsyncStorage.removeItem(config.KEY_TOKEN)
        await AsyncStorage.removeItem(config.KEY_REMEMBER_ME)
        disptach(ClearCartAction())
        navigation.replace('login')
    }

    return (
        <Tab.Navigator screenOptions={{ headerShown: false }}>
            <Tab.Screen
                name='food'
                component={FoodListTabScreen}
                options={{
                    tabBarIcon: () => <Ionicons name='fast-food-outline' size={20} />
                }}
            />
            <Tab.Screen
                name='cart'
                component={CartTabScreen}
                options={{
                    tabBarLabel: `cart(${cartItems.length})`,
                    tabBarIcon: () => <Ionicons name='cart-outline' size={20} />
                }}
            />
            <Tab.Screen
                name='orders'
                component={OrdersTabScreen}
                options={{
                    tabBarIcon: () => <Ionicons name='bag-outline' size={20} />
                }}
            />
            <Tab.Screen
                name='profile'
                component={ProfileTabsScreen}
                options={{
                    headerShown: true,
                    headerRight: () => {
                        return <TouchableOpacity onPress={handleLogoutPress}>
                            <Ionicons name='log-out-outline' size={30} color='red' />
                        </TouchableOpacity>
                    },
                    tabBarIcon: () => <Ionicons name='person-outline' size={20} />

                }}
            />
        </Tab.Navigator>
    )
}

export default HomeScreen
