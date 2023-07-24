package com.example.b07_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateStoreActivity extends AppCompatActivity {
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_store);
        db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").getReference();
    }

    public void onClickCreateStore(View view) {
        // When the Create store button is clicked...

        // Get user's input
        String storeName = ((EditText) findViewById(R.id.editTextStoreName))
                .getText()
                .toString();
        String storeDescription = ((EditText) findViewById(R.id.editTextStoreDescription))
                .getText()
                .toString();

        // TODO: Add the store to DB
    }
}