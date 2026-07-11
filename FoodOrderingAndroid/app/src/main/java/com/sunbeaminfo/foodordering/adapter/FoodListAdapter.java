package com.sunbeaminfo.foodordering.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sunbeaminfo.foodordering.R;
import com.sunbeaminfo.foodordering.entity.Food;
import com.sunbeaminfo.foodordering.util.API;
import com.sunbeaminfo.foodordering.util.Cart;

import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.MyViewHolder> {
    Context context;
    List<Food> foodList;

    Cart cart;

    public FoodListAdapter(Context context, List<Food> foodList,Cart cart) {
        this.context = context;
        this.foodList = foodList;
        this.cart = cart;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_food,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.textName.setText(food.getName());
        holder.textDescription.setText(food.getDescription());
        holder.textPrice.setText("Rs. "+food.getPrice());
        String url = API.BASE_URL+"/foodimage/"+food.getImage();
        Glide.with(context).load(url).into(holder.imageFood);
        holder.btnAddToCart.setOnClickListener(v->{
            // add the item in the cart
            cart.addFoodItem(food);
        });

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textName,textDescription,textPrice;
        Button btnAddToCart;
        ImageView imageFood;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textDescription = itemView.findViewById(R.id.textDescription);
            textPrice = itemView.findViewById(R.id.textPrice);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
            imageFood = itemView.findViewById(R.id.imageFood);
        }
    }
}
