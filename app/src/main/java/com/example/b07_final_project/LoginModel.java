package com.example.b07_final_project;


import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginModel {
    DatabaseReference db;
    public LoginModel(){
        db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").getReference();
    }


    public void queryOwners(LoginPresenter presenter, String username, String password){
        DatabaseReference queryOwners = db.child("Owners").child(username);
        queryOwners.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                presenter.checkOwners(snapshot.exists(), username, password);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getOwnerPassword(LoginPresenter presenter, String username, String password){
        DatabaseReference queryOwners = db.child("Owners").child(username);
        queryOwners.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String databasePassword = snapshot.child("password").getValue(String.class);
                presenter.checkOwnerPassword(username, password, databasePassword);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getOwnerStore(LoginPresenter presenter, String username){
        DatabaseReference queryOwners = db.child("Owners").child(username);
        queryOwners.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String ownerStoreID = snapshot.child("storeKey").getValue(String.class);
                presenter.checkOwnerStore(ownerStoreID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void queryShoppers(LoginPresenter presenter, String username, String password){
        DatabaseReference queryShoppers = db.child("Shoppers").child(username);
        queryShoppers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                presenter.checkShoppers(snapshot.exists(), username, password);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getShopperPassword(LoginPresenter presenter, String username, String password){
        DatabaseReference queryOwners = db.child("Shoppers").child(username);
        queryOwners.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String databasePassword = snapshot.child("password").getValue(String.class);
                presenter.checkShopperPassword(username, password, databasePassword);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
