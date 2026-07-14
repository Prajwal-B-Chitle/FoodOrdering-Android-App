import { configureStore } from "@reduxjs/toolkit";
import CartSlice from './src/slices/CartSlice';

const store = configureStore({
    reducer: {
        CartSlice
    }
})

export default store