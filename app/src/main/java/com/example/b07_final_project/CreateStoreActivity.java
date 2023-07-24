package com.example.b07_final_project;

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

        // DB references for owner and stores
        ownerRef = db.child("Owners").child(username);
        storesRef = db.child("Stores");

        // Check store among other stores and check if it's valid
        // If it is, add it to the DB
        storesRef.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                // Error fetching data
                showError("@string/create_store_data_error");
                return;
            }

            // Get list of stores from DB
            List<String> stores = new ArrayList<>();
            for (DataSnapshot child : task.getResult().getChildren()) {
                stores.add(child.getKey());
            }
            if (stores.contains(storeName)) {
                // Store already exists
                showError("@string/create_store_exists_error");
                return;
            }

            // Check if store name is empty
            if (storeName.equals("")) {
                // Show the error
                showError("@string/create_store_empty_error");
                return;
            }

            // Add store to DB
            DatabaseReference newStoreRef = storesRef.child(storeName);
            newStoreRef.child("description").setValue(storeDescription);
            // Add store key to owner
            ownerRef.child("storeKey").child(storeName);

            // TODO: Switch to the store owner's store page activity
            showError("Success");
        });
    }
}