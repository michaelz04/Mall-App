package com.example.b07_final_project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.b07_final_project.classes.CurrentUserData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EditItemAmount extends AppCompatActivity {

    private DatabaseReference db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item_amount);
        String itemId = getIntent().getStringExtra("item_id");
        String itemName = getIntent().getStringExtra("item_name");
        String store = getIntent().getStringExtra("store");

        String descriptionMessage = "You are currently changing the quantity of " + itemName +
                " from Store: " + store;
        TextView descriptionTextView = findViewById(R.id.itemDetailsTextView);
        descriptionTextView.setText(descriptionMessage);

        //Set up the buttons (remove and confirm) both interacting with database.
        Button removeButton = findViewById(R.id.removeButton);
        removeButton.setOnClickListener(v -> {
            String userId = CurrentUserData.getInstance().getId();
            DatabaseReference cartRef = db.child("Shoppers").child(userId).child("cart");
            cartRef.child(itemId).removeValue();
            Toast.makeText(this, "Item removed", Toast.LENGTH_SHORT).show();

        });

        Button confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(v -> {
            EditText quantityEditText = findViewById(R.id.quantityEditText);
            String quantity = quantityEditText.getText().toString();

            if (!quantity.isEmpty()) {
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt > 0) {
                    String userId = CurrentUserData.getInstance().getId();
                    DatabaseReference cartRef = db.child("Shoppers").child(userId).child("cart");
                    cartRef.child(itemId).setValue(quantityInt);
                    Toast.makeText(this, "Quantity updated to " + quantityInt, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Please enter a valid quantity (greater than 0)", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please enter a quantity", Toast.LENGTH_SHORT).show();
            }

        });
    }
}