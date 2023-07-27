package com.example.b07_final_project.classes;

public class CurrentItemData {


    private String id;

    private CurrentItemData() {
        id = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private static final CurrentItemData currentItem = new CurrentItemData();

    public static CurrentItemData getInstance() {
        return currentItem;
    }
}
