package com.example.b07_final_project.classes;

import java.util.List;

public class Owner {
    private String username;
    private String password;
    private String storeKey;

    private List<String> orders;

    public Owner() {
    }

    public Owner(String username, String password, String storeKey, List<String> orders) {
        this.username = username;
        this.password = password;
        this.storeKey = storeKey;
        this.orders = orders;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStoreKey() {
        return storeKey;
    }

    public void setStoreKey(String storeKey) {
        this.storeKey = storeKey;
    }

    public List<String> getOrders() {
        return orders;
    }

    public void setOrders(List<String> orders) {
        this.orders = orders;
    }
}
