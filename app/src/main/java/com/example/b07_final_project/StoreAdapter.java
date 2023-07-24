package com.example.b07_final_project;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    private List<Store> storeList;


    public StoreAdapter(List<Store> storeList) {
        this.storeList = storeList;
    }

    // ViewHolder class to hold the views for each store item
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
        String buttonText = store.getName() + " - " + store.getInfo() + " - " + store.getOwner();
        holder.storeButton.setText(buttonText);

        // Set click listener for the button if needed

        holder.storeButton.setOnClickListener(v -> {
            // Handle button click, you can open a new activity or fragment to show the store's items
            //Toast.makeText(v.getContext(), store.getName(), Toast.LENGTH_SHORT).show();


            Intent intent = new Intent(v.getContext(), StoreItemsActivityView.class);
            intent.putExtra("store_id", store.getName()); // Assuming you have a getKey() method in the Store class
            v.getContext().startActivity(intent);



        });
    }


    @Override
    public int getItemCount() {
        return storeList.size();
    }
}