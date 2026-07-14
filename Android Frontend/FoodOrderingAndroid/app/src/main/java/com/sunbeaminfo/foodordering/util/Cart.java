package com.sunbeaminfo.foodordering.util;

import com.google.android.material.tabs.TabLayout;
import com.sunbeaminfo.foodordering.entity.Food;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Cart implements Serializable {
    // field -> Redux(initial state)
    Map<Food, Integer> foodItems;
    TabLayout.Tab cartTab = null;

    public Cart() {
        this.foodItems = new LinkedHashMap<>();
    }

    public Map<Food, Integer> getFoodItems() {
        return foodItems;
    }

    public void setCartTab(TabLayout.Tab cartTab) {
        this.cartTab = cartTab;
    }

    // methods -> Redux(Actions)
    public int getFoodItemsCount() {
        return foodItems.size();
    }

    public int getTotalQuantity() {
        Collection<Integer> quantities = foodItems.values();
        int totalQty = 0;
        for (int qty : quantities)
            totalQty += qty;
        return totalQty;
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        Set<Map.Entry<Food, Integer>> entrySet = foodItems.entrySet();
        for (Map.Entry<Food, Integer> e : entrySet) {
            double total = e.getKey().getPrice() * e.getValue();
            totalPrice += total;
        }
        return totalPrice;
    }

    public void addFoodItem(Food food) {
        Integer qty = foodItems.get(food);
        if(qty !=null){
            qty++;
            foodItems.put(food,qty);
        }
        else
            foodItems.put(food,1);
            cartTab.setText("Cart(" + foodItems.size() + ")");
    }

    public int incrementQty(Food food) {
        int qty = foodItems.get(food);
        qty++;
        foodItems.put(food, qty);
        return qty;
    }

    public int decrementQty(Food food) {
        int qty = foodItems.get(food);
        qty--;
        if (qty == 0) {
            foodItems.remove(food);
            cartTab.setText("Cart(" + foodItems.size() + ")");
        } else
            foodItems.put(food, qty);
        return qty;
    }

    public void clearCart(){
        foodItems.clear();
        cartTab.setText("Cart(0)");

    }
}
