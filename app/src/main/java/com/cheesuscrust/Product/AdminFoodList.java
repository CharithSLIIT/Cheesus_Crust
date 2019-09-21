package com.cheesuscrust.Product;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.cheesuscrust.Contact.ContactActivity;
import com.cheesuscrust.Contact.activity_dash;
import com.cheesuscrust.Database.Cheesus_Crust_Db;
import com.cheesuscrust.R;
import com.cheesuscrust.User.UserData_Singleton;
import com.cheesuscrust.User.UserProfile;
import com.cheesuscrust.User.WelcomeScreen;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class AdminFoodList extends AppCompatActivity {

    //Declaration
    public static Cheesus_Crust_Db myDB;
    GridView gridView;
    ArrayList<AdminFood> list;
    AdminFoodListAdapter adapter = null;
    //Navigation Drawer
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    UserData_Singleton userData = UserData_Singleton.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_food_list_activity);

        //Database Connection
        myDB = new Cheesus_Crust_Db(this);

        //Get layouts
        gridView = findViewById(R.id.adGridView);
        list = new ArrayList<>();
        adapter = new AdminFoodListAdapter(this, R.layout.admin_food_items, list);
        gridView.setAdapter(adapter);

        //Get data from Database
        Cursor cursor = myDB.getData("SELECT * FROM Product ORDER by p_type DESC ");
        list.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            String sprice = cursor.getString(3);
            String mprice = cursor.getString(4);
            String lprice = cursor.getString(5);
            String type = cursor.getString(6);
            byte[] image = cursor.getBlob(7);

            list.add(new AdminFood(id,name,description,sprice,mprice,lprice,type,image));

        }

        adapter.notifyDataSetChanged();



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
        if (userData.getUser_type().equals(getString(R.string.admin)))
        {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.nav_dashboard).setVisible(true);
            menu.findItem(R.id.nav_products).setVisible(false);

        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.nav_dashboard :
                        menuItem.setChecked(true);
                        Toast.makeText(AdminFoodList.this, getString(R.string.you_are_already_in_dashboard), Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_products :
                        menuItem.setChecked(true);
                        Toast.makeText(AdminFoodList.this, getString(R.string.customer_product_menu_is_not_accessible), Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_user_profile :
                        menuItem.setChecked(true);
                        Intent intent1 = new Intent(AdminFoodList.this, UserProfile.class);
                        startActivity(intent1);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_contact_us :
                        menuItem.setChecked(true);
                        Intent intent2 = new Intent(AdminFoodList.this, ContactActivity.class);
                        startActivity(intent2);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_settings :
                        menuItem.setChecked(true);
                        Toast.makeText(AdminFoodList.this, R.string.settings_are_not_available_right_now, Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_logout:
                        AlertDialog.Builder builder = new AlertDialog.Builder(AdminFoodList.this);
                        builder.setCancelable(true);
                        builder.setTitle(getString(R.string.are_you_sure_you_want_to_logout));
                        builder.setPositiveButton(getString(R.string.logout), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.cheesus_crust), MODE_PRIVATE);
                                sharedPreferences.edit().remove(String.valueOf(R.string.logged)).apply();
                                sharedPreferences.edit().remove(String.valueOf(R.string.email)).apply();
                                Intent intent3 = new Intent(AdminFoodList.this, WelcomeScreen.class);
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
}
