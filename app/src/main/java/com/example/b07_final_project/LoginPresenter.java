package com.example.b07_final_project;

import com.example.b07_final_project.classes.CurrentStoreData;
import com.example.b07_final_project.classes.CurrentUserData;

public class LoginPresenter {
    LoginActivityView loginView;
    LoginModel model;

    public LoginPresenter(LoginActivityView loginView, LoginModel model){
        this.loginView = loginView;
        this.model = model;
    }

    public void checkFields(String username, String password){
        if (username.isEmpty() || password.isEmpty()) {
            loginView.showSnackbar("Fields cannot be empty");
        } else {
            //check if username is in owners
            model.queryOwners(this, username, password);
        }
    }

    public void checkOwners(boolean ownerExist, String username, String password){
        if (ownerExist){
            model.getOwnerPassword(this, username, password);
        } else {
            //username not in owners, check shoppers
            model.queryShoppers(this, username, password);
        }
    }
    public void checkOwnerPassword(String username, String password, String databasePassword){
        if (databasePassword.equals(password)){
            //set current user data
            CurrentUserData currentUserData = CurrentUserData.getInstance();
            currentUserData.setId(username);
            currentUserData.setAccountType("Owners");
            //set current store instance
            model.getOwnerStore(this, username);

        } else {
            loginView.showSnackbar("Incorrect password");
        }
    }
    public void checkOwnerStore(String ownerStoreID){
        // If the store doesn't exist, send the owner to create the store
        if (ownerStoreID == null || ownerStoreID.isEmpty()) {
            loginView.startNewActivity(CreateStoreActivity.class);
        } else {
            CurrentStoreData.getInstance().setId(ownerStoreID);
            loginView.startNewActivity(OwnerMenuActivity.class);
        }
    }
    public void checkShoppers(boolean shopperExist, String username, String password){
        if (shopperExist){
            model.getShopperPassword(this, username, password);
        } else {
            loginView.showSnackbar("Username does not exist");
        }
    }
    public void checkShopperPassword(String username, String password, String databasePassword){
        if (databasePassword.equals(password)){
            //set current user data
            CurrentUserData currentUserData = CurrentUserData.getInstance();
            currentUserData.setId(username);
            currentUserData.setAccountType("Shoppers");
            loginView.startNewActivity(CustomerMenuActivity.class);
        } else {
            loginView.showSnackbar("Incorrect password");
        }
    }
}
