package com.example.b07_final_project;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
    }

    // Method to add a store to the Firebase Realtime Database
    public static void addStoreToDatabase() {
        // Get a reference to the root node of your database
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        //Map<String, Store> storesMap = new HashMap<>();


        Store store = new Store("StoreName", "StoreInfo", "StoreOwner", Arrays.asList("item1", "item2", "item3"));
        //storesMap.put("store", store);


        Store store1 = new Store("StoreName1", "StoreInfo1", "StoreOwner1", Arrays.asList("1item1", "1item2", "1item3"));
        //storesMap.put("store1", store1);


        Store store2 = new Store("StoreName2", "StoreInfo2", "StoreOwner2", Arrays.asList("2item1", "2item2", "2item3"));
        //storesMap.put("store2", store2);

        // Push the entire map of Store objects to the "Stores" node in the database
        // Do not use push if you only want one instance everytime its ran.
        DatabaseReference storesRef = databaseRef.child("Stores").push();
        DatabaseReference storesRef1 = databaseRef.child("Stores").push();
        DatabaseReference storesRef2 = databaseRef.child("Stores").push();
        storesRef.setValue(store);
        storesRef1.setValue(store1);
        storesRef2.setValue(store2);

    }
}

