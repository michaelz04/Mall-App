package com.example.b07_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {
    String userId = CurrentUserData.getInstance().getId();
    FirebaseDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void onClickLogin(View view){
        //check for user id
        CurrentUserData.getInstance().setId("this is my id");
        Intent intent = new Intent(this, ItemActivity.class);
        startActivity(intent);
    }
}