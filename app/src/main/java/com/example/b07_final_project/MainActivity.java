package com.example.b07_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_type_view);

        //AddDataActivity.addStoreToDatabase();
        //Comment below to make sure it doesnt break.
        Intent intent = new Intent(this, storelist_customer.class);
        startActivity(intent);
        finish();
    }
}