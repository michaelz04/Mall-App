package com.example.b07_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.*;


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
        // Check if store description is empty
        if (storeDescription.equals("")) {
            // Show the error
            showError("Store description cannot be empty");
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
                        //Store does not exist so add to DB
                        // Add store to DB
                        DatabaseReference newStoreRef = storesRef.child(storeName);
                        //set description
                        newStoreRef.child("description").setValue(storeDescription);
                        //set name of store
                        newStoreRef.child("storeName").setValue(storeName);
                        // Add store key to owner
                        ownerRef.child("StoreID").setValue(storeName);

                        // TODO: Switch to the store owner's store page activity
                        showError("Success");
                    } else {
                        showError("Store already exists");
                        return;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }
}