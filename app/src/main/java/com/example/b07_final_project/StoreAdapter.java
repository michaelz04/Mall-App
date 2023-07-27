package com.example.b07_final_project;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07_final_project.classes.CurrentStoreData;
import com.example.b07_final_project.classes.Store;

import java.util.ArrayList;
import java.util.List;


public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    private List<Store> storeList;


    public StoreAdapter(List<Store> storeList) {
        this.storeList = storeList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button storeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            storeButton = itemView.findViewById(R.id.storeButton);
        }
    }

    // Create a new ViewHolder and inflate the stores_for_customers layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stores_for_customers, parent, false);
        return new ViewHolder(view);
    }

    // Bind the data for each store item to its views
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Store store = storeList.get(position);
        String storename = store.getStoreName();
        String errormsg = "No Stores currently";

        if (!storename.equals(errormsg)) {
            String buttonText = store.getStoreName() + " - " + store.getDescription() +
                    " - " + store.getStoreOwner(); //+ " - " + temp.size();
            holder.storeButton.setText(buttonText);

            // Set click listener

            holder.storeButton.setOnClickListener(v -> {


                Intent intent = new Intent(v.getContext(), StoreItemsActivityView.class);
                intent.putExtra("store_id", store.getStoreName());
                CurrentStoreData.getInstance().setId(store.getStoreName());
                v.getContext().startActivity(intent);


            });
        }
        else{
            holder.storeButton.setText(errormsg);
            holder.storeButton.setOnClickListener(null);
            holder.storeButton.setClickable(false);
        }
    }


    @Override
    public int getItemCount() {
        return storeList.size();
    }
}