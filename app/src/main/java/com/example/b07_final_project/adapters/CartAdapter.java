package com.example.b07_final_project.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07_final_project.EditItemAmount;
import com.example.b07_final_project.R;
import com.example.b07_final_project.classes.Item;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<Item> cartItemList;
    //private OnItemClickListener itemClickListener;

    // Constructor with the OnItemClickListener parameter
    public CartAdapter(List<Item> cartItemList) {
        this.cartItemList = cartItemList;
        //this.itemClickListener = itemClickListener;
    }


    /*public interface OnItemClickListener {
        void onItemClick(Item item);
    }*/

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button cartButtonView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartButtonView = itemView.findViewById(R.id.itemorder);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = cartItemList.get(position);
        String itemName = item.getItemName();
        String errormsg = "No Items in cart currently";
        if(!itemName.equals(errormsg)) {
            String buttonText = item.getItemName() + " - " + item.getDescription() + " - $" + item.getPrice();
            holder.cartButtonView.setText(buttonText);

            holder.cartButtonView.setOnClickListener(v ->  {
                Intent intent = new Intent(v.getContext(), EditItemAmount.class);

                intent.putExtra("item_id", item.getItemID());
                intent.putExtra("item_name", item.getItemName());
                intent.putExtra("store", item.getStoreKey());


                v.getContext().startActivity(intent);

            });

        }
        else {
            holder.cartButtonView.setText(errormsg);
            holder.cartButtonView.setOnClickListener(null);
            holder.cartButtonView.setClickable(false);
        }
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }
}