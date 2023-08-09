package com.example.b07_final_project.classes;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import com.example.b07_final_project.LoginActivityView;

public class ToolbarNavigation {
    // Handle going back and logging out by providing the toolbar for each fragment/activity
    public static void set(FragmentActivity activity, Toolbar toolbar) {
        toolbar.setNavigationOnClickListener(v -> activity.onBackPressed());
        toolbar.setOnMenuItemClickListener(item -> {
            activity.startActivity(new Intent(activity, LoginActivityView.class));
            return true;
        });
    }
}
