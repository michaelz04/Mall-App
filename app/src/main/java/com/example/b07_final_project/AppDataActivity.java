package com.example.b07_final_project;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.b07_final_project.classes.Store;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.b07_final_project.classes.Item;

public class AppDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
    }

    // Method to add a store to the Firebase Realtime Database
    public static void addStoreToDatabase() {
       /* // Get a reference to the root node of your database
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
        storesRef2.setValue(store2);*/

        DatabaseReference databaseRef = FirebaseDatabase.getInstance("https://test-project-17268-default-rtdb.firebaseio.com/").getReference();

        // Create a few sample items
        Item item1 = new Item("Item 1", "Description 1", 10.0f, "StoreName", "www.sampleimageurl.com");
        Item item2 = new Item("Item 2", "Description 2", 15.0f, "StoreName", "www.sampleimageurl.com");
        Item item3 = new Item("Item 3", "Description 3", 20.0f, "StoreName", "www.sampleimageurl.com");

        Item item4 = new Item("Item 4", "Description 4", 12.0f, "StoreName1", "www.sampleimageurl.com");
        Item item5 = new Item("Item 5", "Description 5", 18.0f, "StoreName1", "www.sampleimageurl.com");
        Item item6 = new Item("Item 6", "Description 6", 22.0f, "StoreName1", "www.sampleimageurl.com");

        Item item7 = new Item("Item 7", "Description 7", 8.0f, "StoreName2", "www.sampleimageurl.com");
        Item item8 = new Item("Item 8", "Description 8", 16.0f, "StoreName2", "www.sampleimageurl.com");
        Item item9 = new Item("Item 9", "Description 9", 24.0f, "StoreName2", "www.sampleimageurl.com");

        // Push the items to the "Items" node in the database
        DatabaseReference itemsRef = databaseRef.child("Items").push();
        itemsRef.setValue(item1);
        DatabaseReference itemsRef2 = databaseRef.child("Items").push();
        itemsRef2.setValue(item2);
        DatabaseReference itemsRef3 = databaseRef.child("Items").push();
        itemsRef3.setValue(item3);

        DatabaseReference itemsRef4 = databaseRef.child("Items").push();
        itemsRef4.setValue(item4);
        DatabaseReference itemsRef5 = databaseRef.child("Items").push();
        itemsRef5.setValue(item5);
        DatabaseReference itemsRef6 = databaseRef.child("Items").push();
        itemsRef6.setValue(item6);

        DatabaseReference itemsRef7 = databaseRef.child("Items").push();
        itemsRef7.setValue(item7);
        DatabaseReference itemsRef8 = databaseRef.child("Items").push();
        itemsRef8.setValue(item8);
        DatabaseReference itemsRef9 = databaseRef.child("Items").push();
        itemsRef9.setValue(item9);

        // Create the stores
        Store store1 = new Store("StoreName", "StoreInfo", "StoreOwner", Arrays.asList(itemsRef.getKey(), itemsRef2.getKey(), itemsRef3.getKey()));
        Store store2 = new Store("StoreName1", "StoreInfo1", "StoreOwner1", Arrays.asList(itemsRef4.getKey(), itemsRef5.getKey(), itemsRef6.getKey()));
        Store store3 = new Store("StoreName2", "StoreInfo2", "StoreOwner2", Arrays.asList(itemsRef7.getKey(), itemsRef8.getKey(), itemsRef9.getKey()));

        // Push the stores to the "Stores" node in the database
        DatabaseReference storesRef = databaseRef.child("Stores").child(store1.getStoreName());
        storesRef.setValue(store1);
        DatabaseReference storesRef1 = databaseRef.child("Stores").child(store2.getStoreName());
        storesRef1.setValue(store2);
        DatabaseReference storesRef2 = databaseRef.child("Stores").child(store3.getStoreName());
        storesRef2.setValue(store3);
    }
}