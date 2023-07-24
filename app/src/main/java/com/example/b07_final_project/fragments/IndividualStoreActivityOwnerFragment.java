package com.example.b07_final_project.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.b07_final_project.NewItemActivity;
import com.example.b07_final_project.R;

public class IndividualStoreActivityOwnerFragment extends Fragment {

    public IndividualStoreActivityOwnerFragment() {
        super(R.layout.fragment_individual_store_activity_owner);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_individual_store_activity_owner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize a onClick listener
        view.findViewById(R.id.goToAddItemButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToAddItem = new Intent(getActivity(), NewItemActivity.class);
                startActivity(goToAddItem);
            }
        });
    }
}