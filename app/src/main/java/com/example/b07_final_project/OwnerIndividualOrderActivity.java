package com.example.b07_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07_final_project.adapters.OwnerIndvOrderAdapter;
import com.example.b07_final_project.classes.CurrentOrderData;
import com.example.b07_final_project.classes.CurrentStoreData;
import com.example.b07_final_project.classes.CurrentUserData;
import com.example.b07_final_project.classes.Item;
import com.example.b07_final_project.classes.ToolbarNavigation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OwnerIndividualOrderActivity extends AppCompatActivity {
    private List<String> OrderItemList;
    private List<Item> ItemList;
    private List<Integer>OrderQuantity;
    private boolean orderStatus;
    String username = CurrentUserData.getInstance().getId();
    String storeId = CurrentStoreData.getInstance().getId();
    String orderID = CurrentOrderData.getInstance().getId();
    private OwnerIndvOrderAdapter adapter;
    DatabaseReference db;

    Button finish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_owner_ind_order_layout);

        RecyclerView recyclerView = findViewById(R.id.ownerIndvOrderRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Toolbar toolbar = findViewById(R.id.individualOrderToolbar);
        ToolbarNavigation.set(this, toolbar);

        OrderItemList = new ArrayList<String>();
        ItemList = new ArrayList<>();
        OrderQuantity = new ArrayList<Integer>();
        finish = findViewById(R.id.finish);

        adapter = new OwnerIndvOrderAdapter(OrderItemList,ItemList,OrderQuantity);
        recyclerView.setAdapter(adapter);

        db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").
                getReference();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ItemList.clear();
                OrderItemList.clear();
                OrderQuantity.clear();

                for (DataSnapshot item : snapshot.child("Orders").child(orderID).
                        child("stores").child(storeId).child("items").getChildren()) {
                    //adjust for more quantities of items

                    String itemID = item.getKey();
                    if (itemID != null) {
                        OrderItemList.add(itemID);
                        Integer itemQuantity = (Integer) item.getValue(Integer.class);
                        OrderQuantity.add(itemQuantity);

                    }
                }


                for(int i = 0; i< OrderItemList.size();i++){
                        Item temp = (Item) snapshot.child("Items").child(OrderItemList.get(i)).getValue(Item.class);
                        ItemList.add(temp);

                }

                orderStatus = (Boolean) snapshot.child("Orders").child(orderID).
                        child("stores").child(storeId).child("status").getValue();
                String status = "Done";
                if(!orderStatus)status = "Not Done";

                toolbar.setTitle("Order ID #" + orderID);
                if(OrderItemList.isEmpty()){
                    OrderItemList.add("empty");
                    OrderQuantity.add(0);
                }
                adapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //TODO remove finish button and add check if status is not done, then add the button and button listener -- conditional button



    }
    public void onClickFinish(View view){

        orderStatus = true;
        FirebaseDatabase.getInstance().getReference("Orders").child(orderID).child("stores").child(storeId).child("status").setValue(true);
        startActivity(new Intent(OwnerIndividualOrderActivity.this, OwnerOrdersListActivity.class));


    }


}