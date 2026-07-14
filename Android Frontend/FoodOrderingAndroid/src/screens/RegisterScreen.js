import Ionicons from '@react-native-vector-icons/ionicons'
import React, { useState } from 'react'
import { StyleSheet, View } from 'react-native'
import { Button, TextInput } from 'react-native-paper'
import { registerUser } from '../services/userservcie'
import Toast from 'react-native-toast-message'

function RegisterScreen({ navigation }) {
    const [name, setName] = useState('')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [mobile, setMobile] = useState('')

    const handleOnSignupPress = async () => {
        try {
            if (name === '')
                alert('Name is required')
            else if (email === '')
                alert('Email is required')
            else if (password === '')
                alert('Password is required')
            else if (mobile === '')
                alert('Mobile is required')
            else {
                const body = { name, email, password, mobile }
                const response = await registerUser(body)
                if (response.status === 'success') {
                    Toast.show({ text1: 'Registartion successfull', type: 'success' })
                    navigation.goBack()
                }
                else
                    Toast.show({ text1: 'Registartion Failed', type: 'error' })

            }
        } catch (error) {
            alert(error)
        }
    }

    return (
        <View style={styles.container}>

            <Ionicons name='person-outline' size={50} style={styles.imagePerson} />

            <TextInput
                style={{ fontSize: 20 }}
                label='Name'
                mode='outlined'
                onChangeText={setName}
            />

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

            <TextInput
                style={{ fontSize: 20 }}
                label='Phone'
                mode='outlined'
                keyboardType='phone-pad'
                onChangeText={setMobile}
            />

            <Button
                style={styles.button}
                buttonColor='green'
                mode='contained'
                onPress={handleOnSignupPress}
            >Signup</Button>

        </View >
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 16
    },
    imagePerson: {
        width: 50,
        height: 50,
        alignSelf: 'center'
    },
    button: {
        marginTop: 10,
        alignSelf: 'center',
        width: '50%', margin: 5
    }
})

export default RegisterScreen
