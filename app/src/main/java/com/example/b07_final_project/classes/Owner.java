package com.example.b07_final_project.classes;

public class Owner {
    private String username;
    private String password;
    private String storeKey;

    public Owner() {
    }

    public Owner(String username, String password, String storeKey) {
        this.username = username;
        this.password = password;
        this.storeKey = storeKey;
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
}
