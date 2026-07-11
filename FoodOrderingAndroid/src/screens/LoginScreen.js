import React, { useState } from 'react'
import { StyleSheet, View } from 'react-native'
import { Button, Checkbox, TextInput } from 'react-native-paper'
import Ionicons from '@react-native-vector-icons/ionicons'
import { loginUser } from '../services/userservcie'
import Toast from 'react-native-toast-message'
import AsyncStorage from './../../node_modules/@react-native-async-storage/async-storage/lib/module/AsyncStorage';
import config from '../utils/config'

function LoginScreen({ navigation }) {
    const [checkBoxRemeberMe, setCheckBoxRememberMe] = useState('unchecked')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')

    const handleSigninPress = async () => {
        try {
            const response = await loginUser(email, password)
            if (response.status === 'success') {
                Toast.show({
                    type: 'success',
                    text1: 'Sigin Successful'
                })
                await AsyncStorage.setItem(config.KEY_TOKEN, response.data.token)
                if (checkBoxRemeberMe === 'checked')
                    await AsyncStorage.setItem(config.KEY_REMEMBER_ME, 'true')

                navigation.replace('home')
            }
            else
                Toast.show({
                    type: 'error',
                    text1: response.error
                })
        } catch (error) {
            alert(error)
        }
    }
    return (
        <View style={styles.container}>

            <Ionicons name='person-outline' size={100} style={styles.imagePerson} />

            <TextInput
                style={{ fontSize: 20 }}
                label='Email'
                mode='outlined'
                keyboardType='email-address'
                onChangeText={setEmail}
            />

            <TextInput
                style={{ fontSize: 20 }}
                label='Password'
                mode='outlined'
                secureTextEntry={true}
                onChangeText={setPassword}
            />

            <Checkbox.Item
                status={checkBoxRemeberMe}
                label='Remember Me'
                onPress={() => setCheckBoxRememberMe(checkBoxRemeberMe == 'checked' ? 'uncheked' : 'checked')}
            />

            <View style={{ flexDirection: 'row' }}>
                <Button
                    style={styles.button}
                    buttonColor='green'
                    mode='contained'
                    onPress={handleSigninPress}
                >Signin</Button>

                <Button
                    style={styles.button}
                    buttonColor='blue'
                    mode='contained'
                    onPress={() => navigation.push('register')}
                >Signup</Button>
            </View>
        </View >
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        padding: 16
    },
    imagePerson: {
        width: 100,
        height: 100,
        alignSelf: 'center'
    },
    button: {
        width: '50%', margin: 5
    }
})

export default LoginScreen
