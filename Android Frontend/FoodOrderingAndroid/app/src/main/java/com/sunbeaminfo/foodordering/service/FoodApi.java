package com.sunbeaminfo.foodordering.service;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FoodApi {

    @GET("/food/menu")
    Call<JsonObject> getFoodMenu();
}
