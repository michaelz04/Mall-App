package com.example.b07_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

public class StoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
    }

    public void changeFragment(Class<? extends  Fragment> changeTo) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.storeActivityFragment, changeTo, null)
                .commit();
    }

    public void onClickMyStoresButton(View view) {
        changeFragment(MyStoresFragment.class);
    }

    public void onClickStoreItemsButton(View view) {
        changeFragment(StoreItemsFragment.class);
    }

    public void onClickAddStoreButton(View view) {
        changeFragment(AddStoreFragment.class);
    }
}