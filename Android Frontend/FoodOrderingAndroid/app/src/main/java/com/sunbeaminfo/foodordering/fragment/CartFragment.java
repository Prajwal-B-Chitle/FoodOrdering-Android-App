package com.sunbeaminfo.foodordering.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.sunbeaminfo.foodordering.R;
import com.sunbeaminfo.foodordering.adapter.CartListAdapter;
import com.sunbeaminfo.foodordering.entity.Food;
import com.sunbeaminfo.foodordering.util.API;
import com.sunbeaminfo.foodordering.util.Cart;
import com.sunbeaminfo.foodordering.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment {
    Cart cart;
    CartListAdapter cartListAdapter;
    RecyclerView recyclerView;
    LinearLayout layoutBillSummary;
    Button btnPlaceOrder;
    TextView textTotalItems,textTotalQty,textTotalPrice;
    public  CartFragment(Cart cart){
        this.cart = cart;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       cartListAdapter = new CartListAdapter(getContext(),cart);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(cartListAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        layoutBillSummary = view.findViewById(R.id.layoutBillSummary);
        cartListAdapter.setLayoutBillSummary(layoutBillSummary);
        textTotalItems = view.findViewById(R.id.textTotalItems);
        textTotalQty = view.findViewById(R.id.textTotalQty);
        textTotalPrice = view.findViewById(R.id.textTotalPrice);
        btnPlaceOrder = view.findViewById(R.id.btnPlaceOrder);
        btnPlaceOrder.setOnClickListener(v -> placeOrder());
        cartListAdapter.setBillRefereshListner(()->refereshBill());
    }

    public static class CartItems{
        double total_amount;
        List<FoodItems> foodItems = new ArrayList<>();
        static class FoodItems{
            int fid;
            int qty;
        }
    }


    private void placeOrder() {
        CartItems cartItems = new CartItems();
        //cartItems.total_amount = cart.getTotalPrice();
        Set<Map.Entry<Food,Integer>> entrySet = cart.getFoodItems().entrySet();
        for (Map.Entry<Food,Integer> e:entrySet) {
            CartItems.FoodItems foodItem = new CartItems.FoodItems();
            foodItem.fid = e.getKey().getFid();
            foodItem.qty = e.getValue();
            cartItems.total_amount+=(e.getKey().getPrice() * e.getValue());
            cartItems.foodItems.add(foodItem);
        }
        String token = getContext().getSharedPreferences(Constants.PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
                .getString(Constants.PREFERENCE_KEY_TOKEN,"");
        API.getApi()
                .getOrderApi()
                .placeOrder(token,cartItems)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.body().get(Constants.RESPONSE_STATUS).getAsString().equals(Constants.SUCCESS_RESPONSE)){
                            cart.clearCart();
                            layoutBillSummary.setVisibility(View.INVISIBLE);
                            cartListAdapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "Order Placed Successfully", Toast.LENGTH_SHORT).show();

                        }else
                            Toast.makeText(getContext(), "Failed to Place Order", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void refereshBill() {
        textTotalItems.setText("no of items : "+cart.getFoodItemsCount());
        textTotalQty.setText("total qty : "+cart.getTotalQuantity());
        textTotalPrice.setText("total price : "+cart.getTotalPrice());
    }

    @Override
    public void onResume() {
        super.onResume();
        cartListAdapter.notifyDataSetChanged();
        if(cart.getFoodItemsCount() == 0)
            layoutBillSummary.setVisibility(View.INVISIBLE);
        else{
            layoutBillSummary.setVisibility(View.VISIBLE);
            refereshBill();
        }

    }
}