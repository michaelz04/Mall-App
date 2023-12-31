package com.example.b07_final_project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07_final_project.adapters.CartAdapter;
import com.example.b07_final_project.classes.CurrentUserData;
import com.example.b07_final_project.classes.Item;
import com.example.b07_final_project.classes.OrderStores;
import com.example.b07_final_project.classes.Orders;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<Item> cartItemList;
    private List<String> cartItemListKey;
    private DatabaseReference db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").getReference();

    public interface GetStoreKeyCallback {
        void onStoreKeyReceived(String storeKey);
    }

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
                } else {

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
        checkoutButton.setOnClickListener(v -> {
            DatabaseReference ordersRef = db.child("Orders").push();
            String orderId = ordersRef.getKey();

            cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Map<String, OrderStores> storesMap = new HashMap<>(); //Will be all the stores and items
                        Set<String> uniqueStoreKeys = new HashSet<>(); //To check if all the stores are added.
                        HashSet<String> ownersOfOrders = new HashSet<>();

                        for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                            String itemId = itemSnapshot.getKey();
                            int quantity = itemSnapshot.getValue(Integer.class);
                            getStoreKey(itemId, new GetStoreKeyCallback() {
                                @Override
                                public void onStoreKeyReceived(String storeKey) {
                                    if (storeKey != null) {
                                        OrderStores orderStores = storesMap.get(storeKey);
                                        if (orderStores == null) {
                                            orderStores = new OrderStores(new HashMap<>(), false);
                                            storesMap.put(storeKey, orderStores);
                                            uniqueStoreKeys.add(storeKey);

                                        }

                                        orderStores.getItems().put(itemId, quantity);

                                        if (uniqueStoreKeys.size() == storesMap.size()) {
                                            //Set the orders in the database
                                            Orders orders = new Orders(orderId, storesMap);
                                            db.child("Orders").child(orderId).setValue(orders);
                                            //Remove Cart after purchase.
                                            cartRef.removeValue();
                                            cartAdapter.notifyDataSetChanged();

                                            //Add to current user orders in database:
                                            String currentUser = CurrentUserData.getInstance().getId();
                                            DatabaseReference userRef = db.child("Shoppers").child(currentUser);
                                            userRef.child("orders").child(orderId).setValue(orderId);

                                            //Add orders to owners in database
                                            DatabaseReference storeRef = db.child("Stores");
                                            storeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for(DataSnapshot stores: snapshot.getChildren()) {
                                                        if(uniqueStoreKeys.contains(stores.getKey())) {
                                                            String ownername = stores.child("storeOwner").getValue(String.class);
                                                            ownersOfOrders.add(ownername);

                                                        }
                                                    }

                                                    DatabaseReference ownersRef = db.child("Owners");
                                                    for (String orderowner : ownersOfOrders) {
                                                        ownersRef.child(orderowner).child("orders").child(orderId).setValue(orderId);

                                                    }
                                                    Toast.makeText(CartActivity.this, "Checkout successful", Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                        }
                                    }
                                }
                            });
                        }
                    }
                    else {
                        // Cart is empty
                        Toast.makeText(CartActivity.this, "Empty Cart", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error
                }
            });
        });
    }

    private void getStoreKey(String itemId, GetStoreKeyCallback callback) {
        DatabaseReference storedb = db.child("Items");

        storedb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot itemkey : snapshot.getChildren()) {
                        if (itemkey.getKey().equals(itemId)) {
                            Item item = itemkey.getValue(Item.class);
                            if (item != null) {
                                String storeKey = item.getStoreKey();
                                callback.onStoreKeyReceived(storeKey);
                            } else {
                                // Item with given itemId not found
                                callback.onStoreKeyReceived(null);
                            }
                            return;
                        }
                    }
                }

                callback.onStoreKeyReceived(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
                callback.onStoreKeyReceived(null);
            }
        });
    }
}