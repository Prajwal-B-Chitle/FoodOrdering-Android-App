package com.sunbeaminfo.foodordering.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.sunbeaminfo.foodordering.R;
import com.sunbeaminfo.foodordering.activity.LoginActivity;
import com.sunbeaminfo.foodordering.entity.User;
import com.sunbeaminfo.foodordering.util.API;
import com.sunbeaminfo.foodordering.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {

    LinearLayout btnLogout;
    TextView textName,textEmail,textMobile;
    User user = null;

    private void getUserProfile()
    {
        String token = getContext().getSharedPreferences(Constants.PREFERENCE_FILE_NAME,Context.MODE_PRIVATE)
                .getString(Constants.PREFERENCE_KEY_TOKEN,"");
        API.getApi()
                .getUserApi()
                .getProfile(token)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject responseBody = response.body();
                        if(responseBody.get(Constants.RESPONSE_STATUS).getAsString().equals(Constants.SUCCESS_RESPONSE)){
                            JsonObject userObject = responseBody.getAsJsonObject(Constants.RESPONSE_DATA);
                            user = new User();
                            user.setName(userObject.get("name").getAsString());
                            user.setEmail(userObject.get("email").getAsString());
                            user.setMobile(userObject.get("mobile").getAsString());
                            textName.setText(user.getName());
                            textEmail.setText(user.getEmail());
                            textMobile.setText(user.getMobile());
                        }
                        else
                            Toast.makeText(getContext(),responseBody.get("error").getAsString() , Toast.LENGTH_SHORT).show();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogout = view.findViewById(R.id.btnLogout);
        textName = view.findViewById(R.id.textName);
        textEmail = view.findViewById(R.id.textEmail);
        textMobile = view.findViewById(R.id.textMobile);
        btnLogout.setOnClickListener(v->{
            getContext().getSharedPreferences(Constants.PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
                    .edit()
                    .putBoolean(Constants.PREFERENCE_KEY_LOGIN_STATUS,false)
                    .apply();
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
        });
        getUserProfile();
    }
}