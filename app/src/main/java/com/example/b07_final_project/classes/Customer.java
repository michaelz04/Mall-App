package com.example.b07_final_project.classes;

import java.util.List;

public class Customer {
    private String username;
    private String password;
    private List<String> cart;
    private List<String> orders;

    public Customer() {
    }

    public Customer(String username, String password, List<String> cart, List<String> orders) {
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

    public List<String> getCart() {
        return cart;
    }

    public void setCart(List<String> cart) {
        this.cart = cart;
    }

    public List<String> getOrders() {
        return orders;
    }

    public void setOrders(List<String> orders) {
        this.orders = orders;
    }
}
