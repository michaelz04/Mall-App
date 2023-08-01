package com.example.b07_final_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.b07_final_project.adapters.CustomerSpecificOrderAdapter;
import com.example.b07_final_project.classes.CurrentOrderData;
import com.example.b07_final_project.classes.OrderStores;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerSpecificOrderActivity extends AppCompatActivity {
    private String orderID = CurrentOrderData.getInstance().getId();

    private List<OrderStores> orderStores;
    private List<String> storeNames;
    CustomerSpecificOrderAdapter storesAdapter;

    FirebaseDatabase db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_order_recycle_view);

        orderStores = new ArrayList<>();
        storeNames = new ArrayList<>();
        storesAdapter = new CustomerSpecificOrderAdapter(orderStores, storeNames);

        RecyclerView storesListView = findViewById(R.id.customers_order_list);
        storesListView.setLayoutManager(new LinearLayoutManager(this));
        storesListView.setAdapter(storesAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        orderStores.clear();
        storeNames.clear();

        DatabaseReference storeKeys = db.getReference("Orders").child(orderID).child("stores");
        storeKeys.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                OrderStores orderStore = snapshot.getValue(OrderStores.class);
                orderStores.add(orderStore);
                String storeName = snapshot.getKey();
                storeNames.add(storeName);
                storesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}