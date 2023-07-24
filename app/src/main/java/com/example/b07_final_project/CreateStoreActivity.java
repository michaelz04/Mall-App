package com.example.b07_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CreateStoreActivity extends AppCompatActivity {
    DatabaseReference db;
    DatabaseReference ownerRef;
    DatabaseReference storesRef;

    String username = CurrentUserData.getInstance().getId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_store);
        db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").getReference();
    }

    @Override
    public void onBackPressed(){

    }
    private void showError(String errorText) {
        // Replace the error text to display its message
        ((TextView) findViewById(R.id.createStoreError)).setText(errorText);
    }

    public void onClickCreateStore(View view) {
        // When the 'Create store' button is clicked...

        // Get user's input
        String storeName = ((EditText) findViewById(R.id.editTextStoreName))
                .getText()
                .toString();
        String storeDescription = ((EditText) findViewById(R.id.editTextStoreDescription))
                .getText()
                .toString();

        // Check if store name is empty
        if (storeName.equals("")) {
            // Show the error
            showError("Store name cannot be empty");
            return;
        }

        // DB references for owner and stores
        ownerRef = db.child("Owners").child(username);
        storesRef = db.child("Stores");

        // Check store among other stores and check if it's valid
        // If it is, add it to the DB
        storesRef.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                // Error fetching data
                showError("Error fetching data");
                return;
            }

            //check if store exists in DB
            DatabaseReference queryStores = db.child("Stores").child(storeName);

            queryStores.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()){
                        //if username not in owners, check shoppers
                        DatabaseReference queryShoppers = db.child("Shoppers").child(username);
                        queryShoppers.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (!snapshot.exists()){
                                    //username does not exist in either owners or shoppers
                                    ((TextView)findViewById(R.id.LoginFail)).setText("Username does not exist");
                                } else {
                                    //username exists in shoppers so check password
                                    //get password from database
                                    db.child("Shoppers").child(username).child("Password").get().
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
                                                        ((TextView)findViewById(R.id.LoginFail)).setText("Password correct");
                                                    } else {
                                                        ((TextView)findViewById(R.id.LoginFail)).setText("Password incorrect");
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

                        showError("Store already exists");
                        return;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            // Add store to DB
            DatabaseReference newStoreRef = storesRef.child(storeName);
            newStoreRef.child("description").setValue(storeDescription);
            // Add store key to owner
            ownerRef.child("StoreID").child(storeName);

            // TODO: Switch to the store owner's store page activity
            showError("Success");
        });
    }
}