package com.example.b07_final_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07_final_project.classes.Item;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> itemList;

    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    // ViewHolder class to hold the views for each item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button itemButtonView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemButtonView = itemView.findViewById(R.id.itemButtonView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_description, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);
        String buttonText = item.getItemName() + " - " + item.getDescription() + " - $" + item.getPrice();
        holder.itemButtonView.setText(buttonText);

        // Set click listener for the button to handle the item click
        holder.itemButtonView.setOnClickListener(v -> {
            // Handle the item click, for now, let's just show a Toast with the item name
            Toast.makeText(v.getContext(), item.getItemName(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}