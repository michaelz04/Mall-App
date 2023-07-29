package com.example.b07_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.b07_final_project.classes.CurrentStoreData;
import com.example.b07_final_project.classes.CurrentUserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.*;

public class LoginActivity extends AppCompatActivity {
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").getReference();

    }

    public void onClickLogin(View view){
        //check for user id

        //grab username and password from input
        EditText usernameText = (EditText) findViewById(R.id.UsernameInput);
        String username = usernameText.getText().toString();
        EditText passwordText = (EditText) findViewById(R.id.PasswordInput);
        String password = passwordText.getText().toString();

        //query owners
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
                                //username does not exist in either owners or shoppers
                                ((TextView)findViewById(R.id.LoginFail)).setText("Username does not exist");
                            } else {
                                //username exists in shoppers so check password
                                //get password from database
                                db.child("Shoppers").child(username).child("password").get().
                                        addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                String databasePassword = String.valueOf(task.getResult().getValue());
                                                //check if password and password from database is correct
                                                if (databasePassword.equals(password)){
                                                    //set current user id
                                                    CurrentUserData currentUserData = CurrentUserData.getInstance();
                                                    currentUserData.setId(username);
                                                    currentUserData.setAccountType("Shoppers");
                                                    startActivity(new Intent(LoginActivity.this, CustomerMenuActivity.class));
                                                } else {
                                                    ((TextView)findViewById(R.id.LoginFail)).setText("Password incorrect");
                                                }
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    //username exists in owners so check password
                    //get password from database
                    String databasePassword = snapshot.child("password").getValue(String.class);
                    if (databasePassword.equals(password)){
                        //set current user id
                        CurrentUserData currentUserData = CurrentUserData.getInstance();
                        currentUserData.setId(username);
                        currentUserData.setAccountType("Owners");
                        //set current store instance
                        String ownerStoreID = snapshot.child("storeKey").getValue(String.class);
                        // If the store doesn't exist, send the owner to create the store
                        if (ownerStoreID == null || ownerStoreID.isEmpty()) {
                            startActivity(new Intent(LoginActivity.this, CreateStoreActivity.class));
                            return;
                        }
                        CurrentStoreData.getInstance().setId(ownerStoreID);

                        startActivity(new Intent(LoginActivity.this, OwnerMenuActivity.class));
                    } else {
                        ((TextView)findViewById(R.id.LoginFail)).setText("Password incorrect");
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onClickSignup(View view){
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }
}