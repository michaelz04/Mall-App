package com.example.b07_final_project;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.b07_final_project.classes.CurrentStoreData;
import com.example.b07_final_project.classes.CurrentUserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
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
    /*
    public void queryDB(LoginPresenter presenter, String username){
        //query owners
        DatabaseReference queryOwners = db.child("Owners").child(username);
        queryOwners.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    //if username not in owners, check shoppers
                    DatabaseReference queryShoppers = db.child("Shoppers").child(username);
                    queryShoppers.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.exists()){
                                //username does not exist in either owners or shoppers
                                Snackbar.make(  view, "Username does not exist", 1000).show();
                            } else {
                                //username exists in shoppers so check password
                                //get password from database
                                db.child("Shoppers").child(username).child("password").get().
                                        addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                String databasePassword = String.valueOf(task.getResult().getValue());
                                                //check if password and password from database is correct
                                                if (databasePassword.equals(password)){
                                                    //set current user id
                                                    CurrentUserData currentUserData = CurrentUserData.getInstance();
                                                    currentUserData.setId(username);
                                                    currentUserData.setAccountType("Shoppers");
                                                    startActivity(new Intent(LoginActivity.this, CustomerMenuActivity.class));
                                                } else {
                                                    Snackbar.make(view, "Incorrect password", 1000).show();
                                                }
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    //username exists in owners so check password
                    //get password from database
                    String databasePassword = snapshot.child("password").getValue(String.class);
                    if (databasePassword.equals(password)){
                        //set current user id
                        CurrentUserData currentUserData = CurrentUserData.getInstance();
                        currentUserData.setId(username);
                        currentUserData.setAccountType("Owners");
                        //set current store instance
                        String ownerStoreID = snapshot.child("storeKey").getValue(String.class);
                        // If the store doesn't exist, send the owner to create the store
                        if (ownerStoreID == null || ownerStoreID.isEmpty()) {
                            startActivity(new Intent(LoginActivity.this, CreateStoreActivity.class));
                            return;
                        }
                        CurrentStoreData.getInstance().setId(ownerStoreID);

                        startActivity(new Intent(LoginActivity.this, OwnerMenuActivity.class));
                    } else {
                        Snackbar.make(view, "Incorrect password", 1000).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


     */

}
