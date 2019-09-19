package com.cheesuscrust.User;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
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

import static java.lang.String.valueOf;

public class signupScreen extends AppCompatActivity {

    //Notification attributes
    private final String CHANNEL_ID = valueOf(R.string.personal_notification);

    //Declare Seven EditText Objects
    EditText getFirstName, getLastName, getPhone, getAddress, getEmail, getPassword, getConfirmPassword;

    //Create an object of the UsersTable class
    Cheesus_Crust_Db database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Create the database connection
        database = new Cheesus_Crust_Db(this);

        //Initialise EditText objects
        getFirstName = findViewById(R.id.signup_fname);
        getLastName = findViewById(R.id.signup_lname);
        getPhone = findViewById(R.id.signup_phone);
        getAddress = findViewById(R.id.signup_address);
        getEmail = findViewById(R.id.signup_email);
        getPassword = findViewById(R.id.signup_password);
        getConfirmPassword = findViewById(R.id.signup_confirmPassword);
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

        //Email Validation
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(emailPattern))
        {
            getEmail.setError(getString(R.string.invalid_email_address_please_enter_correct_email_address));
            return;
        }

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
        }

        else
        {
            //Display a notification
            displayNotification();

            //Display a Toast
            Toast.makeText(this, R.string.account_created_please_login, Toast.LENGTH_SHORT).show();

            //Send the user into the login screen
            Intent intent = new Intent(signupScreen.this, loginScreen.class);
            startActivity(intent);
        }
    }

    //Check empty fields function
    public boolean isEmpty(String value)
    {
        return TextUtils.isEmpty(value);
    }

    public void goToLogin(View view)
    {
        Intent intent = new Intent(signupScreen.this, loginScreen.class);
        startActivity(intent);
    }

    //Display Notification
    public void displayNotification()
    {
        int NOTIFICATION_ID = 1;

        createNotificationChannel();

        Intent landingIntent = new Intent(this, WelcomeScreen.class);
        landingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent landingPendingIntent = PendingIntent.getActivity(this, 0, landingIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_lcheesus_crust_notification_icon);
        builder.setContentTitle(getString(R.string.your_account_was_created));
        builder.setContentText(getString(R.string.please_login_and_order_some_pizza_to_satisfy_your_hunger));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setAutoCancel(true);
        builder.setContentIntent(landingPendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    public void createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = getString(R.string.personal_notifications);
            String description = getString(R.string.include_all_the_personal_notifications);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);

            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
    }
}
