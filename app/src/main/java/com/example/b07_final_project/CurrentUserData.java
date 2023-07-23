package com.example.b07_final_project;

public class CurrentUserData {
    private String id;
    private String accountType;
    public String getId() {
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getAccountType() {
        return accountType;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    private static final CurrentUserData currentUser = new CurrentUserData();
    public static CurrentUserData getInstance() {
        return currentUser;
    }

}
