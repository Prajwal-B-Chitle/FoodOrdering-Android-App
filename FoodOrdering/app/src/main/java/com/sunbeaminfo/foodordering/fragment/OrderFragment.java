package com.sunbeaminfo.foodordering.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sunbeaminfo.foodordering.R;
import com.sunbeaminfo.foodordering.entity.Order;
import com.sunbeaminfo.foodordering.util.API;
import com.sunbeaminfo.foodordering.util.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderFragment extends Fragment {

    ListView listView;
    ArrayAdapter arrayAdapter;
    List<Order> orderList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listView);
        orderList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,orderList);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        String token = getContext().getSharedPreferences(Constants.PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
                        .getString(Constants.PREFERENCE_KEY_TOKEN,"");
        API.getApi()
                .getOrderApi()
                .getAllOrders(token)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject responseBody = response.body();
                        if(responseBody.get(Constants.RESPONSE_STATUS).getAsString().equals(Constants.SUCCESS_RESPONSE)){
                            JsonArray jsonArray = responseBody.getAsJsonArray(Constants.RESPONSE_DATA);
                            orderList.clear();
                            for (JsonElement element:jsonArray) {
                                JsonObject orderObject = element.getAsJsonObject();
                                Order order = new Order();
                                order.setOid(orderObject.get("oid").getAsInt());
                                order.setDate(orderObject.get("odate").getAsString());
                                order.setTotal_amount(orderObject.get("total_amount").getAsDouble());
                                orderList.add(order);
                            }
                            arrayAdapter.notifyDataSetChanged();
                        }else
                            Toast.makeText(getContext(), "Failed to get Orders", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}