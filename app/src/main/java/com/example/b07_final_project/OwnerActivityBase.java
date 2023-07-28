package com.example.b07_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.b07_final_project.classes.CurrentUserData;
import com.example.b07_final_project.classes.Owner;
import com.google.firebase.database.DatabaseReference;

public class OwnerActivityBase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_base);

    }
    public void onClickStoreview(View view){
        // go to activity StoreItemsActivityView
        startActivity(new Intent(OwnerActivityBase.this, StoreItemsActivityView.class));
    }
    public void onClickOrder(View view){
        //TODO create order history activity to switch to
    }

}