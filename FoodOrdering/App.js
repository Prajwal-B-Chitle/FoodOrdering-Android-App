import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View } from 'react-native';
import AppScreen from './src/screens/AppScreen';
import Toast from 'react-native-toast-message';
import AuthProvider from './src/provider/AuthProvider';
import store from './store';
import { Provider } from 'react-redux';

export default function App() {
  return (
    <View style={styles.container}>
      <StatusBar style="auto" backgroundColor='grey' />
      <Provider store={store}>
        <AuthProvider>
          <AppScreen />
        </AuthProvider>
      </Provider>
      <Toast />
    </View >
  );
}

const styles = StyleSheet.create({
  container: {
    marginTop: 30,
    flex: 1,
    backgroundColor: '#fff',
  },
});
