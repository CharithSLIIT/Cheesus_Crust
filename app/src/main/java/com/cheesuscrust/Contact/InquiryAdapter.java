package com.cheesuscrust.Contact;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cheesuscrust.Database.Cheesus_Crust_Db;
import com.cheesuscrust.R;

import java.util.List;

public class InquiryAdapter extends ArrayAdapter<Inquiry> {

    private Context mCtx;
    private int layoutRes;
    private List<Inquiry> inqList;

    //database object
    private Cheesus_Crust_Db database;

    InquiryAdapter(Context mCtx, int layoutRes, List<Inquiry> inqList, Cheesus_Crust_Db database) {
        super(mCtx, layoutRes, inqList);

        this.mCtx = mCtx;
        this.layoutRes = layoutRes;
        this.inqList = inqList;
        this.database = database;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        @SuppressLint("ViewHolder") View view = inflater.inflate(layoutRes, null);

        TextView textViewDate = view.findViewById(R.id.textViewDate);
        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewEmail = view.findViewById(R.id.textViewEmail);
        TextView textViewPhone = view.findViewById(R.id.textViewPhone);
        TextView textViewMsg = view.findViewById(R.id.textViewMsg);
        final TextView textViewStatus = view.findViewById(R.id.textViewStatus);


        final Inquiry inquiry = inqList.get(position);

        textViewDate.setText(inquiry.getDate());
        textViewName.setText(inquiry.getFname() + " " + inquiry.getLname());
        textViewEmail.setText(inquiry.getEmail());
        textViewPhone.setText(inquiry.getPhone());
        textViewMsg.setText(inquiry.getMsg());
        textViewStatus.setText(inquiry.getStatus());

        final int getId = inquiry.getId();

        view.findViewById(R.id.buttonEditInquiry).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                boolean result = database.updateInquiry(getId);

                if(result)
                {
                    Toast.makeText(mCtx, R.string.inquiry_completed, Toast.LENGTH_SHORT).show();
                    textViewStatus.setText(R.string.complete);
                }

                else
                    Toast.makeText(mCtx, R.string.error, Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.buttonDeleteInquiry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = database.deleteInquiry(getId);

                if(result)
                {
                    Toast.makeText(mCtx, R.string.inquiry_deleted, Toast.LENGTH_SHORT).show();
                }

                else
                    Toast.makeText(mCtx, R.string.error_, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
