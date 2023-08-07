package com.example.b07_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.b07_final_project.adapters.StoreAdapter;
import com.example.b07_final_project.classes.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StoreListActivity extends AppCompatActivity {
    private List<Store> storeList;
    private StoreAdapter adapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storelist_customer);

        recyclerView = findViewById(R.id.recyclerView);
       // Log.d("storelist_customer", "RecyclerView initialized");
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        storeList = new ArrayList<>();

        adapter = new StoreAdapter(storeList);
        recyclerView.setAdapter(adapter);


        //DatabaseReference dbRef = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/");
        DatabaseReference dbRef = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").getReference("Stores");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                storeList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot store : snapshot.getChildren()) {
                        String storename = store.child("storeName").getValue(String.class);
                        String storeinfo = store.child("description").getValue(String.class);
                        String storeowner = store.child("storeOwner").getValue(String.class);
                        String picture = store.child("picture").getValue(String.class);
                        List<String> storeitems = new ArrayList<>();
                        for (DataSnapshot item : store.child("items").getChildren()) {
                            storeitems.add(item.getValue(String.class));
                        }

                        Store createStore = new Store(storename, storeinfo, storeowner,
                                storeitems, picture);

                        storeList.add(createStore);


                    }


                }
                else{
                    String errormsg= "No Stores currently";
                    Store empty = new Store(errormsg, "", "", new ArrayList<>(), "");
                    storeList.add(empty);

                }
                adapter.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Error stuff
            }
        });



    }
    @Override
    public void onBackPressed(){

    }
}