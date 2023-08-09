package com.example.b07_final_project.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.b07_final_project.OwnerUI;
import com.example.b07_final_project.R;
import com.example.b07_final_project.classes.CurrentStoreData;
import com.example.b07_final_project.classes.CurrentUserData;
import com.example.b07_final_project.classes.Item;
import com.example.b07_final_project.classes.Store;
import com.example.b07_final_project.classes.ToolbarNavigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewItem extends Fragment {
    String storeId = CurrentStoreData.getInstance().getId();
    FirebaseDatabase db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/");
    EditText nameInput;
    EditText priceInput;
    EditText imageInput;
    EditText descInput;

    public NewItem() {
        super(R.layout.activity_add_item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_add_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ToolbarNavigation.set(getActivity(), view.findViewById(R.id.addItemToolbar));
        nameInput = view.findViewById(R.id.itemNameInput);
        priceInput = view.findViewById(R.id.itemPriceInput);
        imageInput = view.findViewById(R.id.itemImageInput);
        descInput = view.findViewById(R.id.itemDescriptionInput);

        view.findViewById(R.id.addNewItemSubmit).setOnClickListener(l -> onClickAddNewItemSubmit(view));
    }

    public void onClickAddNewItemSubmit(View view) {
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
//            successPrompt = Snackbar.make(view, "Successfully added item", 3000);

            // Would want to reset fields after successful creation
            nameInput.setText("");
            priceInput.setText("");
            descInput.setText("");
            imageInput.setText("");

            startActivity(new Intent(requireContext(), OwnerUI.class));
        } catch (Exception e) {
            successPrompt = Snackbar.make(view, "Failed to add item", 3000);
            successPrompt.show();
        }
    }
}