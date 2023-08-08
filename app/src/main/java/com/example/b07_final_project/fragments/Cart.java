package com.example.b07_final_project.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07_final_project.R;
import com.example.b07_final_project.adapters.CartAdapter;
import com.example.b07_final_project.adapters.StoreAdapter;
import com.example.b07_final_project.classes.Item;
import com.example.b07_final_project.classes.Store;
import com.example.b07_final_project.classes.ToolbarNavigation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Cart extends Fragment {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<Item> cartItemList;
    private List<String> cartItemListKey;
    private DatabaseReference db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").getReference();
    public Cart() {
        super(R.layout.fragment_cart);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    public interface GetStoreKeyCallback {
        void onStoreKeyReceived(String storeKey);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ToolbarNavigation.set(getActivity(), view.findViewById(R.id.toolbar));

        recyclerView = findViewById(R.id.recyclerViewCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartItemListKey = new ArrayList<>();
        cartItemList = new ArrayList<>();

        cartAdapter = new CartAdapter(cartItemList);
        recyclerView.setAdapter(cartAdapter);

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