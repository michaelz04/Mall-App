package com.example.b07_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.b07_final_project.classes.CurrentItemData;
import com.example.b07_final_project.classes.CurrentUserData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemActivity extends AppCompatActivity {
    //String userId = CurrentUserData.getInstance().getId();
    String itemId;
    DatabaseReference db;
    ImageView rImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        itemId = getIntent().getStringExtra("item_id");
        db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").getReference();

        // Make Add to Cart work.
        Button cartAdd = findViewById(R.id.addToCartButton);
        cartAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAdd();
            }
        });

        Button goCart = findViewById(R.id.goToCart);
        goCart.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), CartActivity.class);
            v.getContext().startActivity(intent);
        });

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

                //displaying values to the user
                ((TextView)findViewById(R.id.Item_Description)).setText(itemDes);
                ((TextView)findViewById(R.id.Item_Name)).setText(itemName);
                ((TextView)findViewById(R.id.Item_Price)).setText(String.valueOf(itemPrice));
                Picasso.get().load(itemImage).into(rImage);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void onClickAdd() {
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
                currentCartItems.put(itemId,1);
                cartRef.setValue(currentCartItems);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void onClickCart() {
        // TODO: Implement code for the button
    }
}
