package com.example.b07_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.b07_final_project.classes.CurrentUserData;
import com.google.firebase.database.*;

public class AccountTypeActivity extends AppCompatActivity {
    DatabaseReference db;
    String username = CurrentUserData.getInstance().getId();
    String password = CurrentUserData.getInstance().getPassword();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_type);
        db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").getReference();
    }

    @Override
    public void onBackPressed(){

    }
    public void onClickShopper(View view){
        //add username and password to Shoppers
        DatabaseReference userRef = db.child("Shoppers").child(username);
        CurrentUserData.getInstance().setAccountType("Shoppers");
        userRef.child("Password").setValue(password);
        startActivity(new Intent(AccountTypeActivity.this, storelist_customer.class));
    }

    public void onClickOwner(View view){
        //add username and password to Shoppers
        db.child("Owners").child(username).child("Password").setValue(password);
        CurrentUserData.getInstance().setAccountType("Owners");
        startActivity(new Intent(AccountTypeActivity.this, CreateStoreActivity.class));

    }
}