package com.example.b07_final_project;

public class CurrentStoreData {
    private String id;

    private CurrentStoreData() {
        id = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private static final CurrentStoreData currentStore = new CurrentStoreData();

    public static CurrentStoreData getInstance() {
        return currentStore;
    }
}
