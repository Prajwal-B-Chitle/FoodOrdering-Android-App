package com.sunbeaminfo.foodordering.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sunbeaminfo.foodordering.R;
import com.sunbeaminfo.foodordering.util.Constants;

public class SplashActivity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imageView = findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.splash);
        imageView.startAnimation(animation);
        new Thread(()->{
            try {
                Thread.sleep(2500);

                if(getSharedPreferences(Constants.PREFERENCE_FILE_NAME,MODE_PRIVATE)
                        .getBoolean(Constants.PREFERENCE_KEY_LOGIN_STATUS,false))
                    startActivity(new Intent(this, HomeActivity.class));
                else
                    startActivity(new Intent(this, LoginActivity.class));

                finish();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();



    }
}