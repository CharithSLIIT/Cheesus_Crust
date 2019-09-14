package com.cheesuscrust.Contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cheesuscrust.R;

import java.util.List;

public class InquiryAdapter extends ArrayAdapter<Inquiry> {

    //Button deleteInq;

    Context mCtx;
    int layoutRes;
    List<Inquiry> inqList;

    //database object
    contactTable database;

    public InquiryAdapter(Context mCtx, int layoutRes, List<Inquiry> inqList, contactTable database) {
        super(mCtx, layoutRes, inqList);

        this.mCtx = mCtx;
        this.layoutRes = layoutRes;
        this.inqList = inqList;
        this.database = database;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(layoutRes, null);

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
            @Override
            public void onClick(View view) {
                boolean result = database.updateInquiry(getId);

                if(result == true)
                {
                    Toast.makeText(mCtx, "Inquiry Completed!", Toast.LENGTH_SHORT).show();
                    textViewStatus.setText("Complete");
                }

                else
                    Toast.makeText(mCtx, "Error!!", Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.buttonDeleteInquiry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = database.deleteInquiry(getId);

                if(result == true)
                {
                    Toast.makeText(mCtx, "Inquiry Deleted!", Toast.LENGTH_SHORT).show();
                }

                else
                    Toast.makeText(mCtx, "Error!!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
//
//    private void updateEmployee( Inquiry inquiry) {
//       AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
//        LayoutInflater inflater = LayoutInflater.from(mCtx);
//         View view = inflater.inflate(R.layout.activity_dialog_update_inquiry, null);
//        builder.setView(view);
//
//        final AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//
//        final EditText editTextStatus = view.findViewById(R.id.editTextStatus);
//
//
//        editTextStatus.setText(inquiry.getStatus());
//
//
//
//        view.findViewById(R.id.buttonUpdateInquiry).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String status = editTextStatus.getText().toString().trim();
//
//
//                if (status.isEmpty()) {
//                    editTextStatus.setError("status can't be empty");
//                    editTextStatus.requestFocus();
//                    return;
//                }
//
//
//
//
//                //calling the update method from database manager instance
//                if (database.updateInquiry(inquiry.getId(), status)) {
//                    Toast.makeText(mCtx, "Inquiry Updated", Toast.LENGTH_SHORT).show();
//                    loadInquiryFromDatabaseAgain();
//                }
//                alertDialog.dismiss();
//            }
//        });
//    }
//
//
//    private void deleteInquiry(final Inquiry inquiry) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
//        builder.setTitle("Are you sure?");
//
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                //calling the delete method from the database manager instance
//                if (database.deleteInquiry(inquiry.getId()))
//                    loadInquiryFromDatabaseAgain();
//            }
//        });
//
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }
//
//    private void loadInquiryFromDatabaseAgain() {
//        //calling the read method from database instance
//        Cursor cursor = database.getAllInquries();
//
//        inqList.clear();
//        if (cursor.moveToFirst()) {
//            do {
//                inqList.add(new Inquiry(
//                        cursor.getInt(0),
//                        cursor.getString(1),
//                        cursor.getString(2),
//                        cursor.getString(3),
//                        cursor.getString(4),
//                        cursor.getString(5),
//                        cursor.getString(6),
//                        cursor.getString(7),
//                        cursor.getString(8)
//                ));
//            } while (cursor.moveToNext());
//        }
//        notifyDataSetChanged();
//
//
//    }

}
