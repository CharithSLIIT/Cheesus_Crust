package com.cheesuscrust.Contact;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.cheesuscrust.R;
import com.cheesuscrust.User.UserData_Singleton;
import com.cheesuscrust.User.UserProfile;
import com.cheesuscrust.User.WelcomeScreen;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class InquiryActivity extends AppCompatActivity {

    //Navigation Drawer
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    UserData_Singleton userData = UserData_Singleton.getInstance();

    List<Inquiry> inqList;
    ListView listView;

    //database object
    contactTable database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set the title
        getSupportActionBar().setTitle(getString(R.string.view_inquiries));

        //Instantiating the database manager object
        database = new contactTable(this);

        inqList = new ArrayList<>();
        listView = findViewById(R.id.listViewInquiry);

        loadInquriesFromDatabase();

        //Navigation menu icon
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.navigation_drawer_icon);

        //Navigation Drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        //Show Dashboard option
        if (userData.getUser_type().equals(getString(R.string.admin)))
        {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.nav_dashboard).setVisible(true);
            menu.findItem(R.id.nav_products).setVisible(false);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.nav_dashboard :
                        menuItem.setChecked(true);
                        Intent intent0 = new Intent(InquiryActivity.this, activity_dash.class);
                        startActivity(intent0);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_products :
                        menuItem.setChecked(true);
                        Toast.makeText(InquiryActivity.this, getString(R.string.customer_product_menu_is_not_accessible), Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_user_profile :
                        menuItem.setChecked(true);
                        Intent intent1 = new Intent(InquiryActivity.this, UserProfile.class);
                        startActivity(intent1);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_contact_us :
                        menuItem.setChecked(true);
                        Intent intent3 = new Intent(InquiryActivity.this, ContactActivity.class);
                        startActivity(intent3);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_settings :
                        menuItem.setChecked(true);
                        Toast.makeText(InquiryActivity.this, R.string.settings_are_not_available_right_now, Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_logout:
                        AlertDialog.Builder builder = new AlertDialog.Builder(InquiryActivity.this);
                        builder.setCancelable(true);
                        builder.setTitle(getString(R.string.are_you_sure_you_want_to_logout));
                        builder.setPositiveButton(getString(R.string.logout), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.cheesus_crust), MODE_PRIVATE);
                                sharedPreferences.edit().remove(String.valueOf(R.string.logged)).apply();
                                sharedPreferences.edit().remove(String.valueOf(R.string.email)).apply();
                                Intent intent3 = new Intent(InquiryActivity.this, WelcomeScreen.class);
                                startActivity(intent3);
                                return;
                            }
                        });
                        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                        builder.show();
                        return true;
                }
                return false;
            }
        });
    }

    //Navigation Drawer Display icon
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
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

//    public void invalidateV()
//    {
//        listView.invalidate();
//    }

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
