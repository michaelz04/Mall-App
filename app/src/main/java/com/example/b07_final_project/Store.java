package com.example.b07_final_project;

import java.util.List;

public class Store {
    private String name;
    private String info;
    private String owner;
    private List<String> items;

    public Store() {}

    public Store(String name, String info, String owner, List<String> items) {
        this.name = name;
        this.info = info;
        this.owner = owner;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public String getOwner() {
        return owner;
    }

    public List<String> getItems() {
        return items;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
