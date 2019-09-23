package com.cheesuscrust.Contact;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.cheesuscrust.Database.Cheesus_Crust_Db;
import com.cheesuscrust.R;
import com.cheesuscrust.User.UserData_Singleton;
import com.cheesuscrust.User.UserProfile;
import com.cheesuscrust.User.WelcomeScreen;
import com.cheesuscrust.Product.HomeActivity;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import static java.lang.String.valueOf;

public class ContactActivity extends AppCompatActivity{

    //notification
    private final String CHANNEL_ID = valueOf(R.string.personal_notification);

    //Navigation Drawer
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    UserData_Singleton userData = UserData_Singleton.getInstance();

    //create a object of input field
    EditText contact_fname,contact_lname,contact_email,contact_remail,contact_phone,contact_msg;

    //create contact table object
    Cheesus_Crust_Db database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set the title
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.contact_us));

        database = new Cheesus_Crust_Db(this);

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
            menu.findItem(R.id.nav_products).setVisible(false);
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

                    case R.id.nav_products :
                        menuItem.setChecked(true);
                        Intent intent2 = new Intent(ContactActivity.this, HomeActivity.class);
                        startActivity(intent2);
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
                            }
                        });
                        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
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
            contact_fname.setError(getString(R.string.please_enter_first_name));
            return;
        }

        if(TextUtils.isEmpty(lnameValue))
        {
            contact_lname.setError(getString(R.string.Please_enter_last_name));
            return;
        }

        if(TextUtils.isEmpty(remailValue))
        {
            contact_remail.setError(getString(R.string.please_reenter_email));
            return;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()){
            contact_email.setError(getString(R.string.please_enter_a_valid_email_address));
            return;
        }


        if(TextUtils.isEmpty(emailValue))
        {
            contact_email.setError(getString(R.string.please_enter_email));
            return;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()){
            contact_email.setError(getString(R.string.please_enter_a_valid_email));
            return;
        }

        //check confirm password
        if(!emailValue.equals(remailValue))
        {
            contact_remail.setError(getString(R.string.email_mismatched_please_enter_above_email));
            return;
        }


        if(TextUtils.isEmpty(msgValue))
        {
            contact_msg.setError(getString(R.string.please_enter_message));
            return;
        }

        //Telephone number validation
        if (phoneValue.length() != 10)
        {
            contact_phone.setError(getString(R.string.please_enter_valid_phone_number));
            return;
        }



        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String dateValue = sdf.format(cal.getTime());

        boolean result = database.insertData(dateValue,fnameValue,lnameValue,emailValue,remailValue,phoneValue,msgValue);

        if(!result)
            Toast.makeText(this, R.string.Error, Toast.LENGTH_SHORT).show();

        else{

            Toast.makeText(this, R.string.sending, Toast.LENGTH_LONG).show();

            //create notification
            createNotificationChannel();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
            builder.setSmallIcon(R.drawable.ic_lcheesus_crust_notification_icon);
            builder.setContentTitle(getString(R.string.thanku_for_contacting_us));
            builder.setContentText(getString(R.string.we_have_received_ur_inquiry));
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            int NOTIFICATION_ID = 1;
            notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
        }

    }

    private  void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = getString(R.string.personal2);
            String description = getString(R.string.include);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);

            notificationChannel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager)  getSystemService(NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
