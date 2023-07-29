package com.example.b07_final_project.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07_final_project.ItemActivity;
import com.example.b07_final_project.R;
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
            String itemName = item.getItemName();
            String errormsg= "No Items in the Store";
            if(!itemName.equals(errormsg)) {
                String buttonText = item.getItemName() + " - " + item.getDescription() + " - $" + item.getPrice();
                holder.itemButtonView.setText(buttonText);

                // Set click listener
                holder.itemButtonView.setOnClickListener(v -> {
                    Intent intent = new Intent(v.getContext(), ItemActivity.class);
                    intent.putExtra("item_id", item.getItemID());
                    v.getContext().startActivity(intent);
                });
            }

            else{
                holder.itemButtonView.setText(errormsg);
                holder.itemButtonView.setOnClickListener(null);
                holder.itemButtonView.setClickable(false);
            }

           // Log.d("test", String.valueOf(position));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}