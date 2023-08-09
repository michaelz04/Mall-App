package com.example.b07_final_project.classes;

import java.util.List;

public class Store {
    private String storeName;
    private String description;
    private String storeOwner;
    private List<String> items;
    private String picture;

    public Store() {}

    public Store(String storeName, String description, String storeOwner, List<String> items, String picture) {
        this.storeName = storeName;
        this.description = description;
        this.storeOwner = storeOwner;
        this.items = items;
        this.picture = picture;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getDescription() {
        return description;
    }

    public String getStoreOwner() {
        return storeOwner;
    }

    public List<String> getItems() {
        return items;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStoreOwner(String storeOwner) {
        this.storeOwner = storeOwner;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
