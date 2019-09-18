package com.cheesuscrust.Contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cheesuscrust.Product.AddItem;
import com.cheesuscrust.R;

public class activity_dash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set the title
        getSupportActionBar().setTitle(getString(R.string.dashboard));
    }

    public void goToAddItems(View view) {
        Intent intent = new Intent(activity_dash.this, AddItem.class);
        startActivity(intent);
    }


    public void goToView(View view) {
        Intent intent = new Intent(activity_dash.this,InquiryActivity.class);
        startActivity(intent);
    }
}
