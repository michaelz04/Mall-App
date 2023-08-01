package com.example.b07_final_project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07_final_project.R;
import com.example.b07_final_project.classes.Orders;

import java.util.List;

public class CustomerOrderAdapter extends RecyclerView.Adapter<CustomerOrderAdapter.ViewHolder> {
    private final List<Orders> orders;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // The container is the parent card view from ui_customer_order.xml
        // We use this as our parent view for each order
        private final View container;

        public ViewHolder(View view) {
            super(view);
            container = (CardView) view.findViewById(R.id.customer_order_card);
        }

        public View getView() {
            return container;
        }

    }


    public CustomerOrderAdapter(List<Orders> orders) {
        this.orders = orders;
    }

    // Inflater turns layout into view?? // TODO: Check if this is true
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ui_customer_order, parent, false);
        // ViewHolder holds the specific "card" for each order
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView status = holder.getView().findViewById(R.id.order_status);
        TextView id = holder.getView().findViewById(R.id.order_id);
        TextView id_label = holder.getView().findViewById(R.id.order_id_label);

        // When the View is Empty
        if (orders.isEmpty()) {
            status.setText("You have no orders");
            id_label.setText("");
            return;
        }

        Orders order = orders.get(position);
        id.setText(order.getOrderID());
        id_label.setText("Ordering ID:");

        // Colors :D // FIXME: Change bc Ish is creating a colorscheme, use that
        if (order.getOrderStatus()) {
            status.setTextColor(holder.getView().getContext().getResources().getColor(android.R.color.holo_green_dark));
            status.setText("Complete");
        }
        else {
            status.setTextColor(holder.getView().getContext().getResources().getColor(android.R.color.holo_red_dark));
            status.setText("Incomplete");
        }

        // Keybinding // TODO: Implement switching to view for single order
        holder.getView().setOnClickListener(v -> {
            Toast t = Toast.makeText(v.getContext(), order.getOrderID(), Toast.LENGTH_LONG);
            t.show();
        });
    }

    @Override
    public int getItemCount() {
        // I never return 0 so that the onBindViewHolder method will always be called. This way
        // I can add a card to show that there are no orders
        if (orders.size() == 0) return 1;
        return orders.size();
    }

}
