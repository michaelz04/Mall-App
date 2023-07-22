package com.example.b07_final_project;

import java.util.List;

public class Store {
    public String name;
    public String info;
    public String owner;
    public List<String> items;

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
}
