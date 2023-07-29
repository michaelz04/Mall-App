package com.example.b07_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //AppDataActivity.addStoreToDatabase();

        Intent intent = new Intent(this, StoreListActivity.class);
        startActivity(intent);
        finish();
    }
}