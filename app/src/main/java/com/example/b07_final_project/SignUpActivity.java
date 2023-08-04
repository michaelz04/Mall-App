package com.example.b07_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.b07_final_project.classes.CurrentUserData;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.*;
import android.widget.*;

public class SignUpActivity extends AppCompatActivity {
    DatabaseReference db;
    TextInputEditText usernameText;
    TextInputEditText passwordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").getReference();
        usernameText = (TextInputEditText) findViewById(R.id.UsernameInput);
        passwordText = (TextInputEditText) findViewById(R.id.PasswordInput);
        // when the user presses 'done' on their keyboard, try logging in
        passwordText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) onClickSignupSignup(findViewById(R.id.SignupSignupButton));
                return false;
            }
        });
    }

    public void onClickSignupSignup(View view){
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
                                //username does not exist in either owners or shoppers so add to singleton class.

                                //check if password is empty
                                if (password.length() == 0){
//                                    ((TextView)findViewById(R.id.LoginFail)).setText("Password cannot be empty. Please try again.");
                                } else {
                                    CurrentUserData.getInstance().setId(username);
                                    CurrentUserData.getInstance().setPassword(password);
                                    startActivity(new Intent(SignUpActivity.this, AccountTypeActivity.class));
                                }
                            } else {
                                //username exists in shoppers so print username exists
                                Snackbar.make(view, "Username taken", 1000).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    //username exists in owners so print username exists
                    Snackbar.make(view, "Username taken", 1000).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onClickSignupLogin(View view){
        startActivity(new Intent(SignUpActivity.this, LoginActivityView.class));
    }
    @Override
    public void onBackPressed(){}
}

