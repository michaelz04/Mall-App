package com.example.b07_final_project.adapters;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07_final_project.R;
import com.example.b07_final_project.StoreItemsListActivity;
import com.example.b07_final_project.classes.CurrentStoreData;
import com.example.b07_final_project.classes.Store;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    private List<Store> storeList;


    public StoreAdapter(List<Store> storeList) {
        this.storeList = storeList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView storeButton;
        TextView storeName;
        TextView description;

        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            storeButton = itemView.findViewById(R.id.storeCard);
            storeName = itemView.findViewById(R.id.storeName);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.imageView);
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
        String image = "https://www.kurin.com/wp-content/uploads/placeholder-square.jpg";

        if (!storename.equals(errormsg)) {
            holder.storeName.setText(store.getStoreName());
            holder.description.setText(store.getDescription());
            try {
                // if the image is a valid URL, show the image
                new URL(image);
                Picasso.get().load(image).into(holder.image);
            } catch (MalformedURLException e) {
                // otherwise show the placeholder
                Picasso.get().load(R.drawable.placeholder).into(holder.image);
            }

            // Set click listener
            holder.storeButton.setOnClickListener(v -> {


                Intent intent = new Intent(v.getContext(), StoreItemsListActivity.class);
                //intent.putExtra("store_id", store.getStoreName());
                CurrentStoreData.getInstance().setId(store.getStoreName());
                v.getContext().startActivity(intent);


            });
        }
        else{
            holder.storeButton.setVisibility(GONE);
        }
    }


    @Override
    public int getItemCount() {
        return storeList.size();
    }
}