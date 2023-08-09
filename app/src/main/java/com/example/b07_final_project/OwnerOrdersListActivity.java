package com.example.b07_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.example.b07_final_project.adapters.OwnerOrderAdapter;
import com.example.b07_final_project.classes.CurrentStoreData;
import com.example.b07_final_project.classes.CurrentUserData;
import com.example.b07_final_project.classes.ToolbarNavigation;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class OwnerOrdersListActivity extends AppCompatActivity {

    private List<String> orderList;
    private List<Boolean> statusList;
    private OwnerOrderAdapter adapter;
    DatabaseReference db;
    String username = CurrentUserData.getInstance().getId();
    String storeId = CurrentStoreData.getInstance().getId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_orders_list);

        ToolbarNavigation.set(this, findViewById(R.id.ordersListToolbar));

        RecyclerView recyclerView = findViewById(R.id.OwnerOrdersRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderList = new ArrayList<>();
        statusList = new ArrayList<>();

        adapter = new OwnerOrderAdapter(orderList, statusList);
        recyclerView.setAdapter(adapter);

        db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").
                getReference();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Owners").child(username).
                        child("orders").exists()){
                    for(DataSnapshot order : snapshot.child("Owners").child(username).
                            child("orders").getChildren()){

                        String orderId = order.getKey();
                        if (orderId != null){
                            boolean orderStatus = (Boolean) snapshot.child("Orders").child(orderId).
                                    child("stores").child(storeId).child("status").getValue();
                            orderList.add(orderId);
                            statusList.add(orderStatus);
                        }

                    }
                }

                if (orderList.isEmpty()){
                    orderList.add("empty");
                }


                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}