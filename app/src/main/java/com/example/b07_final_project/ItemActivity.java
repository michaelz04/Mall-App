package com.example.b07_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.b07_final_project.classes.CurrentUserData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class ItemActivity extends AppCompatActivity {
    //String userId = CurrentUserData.getInstance().getId();
    String itemId;
    DatabaseReference db;
    ImageView rImage;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_expand_item);
        itemId = getIntent().getStringExtra("item_id");
        toolbar = findViewById(R.id.expandItemToolbar);
        db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").getReference();

        // Make Add to Cart work.
        Button cartAdd = findViewById(R.id.addToCartButton);
        cartAdd.setOnClickListener(v ->  {
            onClickAdd(v);

        });

        if (CurrentUserData.getInstance().getAccountType().equals("Owners")) {
            findViewById(R.id.addToCartButton).setVisibility(View.GONE);
            findViewById(R.id.quantityInbox).setVisibility(View.GONE);
        }

//        Button goCart = findViewById(R.id.goToCart);
//        goCart.setOnClickListener(v -> {
//            Intent intent = new Intent(v.getContext(), CartActivity.class);
//            v.getContext().startActivity(intent);
//        });

        DatabaseReference queryItems = db.child("Items").child(itemId);
        rImage = findViewById(R.id.Item_Image);
        queryItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //retrieving values from database
                String itemDes = snapshot.child("description").getValue(String.class);
                String itemName = snapshot.child("itemName").getValue(String.class);
                Float itemPrice = snapshot.child("price").getValue(Float.class);
                String itemImage = snapshot.child("picture").getValue(String.class);
                ImageView image = findViewById(R.id.Item_Image);

                //displaying values to the user
                ((TextView)findViewById(R.id.Item_Description)).setText(itemDes);
                ((TextView)findViewById(R.id.Item_Name)).setText(itemName);
                ((TextView)findViewById(R.id.Item_Price)).setText("$" + itemPrice);
                try {
                    // if the image is a valid URL, show the image
                    new URL(itemImage);
                    Picasso.get().load(itemImage).into(image);
                } catch (MalformedURLException e) {
                    // otherwise show the placeholder
                    Picasso.get().load(R.drawable.placeholder).into(image);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    private void onClickAdd(View view) {
        // TODO: Implement code for the button
        String userId = CurrentUserData.getInstance().getId();
        DatabaseReference cartRef = db.child("Shoppers").child(userId).child("cart");
        cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Integer> currentCartItems;
                if(snapshot.exists()) {
                    currentCartItems = (HashMap<String, Integer>) snapshot.getValue();

                }
                else{
                    currentCartItems = new HashMap<String, Integer>();
                }
                if(currentCartItems.containsKey(itemId)) {
                    Toast.makeText(ItemActivity.this, "This item is already in your cart", Toast.LENGTH_SHORT).show();
                }
                else {
                    currentCartItems.put(itemId, 1);
                    cartRef.setValue(currentCartItems);
                    startActivity(new Intent(ItemActivity.this, CartActivity.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void onClickCart(View view) {
        // TODO: Implement code for the button
    }

    public void onClickBack(View view) {
        onBackPressed();
    }
}
