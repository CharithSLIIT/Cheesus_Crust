package com.cheesuscrust.Contact;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cheesuscrust.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener{

    //create a object of input field
    TextView contact_view;
    EditText contact_date,contact_fname,contact_lname,contact_email,contact_remail,contact_phone,contact_msg;
    //Spinner contact_type;

    //create contact table object
    contactTable database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cform);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set the title
        getSupportActionBar().setTitle(getString(R.string.contact_us));

        database = new contactTable(this);

        //initialise input fields
        //contact_date = (EditText) findViewById(R.id.contact_date);
        contact_fname = (EditText) findViewById(R.id.contact_fname);
        contact_lname = (EditText) findViewById(R.id.contact_lname);
        contact_email = (EditText) findViewById(R.id.contact_email);
        contact_remail = (EditText) findViewById(R.id.conatct_remail);
        contact_phone = (EditText) findViewById(R.id.contact_phone);
        contact_msg = (EditText) findViewById(R.id.contact_message);

        findViewById(R.id.contact_send).setOnClickListener(this);

        //findViewById(R.id.textViewInquiry).setOnClickListener(this);
    }





    public void send()
    {
        //get values
        String fnameValue = contact_fname.getText().toString();
        String lnameValue = contact_lname.getText().toString();
        String emailValue = contact_email.getText().toString();
        String remailValue = contact_remail.getText().toString();
        String phoneValue = contact_phone.getText().toString();
        String msgValue= contact_msg.getText().toString();

        //String typeValue = "aaa";

//        if(TextUtils.isEmpty(dateValue))
//        {
//            contact_date.setError("Please enter date");
//            return;
//        }

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

//        if(TextUtils.isEmpty(emailValue))
//        {
//            contact_email.setError("Please enter email");
//            return;
//        }
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

        if(result == false)
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();

        else
            Toast.makeText(this, "We will get back to you soon", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.contact_send:
                send();
                break;

        }

    }

    public void goToContact(View view) {
    }


}
