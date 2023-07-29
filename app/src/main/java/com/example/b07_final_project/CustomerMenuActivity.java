package com.example.b07_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CustomerMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_menu);
    }
    public void onClickStore(View view){
        startActivity(new Intent(CustomerMenuActivity.this, StoreListActivity.class));
    }
    public void onClickOrder(View view){
        //TODO make order interface
    }
}