package com.cheesuscrust.Contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class activity_dash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
    }

    public void goToContact(View view) {
        Intent intent = new Intent(activity_dash.this, ContactActivity.class);
        startActivity(intent);
    }


    public void goToView(View view) {
        Intent intent = new Intent(activity_dash.this,InquiryActivity.class);
        startActivity(intent);
    }
}
