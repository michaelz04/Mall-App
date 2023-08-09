package com.example.b07_final_project.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07_final_project.EditItemAmount;
import com.example.b07_final_project.R;
import com.example.b07_final_project.classes.Item;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
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
        MaterialCardView itemCard;
        TextView itemName;
        TextView price;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCard = itemView.findViewById(R.id.itemCard);
            itemName = itemView.findViewById(R.id.itemName);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.imageView);
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
            holder.itemCard.setVisibility(View.VISIBLE);
            holder.itemName.setText(itemName);
            Picasso.get().load(item.getPicture()).into(holder.image);

            holder.itemCard.setOnClickListener(v ->  {
                Intent intent = new Intent(v.getContext(), EditItemAmount.class);

                intent.putExtra("item_id", item.getItemID());
                intent.putExtra("item_name", item.getItemName());
                intent.putExtra("store", item.getStoreKey());


                v.getContext().startActivity(intent);

            });

        }
        else {
//            holder.itemCard.setText(errormsg);
//            holder.cartButtonView.setOnClickListener(null);
//            holder.cartButtonView.setClickable(false);
            holder.itemCard.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }
}