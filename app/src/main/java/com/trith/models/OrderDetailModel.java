package com.trith.models;

import java.io.Serializable;

public class OrderDetailModel implements Serializable {
    String productName;
    String quantity;
    int total;
    String currentDate;
    String currentTime;

    String dcocumentId;

    public OrderDetailModel() {
    }

    public OrderDetailModel(String productName, String quantity, int total, String currentDate, String currentTime) {
        this.productName = productName;
        this.quantity = quantity;
        this.total = total;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getDcocumentId() {
        return dcocumentId;
    }

    public void setDcocumentId(String dcocumentId) {
        this.dcocumentId = dcocumentId;
    }
}
