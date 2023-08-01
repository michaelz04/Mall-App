package com.example.b07_final_project.classes;

import java.util.Map;

public class OrderStore {
    private Map<String, Integer> items;
    private boolean status;

    public OrderStore() {
    }

    public OrderStore(Map<String, Integer> items, boolean status) {
        this.items = items;
        this.status = status;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public void setItems(Map<String, Integer> items) {
        this.items = items;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
