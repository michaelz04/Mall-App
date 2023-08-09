package com.example.b07_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.b07_final_project.fragments.Cart;
import com.example.b07_final_project.fragments.Stores;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationBarView;

public class ShopperUI extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    private int currentFragmentLayout;
    private BottomNavigationView navView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopper_ui);

        currentFragmentLayout = R.id.shopper_fragment;

        navView = findViewById(R.id.nav_view);
        navView.setOnItemSelectedListener(this);
        navView.setSelectedItemId(R.id.navigation_shop);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_shop, R.id.navigation_cart, R.id.navigation_orders)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_shopper_ui);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (navView.getSelectedItemId() == R.id.navigation_orders) {
            setFragment(new Stores());
            navView.setSelectedItemId(R.id.navigation_shop);
        }
    }

    public void setFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(currentFragmentLayout, fragment)
                .addToBackStack(null)
                .commit();
        currentFragmentLayout = fragment.getId();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); ++i)
            getSupportFragmentManager().popBackStack();
        int itemId = item.getItemId();
        if (itemId == R.id.navigation_shop) {
            setFragment(new Stores());
            return true;
        }
        if (itemId == R.id.navigation_cart) {
            setFragment(new Cart());
            return true;
        }
        if (itemId == R.id.navigation_orders) {
            startActivity(new Intent(ShopperUI.this, CustomerOrdersListActivity.class));
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 1) {
            getSupportFragmentManager().popBackStack();
        }
    }
}