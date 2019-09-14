package com.cheesuscrust.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.cheesuscrust.Database.Cheesus_Crust_Db;
import com.cheesuscrust.R;

public class signupScreen extends AppCompatActivity {

    //Notification attributes
    private final String CHANNEL_ID = "Personal_Notifications";
    private final int NOTIFICATION_ID = 001;

    //Toolbar
    private Toolbar toolbar;

    //Declare Seven EditText Objects
    EditText getFirstName, getLastName, getPhone, getAddress, getEmail, getPassword, getConfirmPassword;

    //Create an object of the UsersTable class
    Cheesus_Crust_Db database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        //getWindow().setBackgroundDrawable(R.id.);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Create the database connection
        database = new Cheesus_Crust_Db(this);

        //Initialise EditText objects
        getFirstName = (EditText) findViewById(R.id.signup_fname);
        getLastName = (EditText) findViewById(R.id.signup_lname);
        getPhone = (EditText) findViewById(R.id.signup_phone);
        getAddress = (EditText) findViewById(R.id.signup_address);
        getEmail = (EditText) findViewById(R.id.signup_email);
        getPassword = (EditText) findViewById(R.id.signup_password);
        getConfirmPassword = (EditText) findViewById(R.id.signup_confirmPassword);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //Signup function
    public void userSignup(View view)
    {
        //Get String values
        String fname = getFirstName.getText().toString();
        String lname = getLastName.getText().toString();
        String phone = getPhone.getText().toString();
        String address = getAddress.getText().toString();
        String email = getEmail.getText().toString();
        String password = getPassword.getText().toString();
        String confirmPassword = getConfirmPassword.getText().toString();
        String type = getString(R.string.customer);

        //check for empty fields
        if(isEmpty(fname))
        {
            getFirstName.setError(getString(R.string.please_enter_your_first_name));
            return;
        }

        else if(isEmpty(lname))
        {
            getLastName.setError(getString(R.string.please_enter_your_last_name));
            return;
        }

        else if(isEmpty(phone))
        {
            getPhone.setError(getString(R.string.please_enter_your_phone_number));
            return;
        }

        else if(isEmpty(address))
        {
            getAddress.setError(getString(R.string.please_enter_your_address));
            return;
        }

        else if(isEmpty(email))
        {
            getEmail.setError(getString(R.string.please_enter_your_email_address));
            return;
        }

        else if(isEmpty(password))
        {
            getPassword.setError(getString(R.string.please_enter_your_password));
            return;
        }

        else if(isEmpty(confirmPassword))
        {
            getConfirmPassword.setError(getString(R.string.please_enter_above_password));
            return;
        }

        //check confirm password
        if(!password.equals(confirmPassword))
        {
            getConfirmPassword.setError(getString(R.string.password_mismatched_please_enter_above_password));
            return;
        }

        String points = "0";

        //Invoke insertFunction of the database
        int insertResult = database.insertData(fname, lname, email, phone, address, password, points, type);

        //Existing email found
        if(insertResult == 0)
        {
            getEmail.setError(getString(R.string.this_email_is_already_in_our_database_please_provide_another_email_address));
            return;
        }

        //Insert error
        if(insertResult == -1)
        {
            Toast.makeText(this, R.string.insert_error_please_try_again_later, Toast.LENGTH_SHORT).show();
            return;
        }

        else
        {
            Toast.makeText(this, R.string.account_created_please_login, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(signupScreen.this, loginScreen.class);
            startActivity(intent);
        }
    }

    //Check empty fields function
    public boolean isEmpty(String value)
    {
        if(TextUtils.isEmpty(value))
            return true;

        else
            return false;
    }

    public void goToLogin(View view)
    {
        Intent intent = new Intent(signupScreen.this, loginScreen.class);
        startActivity(intent);
    }

    public void displayNotification()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_lcheesus_crust_notification_icon);
        builder.setContentTitle("Your Account Was Created!");
        builder.setContentText("Please login and order some pizza to satisfy your hunger...!");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }
}
