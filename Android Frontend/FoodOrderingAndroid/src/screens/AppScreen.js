import { NavigationContainer } from '@react-navigation/native'
import { createNativeStackNavigator } from '@react-navigation/native-stack'
import { View } from 'react-native'
import LoginScreen from './LoginScreen'
import RegisterScreen from './RegisterScreen'
import HomeScreen from './HomeScreen'
import { useLoginContext } from '../provider/AuthProvider'

const Stack = createNativeStackNavigator()

function AppScreen() {
    const { loginStatus } = useLoginContext()
    return (
        <NavigationContainer>
            <Stack.Navigator initialRouteName={loginStatus ? 'home' : 'login'} screenOptions={{ headerShown: false }}>
                <Stack.Screen name='login' component={LoginScreen} />
                <Stack.Screen name='register' component={RegisterScreen} />
                <Stack.Screen name='home' component={HomeScreen} />
            </Stack.Navigator>
        </NavigationContainer>
    )
}

export default AppScreen
