import { createSlice } from "@reduxjs/toolkit";

const CartSlice = createSlice({
    name: 'cart',
    initialState: {
        cartItems: []
    },
    reducers: {
        AddToCartAction: (state, action) => {
            //console.log(action.type)
            //console.log(action.payload)
            const index = state.cartItems.findIndex(f => f.fid == action.payload.fid)
            if (index == -1)
                state.cartItems.push({ ...action.payload, qty: 1 })
            else
                state.cartItems.at(index).qty += 1
        },
        IncrementAction: (state, action) => {
            const index = state.cartItems.findIndex(f => f.fid == action.payload)
            state.cartItems.at(index).qty += 1
        },
        DecrementAction: (state, action) => {
            const index = state.cartItems.findIndex(f => f.fid == action.payload)
            if (state.cartItems.at(index).qty == 1)
                state.cartItems.splice(index, 1)
            else
                state.cartItems.at(index).qty -= 1
        },
        ClearCartAction: (state, action) => {
            state.cartItems = []
        }
    }
})

export default CartSlice.reducer
export const { AddToCartAction, IncrementAction, DecrementAction, ClearCartAction } = CartSlice.actions