package com.example.b07_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class storelist_customer extends AppCompatActivity {
    private List<Store> storeList;
    private StoreAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storelist_customer);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Log.d("storelist_customer", "RecyclerView initialized");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        storeList = new ArrayList<>();

        adapter = new StoreAdapter(storeList);
        recyclerView.setAdapter(adapter);

        //Change this line for whatever the actual database uses,
        // either the url or the path of Stores
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Stores");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                storeList.clear(); //Comment this out if u have future errors.
                for (DataSnapshot store : snapshot.getChildren()) {
                    String storename= store.child("name").getValue(String.class);
                    String storeinfo= store.child("info").getValue(String.class);
                    String storeowner= store.child("owner").getValue(String.class);
                    List<String> storeitems = new ArrayList<>();
                    for(DataSnapshot item: store.child("items").getChildren()) {
                        storeitems.add(item.getValue(String.class));
                    }

                    Store createStore = new Store(storename, storeinfo, storeowner,
                            storeitems);
                   /* ArrayList empty = new ArrayList();
                    Store createStore = new Store(storename, storeinfo, storeowner, empty);*/
                    storeList.add(createStore);
                    Log.d("StoreListSize", "Store List Size: " + storeList.size());
                    //adapter.notifyDataSetChanged();

                }
               adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Error stuff
            }
        });

    }
}