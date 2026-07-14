package com.sunbeaminfo.foodordering.entity;

import java.io.Serializable;
import java.util.Objects;

public class Food implements Serializable {
    private int fid;
    private String name;
    private String description;
    private double price;
    private String image;

    public Food() {
    }

    public Food(int fid, String name, String description, double price, String image) {
        this.fid = fid;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Food)) return false;
        Food food = (Food) o;
        return fid == food.fid;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(fid);
    }
}
