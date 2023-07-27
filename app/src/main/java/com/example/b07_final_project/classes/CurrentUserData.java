package com.example.b07_final_project.classes;

public class CurrentUserData {
    private String id;
    private String accountType;
    private String password;
  
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password){
        this.password = password;

    }
    private static final CurrentUserData currentUser = new CurrentUserData();
    public static CurrentUserData getInstance() {
        return currentUser;
    }

}
