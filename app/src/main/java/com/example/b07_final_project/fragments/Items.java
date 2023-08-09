package com.example.b07_final_project.fragments;

import static android.view.View.GONE;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.b07_final_project.LoginActivityView;
import com.example.b07_final_project.NewItemActivity;
import com.example.b07_final_project.R;
import com.example.b07_final_project.adapters.ItemAdapter;
import com.example.b07_final_project.classes.CurrentStoreData;
import com.example.b07_final_project.classes.CurrentUserData;
import com.example.b07_final_project.classes.Item;
import com.example.b07_final_project.classes.Store;
import com.example.b07_final_project.classes.ToolbarNavigation;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Items extends Fragment {

    private List<Item> itemList;
    private ItemAdapter adapter;
    private List <String> itemIDs;
    private String storeId; // Store ID received from the previous activity
    public Items() {
        super(R.layout.fragment_items);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        storeId = CurrentStoreData.getInstance().getId();
        ((CollapsingToolbarLayout) requireActivity().findViewById(R.id.collapsingToolbar)).setTitle(storeId);

        // Initialize the RecyclerView and ItemAdapter
        RecyclerView recyclerView = requireView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        itemList = new ArrayList<>();
        adapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = view.findViewById(R.id.toolbar);

        FloatingActionButton addItemButton = view.findViewById(R.id.addItemButton);

        if (CurrentUserData.getInstance().getAccountType().equals("Owners")) {
            addItemButton.setVisibility(View.VISIBLE);
            addItemButton.setOnClickListener(l -> {
                startActivity(new Intent(requireContext(), NewItemActivity.class));
            });
            toolbar.setNavigationIcon(null);
        }
        else {
            addItemButton.setVisibility(View.GONE);
            toolbar.setNavigationIcon(R.drawable.outline_arrow_back_24);
        }


        ToolbarNavigation.set(getActivity(), toolbar);

        final boolean[] addonce = {false};

        DatabaseReference storeRef = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").getReference("Stores").child(storeId);
//        DatabaseReference storeRef = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").child(storeId);
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


        // DatabaseReference itemsRef = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/");
        DatabaseReference itemsRef = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").getReference("Items");
        itemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    String itemId = itemSnapshot.getKey();
                    if (itemIDs != null) {
                        if (itemIDs.contains(itemId)) {
                            Item item = itemSnapshot.getValue(Item.class);
                            itemList.add(item);

                        }
                    }
                }

                if (itemIDs == null && !addonce[0]) {
                    String errormsg = "No Items in the Store";
                    Item empty = new Item(errormsg, "", 0.0f, "", "", "");
                    itemList.add(empty);
                    addonce[0] = true;
                }

                // Log.d("Size", String.valueOf(itemList.size()));
                https://upload.wikimedia.org/wikipedia/commons/thumb/8/8a/Banana-Single.jpg/2324px-Banana-Single.jpg
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }
}