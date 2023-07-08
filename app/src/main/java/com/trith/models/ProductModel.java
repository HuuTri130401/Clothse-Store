package com.trith.models;

import java.io.Serializable;

public class ProductModel implements Serializable {
    private String image;
    private String name;
    private int price;
    private String type;
    private String description;


    public ProductModel(String image, String name, int price, String type, String description) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.type = type;
        this.description = description;
    }

    public ProductModel() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
