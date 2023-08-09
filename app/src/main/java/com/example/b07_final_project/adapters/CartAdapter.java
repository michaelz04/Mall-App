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
import com.example.b07_final_project.classes.CurrentUserData;
import com.example.b07_final_project.classes.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<Item> cartItemList;

    DatabaseReference db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").getReference();

    public CartAdapter(List<Item> cartItemList) {
        this.cartItemList = cartItemList;

    }


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
        float itemPrice = item.getPrice();
        String imageURL = item.getPicture();
        String errormsg = "No Items in cart currently";

        db.child("Shoppers")
                .child(CurrentUserData.getInstance().getId())
                .child("cart").child(item.getItemID())
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful() && !itemName.equals(errormsg)) {
                            int quantity = task.getResult().getValue(Integer.class);

                            holder.itemCard.setVisibility(View.VISIBLE);
                            holder.itemName.setText(itemName + " (" + Integer.toString(quantity) + ")");
                            holder.price.setText("$" + Float.toString(itemPrice * quantity));
                            try {
                                new URL(imageURL);
                                Picasso.get().load(imageURL).into(holder.image);
                            } catch (MalformedURLException e) {
                                Picasso.get().load(R.drawable.placeholder).into(holder.image);

                            }

                            holder.itemCard.setOnClickListener(v -> {
                                Intent intent = new Intent(v.getContext(), EditItemAmount.class);

                                intent.putExtra("item_id", item.getItemID());
                                intent.putExtra("item_name", item.getItemName());
                                intent.putExtra("store", item.getStoreKey());


                                v.getContext().startActivity(intent);

                            });

                        } else {
                            holder.itemCard.setVisibility(View.GONE);
                        }
                    }
                });


    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }
}