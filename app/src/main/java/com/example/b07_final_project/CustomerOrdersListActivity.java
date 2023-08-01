package com.example.b07_final_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.b07_final_project.adapters.CustomerOrderAdapter;
import com.example.b07_final_project.classes.CurrentUserData;
import com.example.b07_final_project.classes.Orders;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CustomerOrdersListActivity extends AppCompatActivity {
    String user = CurrentUserData.getInstance().getId();
    List<Orders> ordersList;
    CustomerOrderAdapter adapter;
    FirebaseDatabase db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_order_recycle_view);

        ordersList = new ArrayList<>();
        adapter = new CustomerOrderAdapter(ordersList);

        RecyclerView listView = findViewById(R.id.customers_order_list);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ordersList.clear();

        // go through the list of orders for current user
        DatabaseReference orderKeys = db.getReference("Shoppers").child(user).child("orders");
        orderKeys.addChildEventListener(new ChildEventListener() {

            // for every order, get the corresponding order from the database and add it to the list to
            // send to adapter
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String orderID = snapshot.getKey();
                if (orderID == null) return;

                db.getReference("Orders").child(orderID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        Orders order = task.getResult().getValue(Orders.class);
                        if (order != null) {
                            ordersList.add(task.getResult().getValue(Orders.class));
                            adapter.notifyDataSetChanged();
//                            adapter.notifyItemInserted(ordersList.size());
                        }
                    }
                });
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