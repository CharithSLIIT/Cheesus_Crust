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

import com.cheesuscrust.Contact.activity_dash;
import com.cheesuscrust.Database.Cheesus_Crust_Db;
import com.cheesuscrust.Product.HomeActivity;
import com.cheesuscrust.R;
import com.google.android.material.snackbar.Snackbar;

public class WelcomeScreen extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Cheesus_Crust_Db database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        //Check network
        boolean network = this.isOnline();

        if(!network)
        {
            Snackbar.make(findViewById(R.id.welcome_screen), R.string.please_connect_to_a_network, Snackbar.LENGTH_LONG).show();
            return;
        }

        sharedPreferences = getSharedPreferences(String.valueOf(R.string.cheesus_crust), MODE_PRIVATE);

        if(sharedPreferences.getBoolean(String.valueOf(R.string.logged),false))
        {
            //Create UserData_Singleton Singleton object to store user data
            UserData_Singleton data = UserData_Singleton.getInstance();

            database = new Cheesus_Crust_Db(this);

            Cursor userData = database.getUserData(sharedPreferences.getString(String.valueOf(R.string.email_sp), null));

            while(userData.moveToNext())
            {
                //Store user data
                data.setUser_name(userData.getString(1), userData.getString(2));
                data.setUser_email(userData.getString(3));
                data.setUser_phone(userData.getString(4));
                data.setUser_address(userData.getString(5));
                data.setUser_type(userData.getString(8));
            }

            if (data.getUser_type().equals(getString(R.string.admin)))
            {
                Intent intent = new Intent(WelcomeScreen.this, activity_dash.class);
                startActivity(intent);
                return;
            }

            Intent intent = new Intent(WelcomeScreen.this, HomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Check network
        boolean network = this.isOnline();

        if(!network)
        {
            Snackbar.make(findViewById(R.id.welcome_screen), R.string.please_connect_to_a_network, Snackbar.LENGTH_LONG).show();
            return;
        }

        sharedPreferences = getSharedPreferences(String.valueOf(R.string.cheesus_crust), MODE_PRIVATE);

        if(sharedPreferences.getBoolean(String.valueOf(R.string.logged),false))
        {
            //Create UserData_Singleton Singleton object to store user data
            UserData_Singleton data = UserData_Singleton.getInstance();

            database = new Cheesus_Crust_Db(this);

            Cursor userData = database.getUserData(sharedPreferences.getString(String.valueOf(R.string.email_sp), null));

            while(userData.moveToNext())
            {
                //Store user data
                data.setUser_name(userData.getString(1), userData.getString(2));
                data.setUser_email(userData.getString(3));
                data.setUser_phone(userData.getString(4));
                data.setUser_address(userData.getString(5));
                data.setUser_type(userData.getString(8));
            }

            if (data.getUser_type().equals(getString(R.string.admin)))
            {
                Intent intent = new Intent(WelcomeScreen.this, activity_dash.class);
                startActivity(intent);
                return;
            }

            Intent intent = new Intent(WelcomeScreen.this, HomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //Check network
        boolean network = this.isOnline();

        if(!network)
        {
            Snackbar.make(findViewById(R.id.welcome_screen), R.string.please_connect_to_a_network, Snackbar.LENGTH_LONG).show();
            return;
        }

        sharedPreferences = getSharedPreferences(String.valueOf(R.string.cheesus_crust), MODE_PRIVATE);

        if(sharedPreferences.getBoolean(String.valueOf(R.string.logged),false))
        {
            //Create UserData_Singleton Singleton object to store user data
            UserData_Singleton data = UserData_Singleton.getInstance();

            database = new Cheesus_Crust_Db(this);

            Cursor userData = database.getUserData(sharedPreferences.getString(String.valueOf(R.string.email_sp), null));

            while(userData.moveToNext())
            {
                //Store user data
                data.setUser_name(userData.getString(1), userData.getString(2));
                data.setUser_email(userData.getString(3));
                data.setUser_phone(userData.getString(4));
                data.setUser_address(userData.getString(5));
                data.setUser_type(userData.getString(8));
            }

            if (data.getUser_type().equals(getString(R.string.admin)))
            {
                Intent intent = new Intent(WelcomeScreen.this, activity_dash.class);
                startActivity(intent);
                return;
            }

            Intent intent = new Intent(WelcomeScreen.this, HomeActivity.class);
            startActivity(intent);
        }
    }

    public void goToLogin(View view)
    {
        //Check network
        boolean network = this.isOnline();

        if(!network)
        {
            Snackbar.make(findViewById(R.id.welcome_screen), R.string.please_connect_to_a_network, Snackbar.LENGTH_LONG).show();
            return;
        }

        sharedPreferences = getSharedPreferences(String.valueOf(R.string.cheesus_crust), MODE_PRIVATE);

        if(sharedPreferences.getBoolean(String.valueOf(R.string.logged),false))
        {
            //Create UserData_Singleton Singleton object to store user data
            UserData_Singleton data = UserData_Singleton.getInstance();

            database = new Cheesus_Crust_Db(this);

            Cursor userData = database.getUserData(sharedPreferences.getString(String.valueOf(R.string.email_sp), null));

            while(userData.moveToNext())
            {
                //Store user data
                data.setUser_name(userData.getString(1), userData.getString(2));
                data.setUser_email(userData.getString(3));
                data.setUser_phone(userData.getString(4));
                data.setUser_address(userData.getString(5));
                data.setUser_type(userData.getString(8));
            }

            if(data.getUser_type().equals(getString(R.string.admin)))
            {
                Intent intent = new Intent(WelcomeScreen.this, activity_dash.class);
                startActivity(intent);
                return;
            }

            Intent intent = new Intent(WelcomeScreen.this, HomeActivity.class);
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
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        return (networkInfo != null && networkInfo.isConnected());
    }
}
