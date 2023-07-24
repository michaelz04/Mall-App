package com.example.b07_final_project;

public class Item {
    private String itemName;
    private String description;
    private float price;
    private String storeName;

    public Item() {
        // Empty constructor required for Firebase
    }

    public Item(String itemname, String description, float price, String storeName) {
        this.itemName = itemname;
        this.description = description;
        this.price = price;
        this.storeName = storeName;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}