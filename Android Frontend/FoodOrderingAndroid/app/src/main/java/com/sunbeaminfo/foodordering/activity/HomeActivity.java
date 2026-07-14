package com.sunbeaminfo.foodordering.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sunbeaminfo.foodordering.R;
import com.sunbeaminfo.foodordering.adapter.HomeFragmentAdapter;
import com.sunbeaminfo.foodordering.util.Cart;

public class HomeActivity extends AppCompatActivity {
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    HomeFragmentAdapter homeFragmentAdapter;
    Cart cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        viewPager2 = findViewById(R.id.viewPager2);
        tabLayout = findViewById(R.id.tabLayout);
        cart = new Cart();
        homeFragmentAdapter = new HomeFragmentAdapter(this,cart);
        viewPager2.setAdapter(homeFragmentAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Food List").setIcon(R.drawable.food);
                        break;
                    case 1:
                        tab.setText("Cart(0)").setIcon(R.drawable.cart);
                        cart.setCartTab(tab); // pass the tab view to the cart
                        break;
                    case 2:
                        tab.setText("Orders").setIcon(R.drawable.orders);
                        break;
                    case 3:
                        tab.setText("Profile").setIcon(R.drawable.profile);
                        break;
                }
            }
        }).attach();


    }
}