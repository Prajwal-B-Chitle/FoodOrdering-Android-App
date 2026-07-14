package com.sunbeaminfo.foodordering.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sunbeaminfo.foodordering.R;
import com.sunbeaminfo.foodordering.entity.Food;
import com.sunbeaminfo.foodordering.util.API;
import com.sunbeaminfo.foodordering.util.Cart;

import java.util.List;
import java.util.stream.Collectors;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder> {
    Context context;
    Cart cart;
    LinearLayout layoutBillSummary;

    public void setLayoutBillSummary(LinearLayout layoutBillSummary) {
        this.layoutBillSummary = layoutBillSummary;
    }

    public CartListAdapter(Context context, Cart cart) {
        this.context = context;
        this.cart = cart;
    }

    public interface BillRefereshListner{
        void onBillReferesh();
    }
    BillRefereshListner billRefereshListner = null;

    public void setBillRefereshListner(BillRefereshListner billRefereshListner) {
        this.billRefereshListner = billRefereshListner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_cart,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        List<Food> foodList = cart.getFoodItems().keySet().stream().collect(Collectors.toList());
        Food food = foodList.get(position);
        holder.textName.setText(food.getName());
        holder.textPrice.setText("Rs ."+(food.getPrice()*cart.getFoodItems().get(food)));
        holder.textQty.setText(""+cart.getFoodItems().get(food));
        String URL = API.BASE_URL+"/foodimage/"+food.getImage();
        Glide.with(context).load(URL).into(holder.imageFood);
        holder.btnIncrement.setOnClickListener(v -> {
            int qty = cart.incrementQty(food);
            holder.textQty.setText(""+qty);
            holder.textPrice.setText("Rs ."+(food.getPrice()*cart.getFoodItems().get(food)));
            billRefereshListner.onBillReferesh();
        });
        holder.btnDecrement.setOnClickListener(v -> {
            int  qty = cart.decrementQty(food);
            if(qty == 0) {
                notifyDataSetChanged();
                if(cart.getFoodItemsCount() == 0)
                    layoutBillSummary.setVisibility(View.INVISIBLE);
                else {
                    layoutBillSummary.setVisibility(View.VISIBLE);
                    billRefereshListner.onBillReferesh();
                }
            }
            else {
                holder.textQty.setText("" + qty);
                holder.textPrice.setText("Rs ."+(food.getPrice()*cart.getFoodItems().get(food)));
                billRefereshListner.onBillReferesh();
            }
            });
    }

    @Override
    public int getItemCount() {
        return cart.getFoodItemsCount();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textName,textPrice, textQty;
        Button btnIncrement,btnDecrement;
        ImageView imageFood;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textPrice = itemView.findViewById(R.id.textPrice);
            textQty = itemView.findViewById(R.id.textQty);
            btnIncrement = itemView.findViewById(R.id.btnIncrement);
            btnDecrement = itemView.findViewById(R.id.btnDecrement);
            imageFood = itemView.findViewById(R.id.imageFood);
        }
    }
}
