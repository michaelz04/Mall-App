package com.example.b07_final_project;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07_final_project.classes.CurrentUserData;
import com.example.b07_final_project.classes.Item;
import com.example.b07_final_project.classes.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<Item> cartItemList;

    private List<String> cartItemListKey;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_item_layout);


        recyclerView = findViewById(R.id.recyclerViewCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartItemListKey = new ArrayList<>();
        cartItemList = new ArrayList<>();

        cartAdapter = new CartAdapter(cartItemList);
        recyclerView.setAdapter(cartAdapter);
        //final boolean[] addonce = {false};

        /*
        Populate cartItemList accordingly.
         */
        String userId = CurrentUserData.getInstance().getId();
        DatabaseReference db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").getReference();

        DatabaseReference cartRef = db.child("Shoppers").child(userId).child("cart");

        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartItemListKey.clear();
                cartItemList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot itemkey : snapshot.getChildren()) {
                        String itemId = itemkey.getKey();
                        cartItemListKey.add(itemId);
                    }

                    // After fetching cart item keys, fetch the item details
                    DatabaseReference itemRef = db.child("Items");
                    itemRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                                    String itemId = itemSnapshot.getKey();
                                    if (cartItemListKey.contains(itemId)) {
                                        Item item = itemSnapshot.getValue(Item.class);
                                        cartItemList.add(item);
                                        cartAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle error
                        }
                    });
                }

                else {
                    // Handle "No Items in cart currently" when the cart is empty
                    String errormsg = "No Items in cart currently";
                    Item empty = new Item(errormsg, "", 0.0f, "", "", "");
                    cartItemList.add(empty);
                    cartAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });


        Button checkoutButton = findViewById(R.id.checkoutButton);
        checkoutButton.setOnClickListener(v ->  {
         // Set Toast Message for now.
            Toast.makeText(CartActivity.this, "Checkout works", Toast.LENGTH_SHORT).show();

        });


    }
}
