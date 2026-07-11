import AsyncStorage from '@react-native-async-storage/async-storage'
import React, { createContext, useContext, useEffect, useState } from 'react'
import { View } from 'react-native'
import config from '../utils/config'
import { Text } from 'react-native-paper'
import * as SplashScreen from 'expo-splash-screen'

const LoginContext = createContext()

function AuthProvider({ children }) {
    SplashScreen.preventAutoHideAsync()

    const [loginStatus, setLoginStatus] = useState(false)
    const [isLoading, setisLoading] = useState(true)
    const getLoginStatus = async () => {
        try {
            const status = await AsyncStorage.getItem(config.KEY_REMEMBER_ME)
            setLoginStatus(JSON.parse(status))
            setisLoading(false)
            SplashScreen.hideAsync()
        } catch (error) {
            console.log(error)
            SplashScreen.hideAsync()
        }
    }
    useEffect(() => {
        getLoginStatus()
    }, [])
    return !isLoading &&
        <LoginContext.Provider value={{ loginStatus, setLoginStatus }}>
            {children}
        </LoginContext.Provider>

}

export function useLoginContext() {
    return useContext(LoginContext)
}
export default AuthProvider
