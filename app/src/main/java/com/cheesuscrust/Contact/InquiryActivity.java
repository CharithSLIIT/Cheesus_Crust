package com.cheesuscrust.Contact;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.cheesuscrust.R;

import java.util.ArrayList;
import java.util.List;

public class InquiryActivity extends AppCompatActivity {

    List<Inquiry> inqList;
    ListView listView;

    //database object
    contactTable database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);

        //Instantiating the database manager object
        database = new contactTable(this);

        inqList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listViewInquiry);

        loadInquriesFromDatabase();
    }

    private void loadInquriesFromDatabase() {
        Cursor cursor = database.getAllInquries();

        if (cursor.moveToFirst()) {
            //looping through all the records
            do {
                inqList.add(new Inquiry(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8)
                ));
            } while (cursor.moveToNext());

            //passing the databasemanager instance this time to the adapter
            InquiryAdapter adapter = new InquiryAdapter(this, R.layout.activity_list_layout_inquiry, inqList, database);
            listView.setAdapter(adapter);
        }

    }

    public void invalidateV()
    {
        listView.invalidate();
    }

//    private void updateInquiry(final Inquiry inquiry) {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
//
//        LayoutInflater inflater = LayoutInflater.from(mCtx);
//        View view = inflater.inflate(R.layout.activity_dialog_update_inquiry, null);
//        builder.setView(view);
//
//
//        final EditText editTextStatus = view.findViewById(R.id.editStatus);
//
//
//        editTextName.setText(inquiry.getStatus());
//
//
//        final AlertDialog dialog = builder.create();
//        dialog.show();
//
//        view.findViewById(R.id.buttonUpdateInquiry).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String status = editTextStatus.getText().toString().trim();
//
//
//                if (status.isEmpty()) {
//                    editTextName.setError("Status can't be blank");
//                    editTextName.requestFocus();
//                    return;
//                }
//
//
//                String sql = "UPDATE contact \n" +
//                        "SET con_status = ?, \n" +
//                        "WHERE con_id = ?;\n";
//
//                database.execSQL(sql, new String[]{status, String.valueOf(inquiry.getId())});
//                Toast.makeText(mCtx, "Inquiry Updated", Toast.LENGTH_SHORT).show();
//                reloadInquiryFromDatabase();
//
//
//                dialog.dismiss();
//            }
//        });
//
//
//    }
//
//    private void reloadInquiryFromDatabase() {
//        Cursor cursorInquiry = database.getAllInquries();
//        if (cursorInquiry.moveToFirst()) {
//            inqList.clear();
//            do {
//                inqList.add(new Inquiry(
//                        cursorInquiry.getInt(0),
//                        cursorInquiry.getString(1),
//                        cursorInquiry.getString(2),
//                        cursorInquiry.getString(3),
//                        cursorInquiry.getString(4),
//                        cursorInquiry.getString(5),
//                        cursorInquiry.getString(6),
//                        cursorInquiry.getString(7),
//                        cursorInquiry.getString(8)
//
//                ));
//            } while (cursorInquiry.moveToNext());
//        }
//        cursorInquiry.close();
//        notifyDataSetChanged();
//    }
//
//    private void notifyDataSetChanged() {
//    }


}
