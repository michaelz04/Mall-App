package com.example.b07_final_project;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;


public class LoginActivityView extends AppCompatActivity {
    TextInputEditText usernameText;
    TextInputEditText passwordText;
    LoginPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenter(this, new LoginModel());

        usernameText = (TextInputEditText) findViewById(R.id.UsernameInput);
        passwordText = (TextInputEditText) findViewById(R.id.PasswordInput);
    }

    public void onClickLoginLogin(View view){
        //grab username and password from input
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        presenter.checkFields(username, password);
    }
    public void showSnackbar(String prompt){
        Snackbar.make(findViewById(android.R.id.content), prompt, 1000).show();
    }
    public void startNewActivity(Class newActivityClass){
        startActivity(new Intent(LoginActivityView.this, newActivityClass));
    }
    public void onClickLoginSignup(View view){
        startActivity(new Intent(LoginActivityView.this, SignUpActivity.class));
    }
    @Override
    public void onBackPressed(){}
}