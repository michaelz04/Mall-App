package com.example.b07_final_project;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07_final_project.classes.CurrentItemData;
import com.example.b07_final_project.classes.CurrentStoreData;
import com.example.b07_final_project.classes.CurrentUserData;
import com.example.b07_final_project.classes.Item;

import java.util.List;

public class OwnerIndvOrderAdapter extends RecyclerView.Adapter<OwnerIndvOrderAdapter.ViewHolder> {

    private List<String> OrderItemList;
    private List<Item> OrderItem;
    String username = CurrentUserData.getInstance().getId();
    String storeId = CurrentStoreData.getInstance().getId();

    public OwnerIndvOrderAdapter(List<String> orderItemList, List<Item> OrderItem) {
        this.OrderItemList = orderItemList;
        this.OrderItem = OrderItem;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button itemButton;
        public ViewHolder(@NonNull View OwnerOrderView) {
            super(OwnerOrderView);
            itemButton = OwnerOrderView.findViewById(R.id.itemOrder);

        }
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.owner_orders_recycle_row, parent, false);
        return new OwnerIndvOrderAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String itemID = OrderItemList.get(position);
        String text = Integer.toString(position);

        if(itemID.equals("empty")){
            String buttonText = "No Items";
            holder.itemButton.setText(buttonText);
            holder.itemButton.setClickable(false);
        }
        else{
            String buttonText = OrderItem.get(position).getItemName()+ "; price: $"+OrderItem.get(position).getPrice();
            holder.itemButton.setText(buttonText);
            holder.itemButton.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), ItemActivity.class);
                CurrentItemData.getInstance().setId(itemID);
                v.getContext().startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return OrderItem.size();
    }
}
