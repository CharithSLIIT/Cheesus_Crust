package com.cheesuscrust.Product;
import com.cheesuscrust.Contact.ContactActivity;
import com.cheesuscrust.Database.Cheesus_Crust_Db;

import com.cheesuscrust.Contact.activity_dash;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.cheesuscrust.R;
import com.cheesuscrust.User.UserData_Singleton;
import com.cheesuscrust.User.UserProfile;
import com.cheesuscrust.User.WelcomeScreen;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddItem extends AppCompatActivity {

    //Navigation Drawer
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    UserData_Singleton userData = UserData_Singleton.getInstance();

    //Declaration
    public static Cheesus_Crust_Db myDB;
    TextInputLayout editName,editDesc,editsPrice,editmPrice,editlPrice;
    Spinner  spinnerType;
    Button btnAddImage;
    String editType;
    ImageView imageView;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);


        //Database Connection
        myDB = new Cheesus_Crust_Db(this);

        //Display values inside Spinners
        Spinner proType =  findViewById(R.id.typeSpinner);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(AddItem.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray((R.array.type)));
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        proType.setAdapter(typeAdapter);


        //Get values from inputs
        editName = findViewById(R.id.editTextName);
        editDesc = findViewById(R.id.editTextDesc);
        editsPrice = findViewById(R.id.editTextsPrice);
        editmPrice = findViewById(R.id.editTextmPrice);
        editlPrice = findViewById(R.id.editTextlPrice);
        spinnerType = findViewById(R.id.typeSpinner);
        imageView = (ImageView) findViewById(R.id.imageView1);
        btnAddImage = (Button)findViewById(R.id.buttonAddImage);

        //Select a value from the Spinner
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
                editType= parent.getSelectedItem().toString();
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Adding an image
        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        AddItem.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
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
                        Intent intent0 = new Intent(AddItem.this, activity_dash.class);
                        startActivity(intent0);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_user_profile :
                        menuItem.setChecked(true);
                        Intent intent1 = new Intent(AddItem.this, UserProfile.class);
                        startActivity(intent1);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_contact_us :
                        menuItem.setChecked(true);
                        Intent intent3 = new Intent(AddItem.this, ContactActivity.class);
                        startActivity(intent3);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_settings :
                        menuItem.setChecked(true);
                        Toast.makeText(AddItem.this, R.string.settings_are_not_available_right_now, Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_logout:
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddItem.this);
                        builder.setCancelable(true);
                        builder.setTitle(getString(R.string.are_you_sure_you_want_to_logout));
                        builder.setPositiveButton(getString(R.string.logout), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.cheesus_crust), MODE_PRIVATE);
                                sharedPreferences.edit().remove(String.valueOf(R.string.logged)).apply();
                                sharedPreferences.edit().remove(String.valueOf(R.string.email)).apply();
                                Intent intent3 = new Intent(AddItem.this, WelcomeScreen.class);
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

    //Request permission to access Gallery
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else{
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, 600, 600, true);
                imageView.setImageBitmap(bitmapScaled);
//                Drawable drawable = new BitmapDrawable(bitmapScaled);
//                imageView.setBackgroundDrawable(drawable);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    //Add data to the Database
    public void AddData(){

        boolean isInserted = myDB.insertData(editName.getEditText().getText().toString().trim(),
                editDesc.getEditText().getText().toString().trim(),
                editsPrice.getEditText().getText().toString().trim(),
                editmPrice.getEditText().getText().toString().trim(),
                editlPrice.getEditText().getText().toString().trim(),
                editType.trim(),
                imageViewToByte(imageView));

        if(isInserted)
        {
            spinnerType.setSelection(0);
            imageView.setImageResource((R.drawable.imageupload));

            Toast.makeText(AddItem.this, "Item successfully Added", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddItem.this, activity_dash.class);
            startActivity(intent);


        }
        else
            Toast.makeText(AddItem.this, "Item Add failed",Toast.LENGTH_SHORT).show();



    }

    //Convert image to byte
    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        Log.i("first1", "image byte is " +byteArray);
        return byteArray;
    }


    //Input Validation
    private boolean validateName(){
        String nameInput = editName.getEditText().getText().toString().trim();

        if(nameInput.isEmpty()){
            editName.setError("Item Name cannot be empty");
            return false;
        }
        else{
            editName.setError(null);
            return true;
        }
    }

    private boolean validatesPrice(){
        String sPriceInput = editsPrice.getEditText().getText().toString().trim();

        if(sPriceInput.isEmpty()){
            editsPrice.setError("Item Price cannot be empty");
            return false;
        }
        else{
            editsPrice.setError(null);
            return true;
        }
    }

    private boolean validatemPrice(){
        String mPriceInput = editmPrice.getEditText().getText().toString().trim();

        if(mPriceInput.isEmpty()){
            editmPrice.setError("Item Price cannot be empty");
            return false;
        }
        else{
            editmPrice.setError(null);
            return true;
        }
    }

    private boolean validatelPrice(){
        String lPriceInput = editlPrice.getEditText().getText().toString().trim();

        if(lPriceInput.isEmpty()){
            editlPrice.setError("Item Price cannot be empty");
            return false;
        }
        else{
            editlPrice.setError(null);
            return true;
        }
    }

    //Submit data
    public void confirmInput(View view) {
        if(!validateName() | !validatesPrice() | !validatemPrice() | !validatelPrice() ){
            return;
        }
        else{
            AddData();
        }

    }
}
