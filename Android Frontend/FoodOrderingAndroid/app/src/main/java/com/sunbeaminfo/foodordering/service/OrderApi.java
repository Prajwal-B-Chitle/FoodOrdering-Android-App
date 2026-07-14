package com.sunbeaminfo.foodordering.service;

import com.google.gson.JsonObject;
import com.sunbeaminfo.foodordering.fragment.CartFragment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OrderApi {
    @POST("/order")
    Call<JsonObject> placeOrder(@Header("token") String token,@Body CartFragment.CartItems cartItems);

    @GET("/order")
    Call<JsonObject> getAllOrders(@Header("token") String token);
}
