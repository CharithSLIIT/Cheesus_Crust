package com.cheesuscrust.User;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.cheesuscrust.Contact.ContactActivity;
import com.cheesuscrust.Database.Cheesus_Crust_Db;
import com.cheesuscrust.R;
import com.google.android.material.navigation.NavigationView;

import static android.text.TextUtils.isEmpty;

public class UserProfile extends AppCompatActivity implements UserProfile_UpdateAddress_Dialog.passAddress, UserProfile_UpdateEmail_Dialog.passEmail, UserProfile_UpdatePhone_Dialog.passPhone, UserProfile_UpdatePassword_Dialog.passPassword {

    //Create the database connection
    Cheesus_Crust_Db database;

    //get an instance of the UserData to get user data
    UserData data = UserData.getInstance();

    //Create TextView objects
    TextView displayName, displayEmail, displayAddress, displayPhone;

    //Navigation Drawer
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set the title
        getSupportActionBar().setTitle(getString(R.string.my_account));

        //Navigation menu icon
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.navigation_drawer_icon);

        //Navigation Drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.nav_user_profile :
                        menuItem.setChecked(true);
                        Toast.makeText(UserProfile.this, R.string.you_are_already_in_your_page, Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_contact_us :
                        menuItem.setChecked(true);
                        Intent intent1 = new Intent(UserProfile.this, ContactActivity.class);
                        startActivity(intent1);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_settings :
                        menuItem.setChecked(true);
                        Toast.makeText(UserProfile.this, R.string.settings_are_not_available_right_now, Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_logout:
                        SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.cheesus_crust), MODE_PRIVATE);
                        sharedPreferences.edit().remove(String.valueOf(R.string.logged)).apply();
                        sharedPreferences.edit().remove(String.valueOf(R.string.email)).apply();
                        Intent intent2 = new Intent(UserProfile.this, WelcomeScreen.class);
                        startActivity(intent2);
                        return true;
                }
                return false;
            }
        });

        database = new Cheesus_Crust_Db(this);

        //Initialise TextView objects
        displayName = findViewById(R.id.userProfile_displayName);
        displayEmail = findViewById(R.id.userProfile_displayEmail);
        displayAddress = findViewById(R.id.userProfile_displayAddress);
        displayPhone = findViewById(R.id.userProfile_displayPhone);
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

        if(isUpdated)
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

        if(isUpdated)
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

    public void delete_account(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(getString(R.string.are_you_sure_about_this));
        builder.setMessage(getString(R.string.we_will_miss_you));
        builder.setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(UserProfile.this, R.string.hurray_lets_eat_some_pizza, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(getString(R.string.delete_my_account), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                boolean result = database.deleteUser(data.getUser_email());

                if (!result) {
                    Toast.makeText(UserProfile.this, R.string.some_error_occured_lease_try_again_later, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserProfile.this, R.string.your_account_was_deleted, Toast.LENGTH_SHORT).show();

                    SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.cheesus_crust), MODE_PRIVATE);
                    sharedPreferences.edit().remove(getString(R.string.logged)).apply();
                    sharedPreferences.edit().remove(String.valueOf(R.string.email)).apply();

                    Intent intent = new Intent(UserProfile.this, WelcomeScreen.class);
                    startActivity(intent);
                }
            }
        });
        builder.show();
    }
}
