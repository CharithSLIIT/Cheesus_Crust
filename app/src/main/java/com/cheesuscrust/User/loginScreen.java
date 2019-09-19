package com.cheesuscrust.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cheesuscrust.Contact.activity_dash;
import com.cheesuscrust.Database.Cheesus_Crust_Db;
import com.cheesuscrust.R;

public class loginScreen extends AppCompatActivity {

    //Declare EditText objects
    EditText getEmail, getPassword;

    //SharedPreference
    SharedPreferences sharedPreferences;

    Cheesus_Crust_Db database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        database = new Cheesus_Crust_Db(this);

        //Initialise two EditText objects
        getEmail = findViewById(R.id.login_email);
        getPassword = findViewById(R.id.login_password);
    }

    public void userLogin(View view)
    {
        //Get user inputs
        String email = getEmail.getText().toString();
        String password = getPassword.getText().toString();

        //Check for the empty values
        if(TextUtils.isEmpty(email))
        {
            getEmail.setError(getString(R.string.please_enter_your_email));
            return;
        }

        else if(TextUtils.isEmpty(password))
        {
            getPassword.setError(getString(R.string.please_provide_the_password));
            return;
        }

        //Invoke login function in the UserTable class
        int loginResult = database.loginFunction(email, password);

        //No emails found
        if(loginResult == -1)
        {
            getEmail.setError(getString(R.string.invalid_email));
            return;
        }

        //Password is not correct
        else if(loginResult == 0)
        {
            getPassword.setError(getString(R.string.invalid_password));
            return;
        }

        else if(loginResult == -2)
        {
            Toast.makeText(this, getString(R.string.some_error_occurred_lease_try_again_later), Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, R.string.login_success, Toast.LENGTH_LONG).show();

        sharedPreferences = getSharedPreferences(String.valueOf(R.string.cheesus_crust), MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(String.valueOf(R.string.logged),true).apply();
        sharedPreferences.edit().putString(String.valueOf(R.string.email), email).apply();

        UserData data = UserData.getInstance();
        if(data.getUser_type().equals(getString(R.string.admin)))
        {
            Intent intent = new Intent(loginScreen.this, activity_dash.class);
            startActivity(intent);
            return;
        }

        Intent intent = new Intent(loginScreen.this, UserProfile.class);
        startActivity(intent);
    }

    public void goToSignup(View view)
    {
        Intent intent = new Intent(loginScreen.this, signupScreen.class);
        startActivity(intent);
    }
}
