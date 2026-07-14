package com.sunbeaminfo.foodordering.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class RegisterActivity extends AppCompatActivity {
    EditText editName,editEmail,editPassword,editPhone;
    Button btnSignup,btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editPhone = findViewById(R.id.editPhone);
        btnSignup = findViewById(R.id.btnSignup);
        btnCancel = findViewById(R.id.btnCancel);
        
        btnSignup.setOnClickListener(v->signUp(v));
        btnCancel.setOnClickListener(v->finish());
    }

    private void signUp(View v) {
        String name = editName.getText().toString();
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        String mobile = editPhone.getText().toString();
        // basic validation
        if(name.equals(""))
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
        else if(email.equals(""))
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
        else if(password.equals(""))
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
        else if(mobile.equals(""))
            Toast.makeText(this, "Phone no cannot be empty", Toast.LENGTH_SHORT).show();
        else{
            User user = new User(name,email,password,mobile);
            API.getApi()
                    .getUserApi()
                    .registerUser(user)
                    .enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            JsonObject responseBody = response.body();
                            if(responseBody.get(Constants.RESPONSE_STATUS).getAsString().equals(Constants.SUCCESS_RESPONSE)){
                                Snackbar.make(v,"Registration Successful",Snackbar.LENGTH_SHORT).show();
                                finish();
                            }else
                                Snackbar.make(v,"Registration Failed",Snackbar.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Snackbar.make(v,"Something went wrong",Snackbar.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}