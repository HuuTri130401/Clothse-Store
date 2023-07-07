package com.trith.models;


public class CategoryModel {

    private String name;

    public CategoryModel(String  name) {
        this.name = name;
    }

    public CategoryModel() {
    }

    public String  getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
