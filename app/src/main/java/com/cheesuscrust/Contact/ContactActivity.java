package com.cheesuscrust.Contact;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ContactActivity extends AppCompatActivity{

    //Navigation Drawer
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    UserData_Singleton userData = UserData_Singleton.getInstance();

    //create a object of input field
    EditText contact_fname,contact_lname,contact_email,contact_remail,contact_phone,contact_msg;

    //create contact table object
    contactTable database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set the title
        getSupportActionBar().setTitle(getString(R.string.contact_us));

        database = new contactTable(this);

        //initialise input fields
        //contact_date = (EditText) findViewById(R.id.contact_date);
        contact_fname = findViewById(R.id.contact_fname);
        contact_lname = findViewById(R.id.contact_lname);
        contact_email = findViewById(R.id.contact_email);
        contact_remail = findViewById(R.id.conatct_remail);
        contact_phone = findViewById(R.id.contact_phone);
        contact_msg = findViewById(R.id.contact_message);

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
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.nav_dashboard :
                        menuItem.setChecked(true);
                        Intent intent0 = new Intent(ContactActivity.this, activity_dash.class);
                        startActivity(intent0);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_user_profile :
                        menuItem.setChecked(true);
                        Intent intent1 = new Intent(ContactActivity.this, UserProfile.class);
                        startActivity(intent1);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_contact_us :
                        menuItem.setChecked(true);
                        Toast.makeText(ContactActivity.this, getString(R.string.you_are_already_in_contact_us), Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_settings :
                        menuItem.setChecked(true);
                        Toast.makeText(ContactActivity.this, R.string.settings_are_not_available_right_now, Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_logout:
                        AlertDialog.Builder builder = new AlertDialog.Builder(ContactActivity.this);
                        builder.setCancelable(true);
                        builder.setTitle(getString(R.string.are_you_sure_you_want_to_logout));
                        builder.setPositiveButton(getString(R.string.logout), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.cheesus_crust), MODE_PRIVATE);
                                sharedPreferences.edit().remove(String.valueOf(R.string.logged)).apply();
                                sharedPreferences.edit().remove(String.valueOf(R.string.email)).apply();
                                Intent intent3 = new Intent(ContactActivity.this, WelcomeScreen.class);
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

    public void send(View view)
    {
        //get values
        String fnameValue = contact_fname.getText().toString();
        String lnameValue = contact_lname.getText().toString();
        String emailValue = contact_email.getText().toString();
        String remailValue = contact_remail.getText().toString();
        String phoneValue = contact_phone.getText().toString();
        String msgValue= contact_msg.getText().toString();

        if(TextUtils.isEmpty(fnameValue))
        {
            contact_fname.setError("Please enter first name");
            return;
        }

        if(TextUtils.isEmpty(lnameValue))
        {
            contact_lname.setError("Please enter last name");
            return;
        }

        if(TextUtils.isEmpty(remailValue))
        {
            contact_remail.setError("Please enter last re enter email");
            return;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()){
            contact_email.setError("Please enter a valid email address");
            return;
        }


        if(TextUtils.isEmpty(emailValue))
        {
            contact_email.setError("Please enter email");
            return;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()){
            contact_email.setError("Please enter a valid email address");
            return;
        }


        if(TextUtils.isEmpty(msgValue))
        {
            contact_msg.setError("Please enter message");
            return;
        }

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String dateValue = sdf.format(cal.getTime());

        boolean result = database.insertData(dateValue,fnameValue,lnameValue,emailValue,remailValue,phoneValue,msgValue);

        if(!result)
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();

        else
            Toast.makeText(this, "We will get back to you soon", Toast.LENGTH_LONG).show();
    }
}
