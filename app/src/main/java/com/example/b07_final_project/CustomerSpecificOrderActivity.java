package com.example.b07_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.b07_final_project.classes.CurrentOrderData;

public class CustomerSpecificOrderActivity extends AppCompatActivity {
    private String orderID = CurrentOrderData.getInstance().getId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_specific_order);
    }
}