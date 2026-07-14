package com.sunbeaminfo.foodordering.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.sunbeaminfo.foodordering.R;
import com.sunbeaminfo.foodordering.entity.User;
import com.sunbeaminfo.foodordering.util.API;
import com.sunbeaminfo.foodordering.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText editEmail,editPassword;
    Button btnSignin,btnSignup;
    CheckBox checkBoxRememberMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnSignin = findViewById(R.id.btnSignin);
        btnSignup = findViewById(R.id.btnSignup);
        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);
        btnSignin.setOnClickListener(v->signIn(v));
        btnSignup.setOnClickListener(v-> startActivity(new Intent(this, RegisterActivity.class)));

    }

    private void signIn(View v) {
        User user = new User();
        user.setEmail(editEmail.getText().toString());
        user.setPassword(editPassword.getText().toString());
        API.getApi()
                .getUserApi()
                .loginUser(user)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject responseBody = response.body();
                        if(responseBody.get(Constants.RESPONSE_STATUS).getAsString().equals(Constants.SUCCESS_RESPONSE)){
                            String token = responseBody.getAsJsonObject(Constants.RESPONSE_DATA).get("token").getAsString();
                            getSharedPreferences(Constants.PREFERENCE_FILE_NAME,MODE_PRIVATE)
                                    .edit()
                                    .putString(Constants.PREFERENCE_KEY_TOKEN,token)
                                    .putBoolean(Constants.PREFERENCE_KEY_LOGIN_STATUS,checkBoxRememberMe.isChecked())
                                    .apply();
                           startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                           finish();

                        }else
                            Snackbar.make(v,"Invalid email or password",Snackbar.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Snackbar.make(v,"Something went wrong",Snackbar.LENGTH_SHORT).show();
                    }
                });
    }
}