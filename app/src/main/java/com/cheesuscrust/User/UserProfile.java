package com.cheesuscrust.User;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.cheesuscrust.Database.Cheesus_Crust_Db;
import com.cheesuscrust.R;

import static android.text.TextUtils.isEmpty;

public class UserProfile extends AppCompatActivity implements UserProfile_UpdateAddress_Dialog.passAddress, UserProfile_UpdateEmail_Dialog.passEmail, UserProfile_UpdatePhone_Dialog.passPhone, UserProfile_UpdatePassword_Dialog.passPassword {

    //Notification attributes
    private final String CHANNEL_ID = "Personal_Notifications";
    private final int NOTIFICATION_ID = 001;

    //Toolbar
    private Toolbar toolbar;

    //Create the database connection
    Cheesus_Crust_Db database;

    //get an instance of the UserData to get user data
    UserData data = UserData.getInstance();

    //Create TextView objects
    TextView displayName, displayEmail, displayAddress, displayPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set the title
        getSupportActionBar().setTitle(getString(R.string.my_account));

        database = new Cheesus_Crust_Db(this);

        //Initialise TextView objects
        displayName = findViewById(R.id.userProfile_displayName);
        displayEmail = findViewById(R.id.userProfile_displayEmail);
        displayAddress = findViewById(R.id.userProfile_displayAddress);
        displayPhone = findViewById(R.id.userProfile_displayPhone);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Set user data
        displayName.setText(data.getUser_name());
        displayEmail.setText(data.getUser_email());
        displayAddress.setText(data.getUser_address());
        displayPhone.setText(data.getUser_phone());
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //Set user data
        displayName.setText(data.getUser_name());
        displayEmail.setText(data.getUser_email());
        displayAddress.setText(data.getUser_address());
        displayPhone.setText(data.getUser_phone());
    }

    public void editAddress(View view)
    {
        UserProfile_UpdateAddress_Dialog editAddress = new UserProfile_UpdateAddress_Dialog();
        editAddress.show(getSupportFragmentManager(), getString(R.string.update_address_dialog));

    }

    public void editEmail(View view)
    {
        UserProfile_UpdateEmail_Dialog editEmail = new UserProfile_UpdateEmail_Dialog();
        editEmail.show(getSupportFragmentManager(), getString(R.string.update_email_dialog));
    }

    public void editPhone(View view)
    {
        UserProfile_UpdatePhone_Dialog editEmail = new UserProfile_UpdatePhone_Dialog();
        editEmail.show(getSupportFragmentManager(), getString(R.string.update_phone_dialog));
    }

    public void editPassword(View view)
    {
        displayNotification();
        UserProfile_UpdatePassword_Dialog editPassword = new UserProfile_UpdatePassword_Dialog();
        editPassword.show(getSupportFragmentManager(), getString(R.string.update_password_dialog));
    }

    @Override
    public void applyAddress(String address) {

        //check for empty fields
        if(isEmpty(address))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle(R.string.error);
            builder.setMessage(getString(R.string.please_provide_an_address_to_proceed));
            builder.show();
            return;
        }

        //Update details in the UserData object
        data.setUser_address(address);

        //Get user email
        String email = data.getUser_email();

        //Update the database
        boolean isUpdated = database.updateUserAddress(email, address);

        if(isUpdated == true)
        {
            //Update the view
            displayAddress.setText(data.getUser_address());

            Toast.makeText(this, R.string.your_address_updated, Toast.LENGTH_SHORT).show();
        }

        else
        {
            Toast.makeText(this, R.string.cant_update_your_address_right_now_please_try_again_shortly, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void applyEmail(String email) {

        //check for empty fields
        if(isEmpty(email))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle(R.string.error);
            builder.setMessage(getString(R.string.please_provide_an_email_address_to_proceed));
            builder.show();
            return;
        }

        //Get user email
        String currentEmail = data.getUser_email();

        //Update the database
    int isUpdated = database.updateUserEmail(currentEmail, email);

        if(isUpdated == -1)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle(R.string.error);
            builder.setMessage(getString(R.string.entered_email_is_already_in_use_please_provide_another_email));
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
            return;
        }

        else if(isUpdated == 1)
        {
            //Update details in the UserData object
            data.setUser_email(email);

            //Update the view
            displayEmail.setText(data.getUser_email());

            Toast.makeText(this, R.string.your_email_updated, Toast.LENGTH_SHORT).show();
        }

        else
        {
            Toast.makeText(this, R.string.cant_update_your_email_right_now_please_try_again_shortly, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void applyPhone(String phone)
    {
        //check for empty fields
        if(isEmpty(phone))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle(R.string.error);
            builder.setMessage(getString(R.string.please_provide_a_phone_number_to_proceed));
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
            return;
        }

        //Get user email
        String email = data.getUser_email();

        //Update the database
        boolean isUpdated = database.updateUserPhone(email, phone);

        if(isUpdated == true)
        {
            //Update details in the UserData object
            data.setUser_phone(phone);

            //Update the view
            displayPhone.setText(data.getUser_phone());

            Toast.makeText(this, R.string.your_phone_number_updated, Toast.LENGTH_SHORT).show();
        }

        else
        {
            Toast.makeText(this, R.string.cant_update_your_phone_number_right_now_please_try_again_shortly, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void applyPassword(String currentPassword, String newPassword, String confirmPassword)
    {
        //check for empty fields
        if(isEmpty(currentPassword))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle(R.string.error);
            builder.setMessage(getString(R.string.please_provide_your_current_password_to_proceed));
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
            return;
        }

        else if(isEmpty(newPassword))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle(R.string.error);
            builder.setMessage(getString(R.string.please_provide_a_new_password_to_proceed));
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
            return;
        }

        else if(isEmpty(confirmPassword))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle(R.string.error);
            builder.setMessage(getString(R.string.please_confirm_above_password_to_proceed));
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
            return;
        }

        else if(newPassword.equals(confirmPassword))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle(R.string.error);
            builder.setMessage(getString(R.string.password_mismatched_please_confirm_your_new_password_correctly_to_proceed));
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
            return;
        }

        //Get user email
        String email = data.getUser_email();

        //Update the database
        int result = database.updateUserPassword(email, currentPassword, newPassword);

        if(result == -1)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle(getString(R.string.error));
            builder.setMessage(getString(R.string.invalid_current_password_please_enter_your_current_password_to_proceed));
            builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
            return;
        }

        else if(result == 1)
        {
            Toast.makeText(this, R.string.your_password_updated, Toast.LENGTH_SHORT).show();
        }

        else
        {
            Toast.makeText(this, R.string.cant_update_your_password_right_now_please_try_again_shortly, Toast.LENGTH_LONG).show();
        }
    }

    public void user_logout(View view)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.cheesus_crust), MODE_PRIVATE);
        sharedPreferences.edit().remove(String.valueOf(R.string.logged)).apply();
        sharedPreferences.edit().remove(String.valueOf(R.string.email)).apply();

        Intent intent = new Intent(UserProfile.this, WelcomeScreen.class);
        startActivity(intent);
    }

    public void delete_account(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(getString(R.string.are_you_sure_about_this));
        builder.setMessage(getString(R.string.we_will_miss_you));
        builder.setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(UserProfile.this, R.string.hurray_lets_eat_some_pizza, Toast.LENGTH_SHORT).show();
                return;
            }
        });
        builder.setNegativeButton(getString(R.string.delete_my_account), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                boolean result = database.deleteUser(data.getUser_email());

                if (result == false) {
                    Toast.makeText(UserProfile.this, R.string.some_error_occured_lease_try_again_later, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(UserProfile.this, R.string.your_account_was_deleted, Toast.LENGTH_SHORT).show();

                    SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.cheesus_crust), MODE_PRIVATE);
                    sharedPreferences.edit().remove(getString(R.string.logged)).apply();
                    sharedPreferences.edit().remove(String.valueOf(R.string.email)).apply();

                    Intent intent = new Intent(UserProfile.this, WelcomeScreen.class);
                    startActivity(intent);

                    return;
                }
            }
        });
        builder.show();
        return;

    }

    public void displayNotification()
    {
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
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }


}
