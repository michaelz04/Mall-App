package com.example.b07_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.*;
import android.widget.*;

public class SignUpActivity extends AppCompatActivity {
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").getReference();
    }

    public void onClickRegister(View view){
        //grab username and password from input
        EditText usernameText = (EditText) findViewById(R.id.UsernameInput);
        String username = usernameText.getText().toString();
        EditText passwordText = (EditText) findViewById(R.id.PasswordInput);
        String password = passwordText.getText().toString();

        DatabaseReference queryOwners = db.child("Owners").child(username);

        queryOwners.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    //if username not in owners, check shoppers
                    DatabaseReference queryShoppers = db.child("Shoppers").child(username);
                    queryShoppers.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.exists()){
                                //username does not exist in either owners or shoppers so add to singleton class.
                                CurrentUserData.getInstance().setId(username);
                                CurrentUserData.getInstance().setPassword(password);
                                startActivity(new Intent(SignUpActivity.this, AccountTypeActivity.class));
                            } else {
                                //username exists in shoppers so print username exists
                                ((TextView)findViewById(R.id.LoginFail)).setText("Username already exists. Please try again.");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    //username exists in owners so print username exists
                    ((TextView)findViewById(R.id.LoginFail)).setText("Username already exists. Please try again.");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onClickLogin(View view){
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
    }
}

