package com.sunbeaminfo.foodordering.util;

import com.sunbeaminfo.foodordering.service.FoodApi;
import com.sunbeaminfo.foodordering.service.OrderApi;
import com.sunbeaminfo.foodordering.service.UserApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    public static final String BASE_URL ="http://192.168.1.103:4000";
    private static API api = null;
    private UserApi userApi;
    private FoodApi foodApi;
    private OrderApi orderApi;

    private API(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userApi = retrofit.create(UserApi.class);
        foodApi = retrofit.create(FoodApi.class);
        orderApi = retrofit.create(OrderApi.class);
    }

    public static API getApi(){
        if (api == null)
            api = new API();
        return api;
    }

    public UserApi getUserApi() {
        return userApi;
    }

    public FoodApi getFoodApi() {
        return foodApi;
    }

    public OrderApi getOrderApi() {
        return orderApi;
    }
}
