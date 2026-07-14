import Ionicons from '@react-native-vector-icons/ionicons'
import React, { useEffect, useState } from 'react'
import { View } from 'react-native'
import { Text } from 'react-native-paper'
import { StyleSheet } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import config from '../../utils/config';
import { getUser } from '../../services/userservcie';

function ProfileTabsScreen() {
    const [name, setName] = useState('')
    const [email, setEmail] = useState('')
    const [mobile, setMobile] = useState('')

    const getUserProfile = async () => {
        try {
            const token = await AsyncStorage.getItem(config.KEY_TOKEN)
            const response = await getUser(token)
            if (response.status == 'success') {
                setName(response.data.name)
                setEmail(response.data.email)
                setMobile(response.data.mobile)
            }
        } catch (error) {
            alert(error)
        }
    }


    useEffect(() => {
        getUserProfile()
    }, [])

    return (
        <View style={{ padding: 16 }}>
            <View style={styles.innerContainer}>
                <Ionicons name='person-outline' size={25} />
                <Text style={{ marginLeft: 20 }} variant="titleLarge" >{name}</Text>
            </View>
            <View style={styles.innerContainer}>
                <Ionicons name='mail-outline' size={25} />
                <Text style={{ marginLeft: 20 }} variant="titleLarge" >{email}</Text>
            </View>
            <View style={styles.innerContainer}>
                <Ionicons name='call-outline' size={25} />
                <Text style={{ marginLeft: 20 }} variant="titleLarge" >{mobile}</Text>
            </View>

        </View>
    )
}

const styles = StyleSheet.create({
    innerContainer: {
        marginTop: 10,
        flexDirection: 'row',
        alignItems: 'center'
    }
})

export default ProfileTabsScreen
