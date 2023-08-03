package com.example.b07_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;

import com.example.b07_final_project.classes.CurrentStoreData;
import com.example.b07_final_project.classes.CurrentUserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.*;

public class LoginActivity extends AppCompatActivity {
    DatabaseReference db;
    TextInputEditText usernameText;
    TextInputEditText passwordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").getReference();
        usernameText = (TextInputEditText) findViewById(R.id.UsernameInput);
        passwordText = (TextInputEditText) findViewById(R.id.PasswordInput);
        // when the user presses 'done' on their keyboard, try logging in
        passwordText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) onClickLoginLogin(findViewById(R.id.LoginLoginButton));
                return false;
            }
        });
    }

    public void onClickLoginLogin(View view){
        //check for user id

        // if empty, show error
        if (usernameText.getText() == null || passwordText.getText() == null) {
            Snackbar.make(view, "Fields cannot be empty", 1000).show();
            return;
        }
        //grab username and password from input
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        // if empty, show error
        if (username.isEmpty() || password.isEmpty()) {
            Snackbar.make(view, "Fields cannot be empty", 1000).show();
            return;
        }

        //query owners
        DatabaseReference queryOwners = db.child("Owners").child(username);
        queryOwners.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    //if username not in owners, check shoppers
                    DatabaseReference queryShoppers = db.child("Shoppers").child(username);
                    queryShoppers.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.exists()){
                                //username does not exist in either owners or shoppers
                                Snackbar.make(view, "Username does not exist", 1000).show();
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
                                                    Snackbar.make(view, "Incorrect password", 1000).show();
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
                        Snackbar.make(view, "Incorrect password", 1000).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onClickLoginSignup(View view){
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }

    @Override
    public void onBackPressed(){}
}