package com.example.b07_final_project.classes;

import java.util.List;
import java.util.Map;

public class Customer {
    private String username;
    private String password;
    private Map<String, Integer> cart; // item id: Amount
    private List<String> orders; // This is a string of the order ids

    public Customer() {
    }

    public Customer(String username, String password, Map<String, Integer> cart, List<String> orders) {
        this.username = username;
        this.password = password;
        this.cart = cart;
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

    public Map<String, Integer> getCart() {
        return cart;
    }

    public void setCart(Map<String, Integer> cart) {
        this.cart = cart;
    }

    public List<String> getOrders() {
        return orders;
    }

    public void setOrders(List<String> orders) {
        this.orders = orders;
    }
}
