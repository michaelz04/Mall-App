package com.example.b07_final_project;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07_final_project.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StoreItemsActivityView extends AppCompatActivity {

    private List<Item> itemList;
    private ItemAdapter adapter;
    private List <String> itemIDs;
    private String storeId; // Store ID received from the previous activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_items_view);

        //Error is that getting the StoreID from a previous activity is hard, might need to change
        // Store class
        // Get the store ID passed from the previous activity somehow find some code.
        storeId = getIntent().getStringExtra("store_id");

        // Initialize the RecyclerView and ItemAdapter
        RecyclerView recyclerView = findViewById(R.id.recyclerViewItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();
        adapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(adapter);

        DatabaseReference storeRef = FirebaseDatabase.getInstance().getReference("Stores").child(storeId);
        storeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Store store = snapshot.getValue(Store.class);
                itemIDs = store.getItems();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });


        DatabaseReference itemsRef = FirebaseDatabase.getInstance().getReference("Items");
        itemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    String itemId = itemSnapshot.getKey();
                    if(itemIDs.contains(itemId)) {
                        Item item = itemSnapshot.getValue(Item.class);
                        itemList.add(item);


                    }
                }
               // Log.d("Size", String.valueOf(itemList.size()));

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }

    // Helper method to check if the store contains the given item ID

}