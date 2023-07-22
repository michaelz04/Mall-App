package com.example.b07_final_project;

public class CurrentUserData {
    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    private static final CurrentUserData currentUser = new CurrentUserData();
    public static CurrentUserData getInstance() {
        return currentUser;
    }

}
