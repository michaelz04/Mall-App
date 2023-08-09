package com.example.b07_final_project.adapters;

import android.content.Intent;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.*;

import com.example.b07_final_project.OwnerIndividualOrderActivity;
import com.example.b07_final_project.classes.CurrentOrderData;
import com.example.b07_final_project.classes.CurrentStoreData;
import com.example.b07_final_project.classes.CurrentUserData;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07_final_project.R;

import java.util.*;

public class OwnerOrderAdapter extends RecyclerView.Adapter<OwnerOrderAdapter.ViewHolder>{

    List<String> orderList;
    List<Boolean> statusList;
    DatabaseReference db;
    String username = CurrentUserData.getInstance().getId();
    String storeId = CurrentStoreData.getInstance().getId();


    public OwnerOrderAdapter(List<String> orderList, List<Boolean> statusList){
        this.orderList = orderList;
        this.statusList = statusList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView card;
        TextView orderId;
        TextView doneText;

        public ViewHolder(@NonNull View OwnerOrdersView) {
            super(OwnerOrdersView);
            card = OwnerOrdersView.findViewById(R.id.OwnerOrderCard);
            orderId = OwnerOrdersView.findViewById(R.id.OrderIDText);
            doneText = OwnerOrdersView.findViewById(R.id.OrderStatusDone);
        }
    }

    @NonNull
    @Override
    public OwnerOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.owner_orders_recycle_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerOrderAdapter.ViewHolder holder, int position) {
        String orderId = orderList.get(position);

        String text = Integer.toString(position);
        Log.d("test", text);

        if (orderId.equals("empty")){
//            String buttonText = "No orders";
//            holder.orderButton.setText(buttonText);
//            holder.orderButton.setClickable(false);
//            holder.statusText.setText("");
//            holder.doneText.setText("");
        } else {
            boolean orderStatus = statusList.get(position);

            if (orderStatus){
                holder.doneText.setText("Done");
                holder.doneText.setTextColor(Color.GREEN);
            } else {
                holder.doneText.setText("Not Done");
                holder.doneText.setTextColor(Color.RED);
            }

            String orderIdTrim = orderId.substring(0, 15);
            holder.orderId.setText("Order ID #" + orderIdTrim);

            // Set click listener
            holder.card.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), OwnerIndividualOrderActivity.class);
                CurrentOrderData.getInstance().setId(orderId);
                v.getContext().startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


}
