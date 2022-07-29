package com.example.myapplication.models;

import java.io.Serializable;

public class AllTopsModel implements Serializable {
    String name, img_url, type, description;
    int price;

    public AllTopsModel() {
    }

    public AllTopsModel(String name, String img_url, String type, String description, int price) {
        this.name = name;
        this.img_url = img_url;
        this.type = type;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
