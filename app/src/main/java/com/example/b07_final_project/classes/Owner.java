package com.example.b07_final_project.classes;

import java.util.Map;

public class Owner {
    private String username;
    private String password;
    private String storeKey;

    private Map<String, String> orders;

    public Owner() {
    }

    public Owner(String username, String password, String storeKey, Map<String, String> orders) {
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

    public Map<String, String> getOrders() {
        return orders;
    }

    public void setOrders(Map<String, String> orders) {
        this.orders = orders;
    }
}
