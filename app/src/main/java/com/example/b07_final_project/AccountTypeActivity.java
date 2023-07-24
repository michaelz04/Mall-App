package com.example.b07_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.*;

public class AccountTypeActivity extends AppCompatActivity {
    DatabaseReference db;
    String username = CurrentUserData.getInstance().getId();
    String password = CurrentUserData.getInstance().getPassword();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_type);
        db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").getReference();
    }

    public void onClickShopper(View view){

        DatabaseReference queryOwners = db.child("Shoppers").child(username);

        queryOwners.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //add username and password to Shoppers


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
}