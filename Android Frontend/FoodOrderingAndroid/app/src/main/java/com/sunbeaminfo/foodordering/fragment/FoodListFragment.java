package com.sunbeaminfo.foodordering.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sunbeaminfo.foodordering.R;
import com.sunbeaminfo.foodordering.adapter.FoodListAdapter;
import com.sunbeaminfo.foodordering.entity.Food;
import com.sunbeaminfo.foodordering.util.API;
import com.sunbeaminfo.foodordering.util.Cart;
import com.sunbeaminfo.foodordering.util.Constants;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodListFragment extends Fragment {
    RecyclerView recyclerView;
    List<Food> foodList;


    FoodListAdapter foodListAdapter;

    Cart cart;
    public  FoodListFragment(Cart cart){
        this.cart = cart;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foodList = new ArrayList<>();
        foodListAdapter = new FoodListAdapter(getContext(),foodList,cart);
        // call the api
        API.getApi()
                .getFoodApi()
                .getFoodMenu()
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject responseBody = response.body();
                        System.out.println(responseBody);
                        if(responseBody.get(Constants.RESPONSE_STATUS).getAsString().equals(Constants.SUCCESS_RESPONSE)){
                            JsonArray jsonArray = responseBody.getAsJsonArray(Constants.RESPONSE_DATA);
                            for(JsonElement element : jsonArray){
                                JsonObject jsonObject = element.getAsJsonObject();
                                Food food = new Food();
                                food.setFid(jsonObject.get("fid").getAsInt());
                                food.setName(jsonObject.get("name").getAsString());
                                food.setDescription(jsonObject.get("description").getAsString());
                                food.setPrice(jsonObject.get("price").getAsDouble());
                                food.setImage(jsonObject.get("image").getAsString());
                                foodList.add(food);
                            }
                            foodListAdapter.notifyDataSetChanged();
                        }else
                            Toast.makeText(getContext(), "Failed to fetch food menu", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(foodListAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
    }
}