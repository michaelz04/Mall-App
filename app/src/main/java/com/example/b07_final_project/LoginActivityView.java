package com.example.b07_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

public class LoginActivityView extends AppCompatActivity {
    DatabaseReference db;
    TextInputEditText usernameText;
    TextInputEditText passwordText;
    LoginPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenter(this, new LoginModel());

        db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").getReference();
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