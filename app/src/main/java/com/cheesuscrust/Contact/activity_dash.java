package com.cheesuscrust.Contact;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.cheesuscrust.Product.AddItem;
import com.cheesuscrust.R;
import com.cheesuscrust.User.UserData;
import com.cheesuscrust.User.UserProfile;
import com.cheesuscrust.User.WelcomeScreen;
import com.google.android.material.navigation.NavigationView;

public class activity_dash extends AppCompatActivity {

    //Navigation Drawer
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    UserData userData = UserData.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set the title
        getSupportActionBar().setTitle(getString(R.string.dashboard));

        //Navigation menu icon
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.navigation_drawer_icon);

        //Navigation Drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        //Show Dashboard option
        if (userData.getUser_type().equals("Admin"))
        {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.nav_dashboard).setVisible(true);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.nav_dashboard :
                        menuItem.setChecked(true);
                        Toast.makeText(activity_dash.this, "You are already in the Dashboard!", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_user_profile :
                        menuItem.setChecked(true);
                        Intent intent1 = new Intent(activity_dash.this, UserProfile.class);
                        startActivity(intent1);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_contact_us :
                        menuItem.setChecked(true);
                        Intent intent2 = new Intent(activity_dash.this, ContactActivity.class);
                        startActivity(intent2);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_settings :
                        menuItem.setChecked(true);
                        Toast.makeText(activity_dash.this, R.string.settings_are_not_available_right_now, Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_logout:
                        SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.cheesus_crust), MODE_PRIVATE);
                        sharedPreferences.edit().remove(String.valueOf(R.string.logged)).apply();
                        sharedPreferences.edit().remove(String.valueOf(R.string.email)).apply();
                        Intent intent3 = new Intent(activity_dash.this, WelcomeScreen.class);
                        startActivity(intent3);
                        return true;
                }
                return false;
            }
        });
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

    public void goToAddItems(View view) {
        Intent intent = new Intent(activity_dash.this, AddItem.class);
        startActivity(intent);
    }


    public void goToView(View view) {
        Intent intent = new Intent(activity_dash.this,InquiryActivity.class);
        startActivity(intent);
    }
}
