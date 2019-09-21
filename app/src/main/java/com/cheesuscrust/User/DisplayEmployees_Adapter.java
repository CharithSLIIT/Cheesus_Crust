package com.cheesuscrust.User;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cheesuscrust.R;

import java.util.List;

public class DisplayEmployees_Adapter extends RecyclerView.Adapter<DisplayEmployees_Adapter.EmployeesViewHolder> {

    private List<Users> users;

    DisplayEmployees_Adapter(List<Users> users)
    {
        this.users = users;
    }

    @NonNull
    @Override
    public EmployeesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.display_employees_adapter_layout, parent, false);
        return new EmployeesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeesViewHolder holder, int position) {

        Users employee = users.get(position);

        holder.emp_name_display.setText(employee.getUser_name());
        holder.emp_email_display.setText(employee.getUser_email());
        holder.emp_phone_display.setText(employee.getUser_phone());
        holder.emp_address_display.setText(employee.getUser_address());
        holder.emp_position.setText(employee.getUser_position());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class EmployeesViewHolder extends RecyclerView.ViewHolder
    {
        TextView emp_name_display;
        TextView emp_email_display;
        TextView emp_phone_display;
        TextView emp_address_display;
        TextView emp_position;

        EmployeesViewHolder(@NonNull View itemView) {
            super(itemView);
            emp_name_display = itemView.findViewById(R.id.display_employees_recyclerView_name);
            emp_email_display = itemView.findViewById(R.id.display_employees_recyclerView_email);
            emp_phone_display = itemView.findViewById(R.id.display_employees_recyclerView_phone);
            emp_address_display = itemView.findViewById(R.id.display_employees_recyclerView_address);
            emp_position = itemView.findViewById(R.id.display_employees_recyclerView_type);
        }

    }
}
