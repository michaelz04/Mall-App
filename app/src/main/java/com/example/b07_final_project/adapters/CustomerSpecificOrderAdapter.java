package com.example.b07_final_project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07_final_project.R;
import com.example.b07_final_project.classes.Item;
import com.example.b07_final_project.classes.OrderStores;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class CustomerSpecificOrderAdapter extends RecyclerView.Adapter<CustomerSpecificOrderAdapter.ViewHolder> {

    private final List<OrderStores> orderStores;
    // I need this list too, because OrderStores class does not store the name of the store :(
    private final List<String> storeNames;

    FirebaseDatabase db = FirebaseDatabase.getInstance("https://test-54768-default-rtdb.firebaseio.com/");

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final View container;

        public ViewHolder(View view) {
            super(view);
            container = (CardView) view.findViewById(R.id.customer_specific_order_card);
        }

        public View getView() {
            return container;
        }

    }

    public CustomerSpecificOrderAdapter(List<OrderStores> orderStores, List<String> storeNames) {
        this.orderStores = orderStores;
        this.storeNames = storeNames;
    }

    @NonNull
    @Override
    public CustomerSpecificOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ui_customer_specific_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerSpecificOrderAdapter.ViewHolder holder, int position) {
        OrderStores orderStore = orderStores.get(position);
        String storeName = storeNames.get(position);

        TextView store = holder.getView().findViewById(R.id.order_store);
        TextView status = holder.getView().findViewById(R.id.store_status);
        TextView price = holder.getView().findViewById(R.id.total_store_price);

        // For lack of a better method
        // The idea is that the card view has two linear layouts (defined below). As we loop through
        // the items, we add a new textview onto each linear layout
        final float[] totalPrice = {0.0f};
        LinearLayout itemsList = holder.getView().findViewById(R.id.customer_items_from_store);
        LinearLayout pricesList = holder.getView().findViewById(R.id.customer_prices_from_store);

        for (String item: orderStore.getItems().keySet()) {
            // Creating the new views to put into linear layout
            TextView itemView = new TextView(holder.getView().getContext());
            TextView priceView = new TextView(holder.getView().getContext());
            db.getReference("Items").child(item).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    Item newItem = task.getResult().getValue(Item.class);
                    itemView.setText(newItem.getItemName());
                    priceView.setText("$" + Float.toString(newItem.getPrice()));
                    // Numbers aligned to right
                    priceView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

                    // Adding them on to the linear layout
                    itemsList.addView(itemView);
                    pricesList.addView(priceView);

                    // I don't really want to set this here, but I don't know how to set it once because
                    // The function creates a new thread
                    totalPrice[0] += newItem.getPrice();
                    price.setText("$" + Float.toString(totalPrice[0]));
                }
            });
        }

        store.setText(storeName);
        if (orderStore.isStatus())
            status.setText("Complete");
        else
            status.setText("Incomplete");
    }

    @Override
    public int getItemCount() {
//        if (orderStores.size() == 0) return 1;
        return orderStores.size();
    }
}
