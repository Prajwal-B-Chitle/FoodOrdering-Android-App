package com.sunbeaminfo.foodordering.entity;

import java.io.Serializable;

public class Order implements Serializable {
    private int oid;
    private String date;
    private double total_amount;

    public Order() {
    }

    public Order(int oid, String date, double total_amount) {
        this.oid = oid;
        this.date = date;
        this.total_amount = total_amount;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    @Override
    public String toString() {
        return "oid=" + oid +
                "\ndate='" + date + '\'' +
                "\ntotal_amount=" + total_amount;
    }
}
