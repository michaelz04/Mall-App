package com.example.b07_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ItemActivity extends AppCompatActivity {
    String userId = CurrentUserData.getInstance().getId();
    String itemId = CurrentItemData.getInstance().getId();
    DatabaseReference db;
    ImageView rImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").getReference();
        DatabaseReference queryItems = db.child("Items").child(itemId);
        rImage = findViewById(R.id.Item_Image);
        queryItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //retrieving values from database
                String itemDes = snapshot.child("description").getValue(String.class);
                String itemName = snapshot.child("itemName").getValue(String.class);
                Integer itemPrice = snapshot.child("price").getValue(Integer.class);
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
}
