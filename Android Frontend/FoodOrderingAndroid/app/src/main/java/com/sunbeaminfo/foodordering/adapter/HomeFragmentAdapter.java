package com.sunbeaminfo.foodordering.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.sunbeaminfo.foodordering.fragment.CartFragment;
import com.sunbeaminfo.foodordering.fragment.FoodListFragment;
import com.sunbeaminfo.foodordering.fragment.OrderFragment;
import com.sunbeaminfo.foodordering.fragment.ProfileFragment;
import com.sunbeaminfo.foodordering.util.Cart;

public class HomeFragmentAdapter extends FragmentStateAdapter {
    Cart cart;
    public HomeFragmentAdapter(@NonNull FragmentActivity fragmentActivity,Cart cart) {
        super(fragmentActivity);
        this.cart = cart;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new FoodListFragment(cart);
            case 1:
                return new CartFragment(cart);
            case 2:
                return new OrderFragment();
            case 3:
                return new ProfileFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
