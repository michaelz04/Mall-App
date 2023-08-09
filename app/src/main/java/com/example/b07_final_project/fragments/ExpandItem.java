package com.example.b07_final_project.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07_final_project.CartActivity;
import com.example.b07_final_project.ItemActivity;
import com.example.b07_final_project.LoginActivityView;
import com.example.b07_final_project.R;
import com.example.b07_final_project.ShopperUI;
import com.example.b07_final_project.adapters.ItemAdapter;
import com.example.b07_final_project.classes.CurrentStoreData;
import com.example.b07_final_project.classes.CurrentUserData;
import com.example.b07_final_project.classes.Item;
import com.example.b07_final_project.classes.Store;
import com.example.b07_final_project.classes.ToolbarNavigation;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandItem extends Fragment {
    String itemId;
    DatabaseReference db;
    ImageView rImage;
    public ExpandItem(String itemId) {
        super(R.layout.fragment_expand_item);
        this.itemId = itemId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expand_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ToolbarNavigation.set(getActivity(), view.findViewById(R.id.expandItemToolbar));

        db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/").getReference();

        // Make Add to Cart work.
        Button cartAdd = view.findViewById(R.id.addToCartButton);
        cartAdd.setOnClickListener(v ->  {
            onClickAdd(v);
        });

        Button cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(v ->  {
            requireActivity().onBackPressed();
        });

        if (CurrentUserData.getInstance().getAccountType().equals("Owners")) {
            view.findViewById(R.id.addToCartButton).setVisibility(View.GONE);
            view.findViewById(R.id.quantityInbox).setVisibility(View.GONE);
        }

        DatabaseReference queryItems = db.child("Items").child(itemId);
        rImage = view.findViewById(R.id.Item_Image);
        queryItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //retrieving values from database
                String itemDes = snapshot.child("description").getValue(String.class);
                String itemName = snapshot.child("itemName").getValue(String.class);
                Float itemPrice = snapshot.child("price").getValue(Float.class);
                String itemImage = snapshot.child("picture").getValue(String.class);
                ImageView image = view.findViewById(R.id.Item_Image);

                //displaying values to the user
                ((TextView)view.findViewById(R.id.Item_Description)).setText(itemDes);
                ((TextView)view.findViewById(R.id.Item_Name)).setText(itemName);
                ((TextView)view.findViewById(R.id.Item_Price)).setText("$" + itemPrice);
                try {
                    // if the image is a valid URL, show the image
                    new URL(itemImage);
                    Picasso.get().load(itemImage).into(image);
                } catch (MalformedURLException e) {
                    // otherwise show the placeholder
                    Picasso.get().load(R.drawable.placeholder).into(image);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void onClickAdd(View view) {
        // TODO: Implement code for the button
        String userId = CurrentUserData.getInstance().getId();
        DatabaseReference cartRef = db.child("Shoppers").child(userId).child("cart");
        cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Integer> currentCartItems;
                if(snapshot.exists()) {
                    currentCartItems = (HashMap<String, Integer>) snapshot.getValue();

                }
                else{
                    currentCartItems = new HashMap<String, Integer>();
                }
                if(currentCartItems.containsKey(itemId)) {
                    Toast.makeText(getContext(), "This item is already in your cart", Toast.LENGTH_SHORT).show();
                    requireActivity().onBackPressed();
                }
                else {
                    currentCartItems.put(itemId, 1);
                    cartRef.setValue(currentCartItems);
                    Toast.makeText(getContext(), "The item is successfully added to your cart.", Toast.LENGTH_SHORT).show();
                    requireActivity().onBackPressed();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}