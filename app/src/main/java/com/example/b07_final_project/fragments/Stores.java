package com.example.b07_final_project.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.b07_final_project.R;
import com.example.b07_final_project.adapters.StoreAdapter;
import com.example.b07_final_project.classes.Store;
import com.example.b07_final_project.classes.UserUI;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Stores extends Fragment {
    private List<Store> storeList;
    private StoreAdapter adapter;
    private RecyclerView recyclerView;
    public Stores() {
        super(R.layout.fragment_stores);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stores, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = requireView().findViewById(R.id.recyclerView);
        // Log.d("storelist_customer", "RecyclerView initialized");
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
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
}