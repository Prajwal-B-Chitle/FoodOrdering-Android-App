package com.sunbeaminfo.foodordering.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sunbeaminfo.foodordering.entity.User;
import com.sunbeaminfo.foodordering.fragment.CartFragment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserApi {

    @POST("/user/signup")
    Call<JsonObject> registerUser(@Body User user);
    @POST("/user/signin")
    Call<JsonObject> loginUser(@Body User user);

    @GET("/user")
    Call<JsonObject> getProfile(@Header("token") String token);

}
