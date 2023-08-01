package com.example.b07_final_project.classes;

import java.util.Map;

public class Order{
    private String orderID;
    private Map<String, OrderStore> stores;

    public Order() {
    }

    public Order(String orderID, Map<String, OrderStore> stores) {
        this.orderID = orderID;
        this.stores = stores;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Map<String, OrderStore> getStores() {
        return stores;
    }

    public void setStores(Map<String, OrderStore> stores) {
        this.stores = stores;
    }

    public boolean getOrderStatus() {
        // If there are no orders, why is this even here?
        if (stores == null) return true;

        for (String storeKey: stores.keySet()) {
            OrderStore orderStore = stores.get(storeKey);
            if (orderStore ==  null) return false;
            if (!orderStore.isStatus()) return false;
        }
        return true;

    }
}
