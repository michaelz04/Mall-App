package com.example.b07_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CustomerActivityBase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_base);
    }
    public void onClickStore(View view){
        startActivity(new Intent(CustomerActivityBase.this, storelist_customer.class));
    }
    public void onClickOrder(View view){
        startActivity(new Intent(CustomerActivityBase.this, CustomerOrdersListActivity.class));
    }
}