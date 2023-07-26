package com.example.b07_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.b07_final_project.classes.Item;
import com.example.b07_final_project.classes.Store;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.example.b07_final_project.classes.CurrentStoreData;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class NewItemActivity extends AppCompatActivity {
    String storeId = CurrentStoreData.getInstance().getId();
    FirebaseDatabase db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
    }

    public void onClickAddNewItemSubmit(View view) {
        // Get all the needed input fields
        EditText nameInput = findViewById(R.id.itemNameInput);
        EditText priceInput = findViewById(R.id.itemPriceInput);
        EditText imageInput = findViewById(R.id.itemImageInput);
        EditText descInput = findViewById(R.id.itemDescriptionInput);

        // This is for showing the success/error message in a Toast, so that it can disappear after
        // a few seconds.
        // See: https://developer.android.com/reference/com/google/android/material/snackbar/Snackbar
        Snackbar successPrompt;

        // No empty fields
        if (TextUtils.isEmpty(nameInput.getText().toString())
                || TextUtils.isEmpty(priceInput.getText().toString())
                || TextUtils.isEmpty(imageInput.getText().toString())
                || TextUtils.isEmpty(descInput.getText().toString())

        ) {
            successPrompt = Snackbar.make(view, "Cannot have empty field", 3000);
            successPrompt.show();
            return;

        }

        try {
            // Create item with random ID
            DatabaseReference newItem = db.getReference("Items").push();
            String itemKey = newItem.getKey();

            Item item = new Item(
                    nameInput.getText().toString(),
                    descInput.getText().toString(),
                    Float.parseFloat(priceInput.getText().toString()),
                    imageInput.getText().toString(),
                    storeId,
                    itemKey
            );
            newItem.setValue(item);

            // This is needed to add the item in the Stores.id.items list
            DatabaseReference storeRef = db.getReference("Stores").child(storeId);
            storeRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        Store curStore = task.getResult().getValue(Store.class);
                        // If items is not initialized yet
                        if (curStore.getItems() == null) {
                            ArrayList<String> items = new ArrayList<String>();
                            items.add(itemKey);
                            curStore.setItems(items);
                        }
                        // If it is, we just add on to the list
                        else {
                            List<String> items = curStore.getItems();
                            // Even tho it is a List (Interface with no implementation). We can call
                            // .add() because the list is always initialized as an ArrayList (see above).
                            items.add(itemKey);
                            curStore.setItems(items);
                        }
                        storeRef.setValue(curStore);
                    }
                }
            });

//            if (itemKey != null)
//                storeRef.child("items").child(itemKey).setValue(nameInput.getText().toString());

            // Set text for snack bar
            successPrompt = Snackbar.make(view, "Successfully added item", 3000);

            // Would want to reset fields after successful creation
            nameInput.setText("");
            priceInput.setText("");
            descInput.setText("");
            imageInput.setText("");
        } catch (Exception e) {
            successPrompt = Snackbar.make(view, "Failed to add item", 3000);
        }

        successPrompt.show();
    }


}