package com.example.b07_final_project.classes;

public class CurrentOrderData {
    private String id;

    public String getId() {
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    private static final CurrentOrderData currentOrder = new CurrentOrderData();
    public static CurrentOrderData getInstance() {
        return currentOrder;
    }
}
