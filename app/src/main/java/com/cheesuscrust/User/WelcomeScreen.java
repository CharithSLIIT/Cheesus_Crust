package com.cheesuscrust.User;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cheesuscrust.R;
import com.google.android.material.snackbar.Snackbar;

public class WelcomeScreen extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    UsersTable database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        //Check network
        boolean network = this.isOnline();

        if(network == false)
        {
            Snackbar.make(findViewById(R.id.welcome_screen), R.string.please_connect_to_a_network, Snackbar.LENGTH_LONG).show();
            return;
        }

        sharedPreferences = getSharedPreferences(String.valueOf(R.string.cheesus_crust), MODE_PRIVATE);

        if(sharedPreferences.getBoolean(String.valueOf(R.string.logged),false))
        {
            //Create UserData Singleton object to store user data
            UserData data = UserData.getInstance();

            database = new UsersTable(this);

            Cursor userData = database.getUserData(sharedPreferences.getString(String.valueOf(R.string.email), null));

            while(userData.moveToNext())
            {
                //Store user data
                data.setUser_id(userData.getInt(0));
                data.setUser_name(userData.getString(1), userData.getString(2));
                data.setUser_email(userData.getString(3));
                data.setUser_phone(userData.getString(4));
                data.setUser_address(userData.getString(5));
            }

            Intent intent = new Intent(WelcomeScreen.this, UserProfile.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Check network
        boolean network = this.isOnline();

        if(network == false)
        {
            Snackbar.make(findViewById(R.id.welcome_screen), R.string.please_connect_to_a_network, Snackbar.LENGTH_LONG).show();
            return;
        }

        sharedPreferences = getSharedPreferences(String.valueOf(R.string.cheesus_crust), MODE_PRIVATE);

        if(sharedPreferences.getBoolean(String.valueOf(R.string.logged),false))
        {
            //Create UserData Singleton object to store user data
            UserData data = UserData.getInstance();

            database = new UsersTable(this);

            Cursor userData = database.getUserData(sharedPreferences.getString(String.valueOf(R.string.email), null));

            while(userData.moveToNext())
            {
                //Store user data
                data.setUser_id(userData.getInt(0));
                data.setUser_name(userData.getString(1), userData.getString(2));
                data.setUser_email(userData.getString(3));
                data.setUser_phone(userData.getString(4));
                data.setUser_address(userData.getString(5));
            }

            Intent intent = new Intent(WelcomeScreen.this, UserProfile.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //Check network
        boolean network = this.isOnline();

        if(network == false)
        {
            Snackbar.make(findViewById(R.id.welcome_screen), R.string.please_connect_to_a_network, Snackbar.LENGTH_LONG).show();
            return;
        }

        sharedPreferences = getSharedPreferences(String.valueOf(R.string.cheesus_crust), MODE_PRIVATE);

        if(sharedPreferences.getBoolean(String.valueOf(R.string.logged),false))
        {
            //Create UserData Singleton object to store user data
            UserData data = UserData.getInstance();

            database = new UsersTable(this);

            Cursor userData = database.getUserData(sharedPreferences.getString(String.valueOf(R.string.email), null));

            while(userData.moveToNext())
            {
                //Store user data
                data.setUser_id(userData.getInt(0));
                data.setUser_name(userData.getString(1), userData.getString(2));
                data.setUser_email(userData.getString(3));
                data.setUser_phone(userData.getString(4));
                data.setUser_address(userData.getString(5));
            }

            Intent intent = new Intent(WelcomeScreen.this, UserProfile.class);
            startActivity(intent);
        }
    }

    public void goToLogin(View view)
    {
        //Check network
        boolean network = this.isOnline();

        if(network == false)
        {
            Snackbar.make(findViewById(R.id.welcome_screen), R.string.please_connect_to_a_network, Snackbar.LENGTH_LONG).show();
            return;
        }

        sharedPreferences = getSharedPreferences(String.valueOf(R.string.cheesus_crust), MODE_PRIVATE);

        if(sharedPreferences.getBoolean(String.valueOf(R.string.logged),false))
        {
            //Create UserData Singleton object to store user data
            UserData data = UserData.getInstance();

            database = new UsersTable(this);

            Cursor userData = database.getUserData(sharedPreferences.getString(String.valueOf(R.string.email), null));

            while(userData.moveToNext())
            {
                //Store user data
                data.setUser_id(userData.getInt(0));
                data.setUser_name(userData.getString(1), userData.getString(2));
                data.setUser_email(userData.getString(3));
                data.setUser_phone(userData.getString(4));
                data.setUser_address(userData.getString(5));
            }

            Intent intent = new Intent(WelcomeScreen.this, UserProfile.class);
            startActivity(intent);
            return;
        }

        Intent intent = new Intent(WelcomeScreen.this, loginScreen.class);
        startActivity(intent);
    }

    //Check network status
    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
