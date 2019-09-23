package com.cheesuscrust.User;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cheesuscrust.R;

import java.util.List;

public class DisplayCustomers_Adapter extends RecyclerView.Adapter<DisplayCustomers_Adapter.CustomerViewHolder> {

    private List<Users> users;

    DisplayCustomers_Adapter(List<Users> users)
    {
        this.users = users;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.display_customers_adapter_layout, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {

        Users customer = users.get(position);

        holder.cus_name_display.setText(customer.getUser_name());
        holder.cus_email_display.setText(customer.getUser_email());
        holder.cus_phone_display.setText(customer.getUser_phone());
        holder.cus_address_display.setText(customer.getUser_address());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class CustomerViewHolder extends RecyclerView.ViewHolder
    {
        TextView cus_name_display;
        TextView cus_email_display;
        TextView cus_phone_display;
        TextView cus_address_display;

        CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            cus_name_display = itemView.findViewById(R.id.display_customers_recyclerView_name);
            cus_email_display = itemView.findViewById(R.id.display_customers_recyclerView_email);
            cus_phone_display = itemView.findViewById(R.id.display_customers_recyclerView_phone);
            cus_address_display = itemView.findViewById(R.id.display_customers_recyclerView_address);
        }

    }
}
