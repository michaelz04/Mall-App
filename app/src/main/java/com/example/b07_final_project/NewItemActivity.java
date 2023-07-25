package com.example.b07_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.b07_final_project.classes.CurrentStoreData;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewItemActivity extends AppCompatActivity {
    String storeId = CurrentStoreData.getInstance().getId();
    FirebaseDatabase db = FirebaseDatabase.getInstance("https://testing-6c0f3-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
    }

    public void onClickAddNewItemSubmit(View view) {
        // Get all the needed input fields
        EditText nameInput = findViewById(R.id.itemNameInput);
        EditText priceInput = findViewById(R.id.itemPriceInput);
        EditText descInput = findViewById(R.id.itemDescriptionInput);

        // This is for showing the success/error message in a Toast, so that it can disappear after
        // a few seconds.
        // See: https://developer.android.com/reference/com/google/android/material/snackbar/Snackbar
        Snackbar successPrompt;

        try {
            // Create item with random ID
            DatabaseReference newItem = db.getReference("Items").push();
            String itemKey = newItem.getKey();

            // TODO: Discuss whether to use classes
            newItem.child("itemName").setValue(nameInput.getText().toString());
            newItem.child("price").setValue(priceInput.getText().toString());
            newItem.child("description").setValue(descInput.getText().toString());
            newItem.child("storeKey").setValue(storeId);

            // This is needed to add the item in the Stores.id.items list
            DatabaseReference storeRef = db.getReference("Stores").child(storeId);
            if (itemKey != null) storeRef.child("items").child(itemKey).setValue(nameInput.getText().toString());

            // Set text for snack bar
            successPrompt = Snackbar.make(view, "Successfully added item", 3000);

            // Would want to reset fields after successful creation
            nameInput.setText("");
            priceInput.setText("");
            descInput.setText("");
        } catch (Exception e) {
            successPrompt = Snackbar.make(view, "Failed to add item", 3000);
        }

        successPrompt.show();
    }


}