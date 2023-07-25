package com.example.b07_final_project.classes;

public class Item {
    private String itemName;
    private String description;
    private float price;
    private String picture;
    private String storeKey;

    public Item() {
        // Empty constructor required for Firebase
    }

    public Item(String itemname, String description, float price, String storeKey, String picture) {
        this.itemName = itemname;
        this.description = description;
        this.price = price;
        this.storeKey = storeKey;
        this.picture = picture;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getStoreKey() {
        return storeKey;
    }

    public void setStoreKey(String storeKey) {
        this.storeKey = storeKey;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}