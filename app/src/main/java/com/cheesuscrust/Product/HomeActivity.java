package com.cheesuscrust.Product;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.cheesuscrust.Contact.ContactActivity;
import com.cheesuscrust.Contact.activity_dash;
import com.cheesuscrust.R;
import com.cheesuscrust.User.UserData;
import com.cheesuscrust.User.UserProfile;
import com.cheesuscrust.User.WelcomeScreen;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    private ViewPager SlideViewPager;
    private LinearLayout DotLayout;
    private HomeSliderAdapter sliderAdapter;
    private TextView[] Dots;
    private Button NextButton, BackButton, trigger;
    private int CurrentPage;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    UserData userData = UserData.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_main);

        SlideViewPager =  findViewById(R.id.slideViewPager);
        DotLayout =  findViewById(R.id.dotLayout);

        sliderAdapter = new HomeSliderAdapter(this);

        //Set Layouts
        NextButton = findViewById(R.id.nxtbttn);
        BackButton = findViewById(R.id.prvsbttn);
        SlideViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);

        SlideViewPager.addOnPageChangeListener(viewListener);

        //Button click
        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SlideViewPager.setCurrentItem(CurrentPage + 1);
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SlideViewPager.setCurrentItem(CurrentPage - 1);
            }
        });


        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set the title
        getSupportActionBar().setTitle(getString(R.string.add_an_item));

        //Navigation menu icon
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.navigation_drawer_icon);

        //Navigation Drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        //Show Dashboard option
        if (userData.getUser_type().equals(getString(R.string.admin)))
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
                        Intent intent0 = new Intent(HomeActivity.this, activity_dash.class);
                        startActivity(intent0);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_user_profile :
                        menuItem.setChecked(true);
                        Intent intent1 = new Intent(HomeActivity.this, UserProfile.class);
                        startActivity(intent1);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_contact_us :
                        menuItem.setChecked(true);
                        Intent intent3 = new Intent(HomeActivity.this, ContactActivity.class);
                        startActivity(intent3);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_settings :
                        menuItem.setChecked(true);
                        Toast.makeText(HomeActivity.this, R.string.settings_are_not_available_right_now, Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_logout:
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                        builder.setCancelable(true);
                        builder.setTitle(getString(R.string.are_you_sure_you_want_to_logout));
                        builder.setPositiveButton(getString(R.string.logout), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.cheesus_crust), MODE_PRIVATE);
                                sharedPreferences.edit().remove(String.valueOf(R.string.logged)).apply();
                                sharedPreferences.edit().remove(String.valueOf(R.string.email)).apply();
                                Intent intent3 = new Intent(HomeActivity.this, WelcomeScreen.class);
                                startActivity(intent3);
                                return;
                            }
                        });
                        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                        builder.show();
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

    //Navigation Dots
    public void addDotsIndicator(int position){

        Dots = new TextView[3];
        DotLayout.removeAllViews();

        for(int i = 0; i < Dots.length; i++){
            Dots[i] = new TextView(this);
            Dots[i].setText(Html.fromHtml("&#8226;"));
            Dots[i].setTextSize(35);
            Dots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            DotLayout.addView(Dots[i]);



        }

        if(Dots.length> 0){
            Dots[position].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
        }
    }

    //Page scrolls
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            addDotsIndicator(i);

            CurrentPage = i;

            if(i == 0){
                NextButton.setEnabled(true);
                BackButton.setEnabled(false);
                BackButton.setVisibility(View.INVISIBLE);

                String next = "Next";
                NextButton.setText(next);
                BackButton.setText("");
            }
            else if(i == Dots.length -1 ){
                NextButton.setEnabled(true);
                BackButton.setEnabled(true);
                BackButton.setVisibility(View.VISIBLE);

                String previous = "Previous";
                String finish = "Finish";
                NextButton.setText(finish);
                BackButton.setText(previous);

            }

            else {

                NextButton.setEnabled(true);
                BackButton.setEnabled(true);
                BackButton.setVisibility(View.VISIBLE);

                String next = "Next";
                String previous = "Previous";
                NextButton.setText(next);
                BackButton.setText(previous);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
