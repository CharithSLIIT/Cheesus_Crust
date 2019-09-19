package com.cheesuscrust.Product;
import com.cheesuscrust.Database.Cheesus_Crust_Db;

import com.cheesuscrust.Contact.activity_dash;
import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.cheesuscrust.R;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddItem extends AppCompatActivity {
    public static DBHelper myDB;
    TextInputLayout editName,editDesc,editsPrice,editmPrice,editlPrice;
    Spinner  spinnerType;
    Button btnAddData, btnView, btnAddImage;
    String editType;
    ImageView imageView;

    final int REQUEST_CODE_GALLERY = 999;
    final String CHANNEL_ID = "Cheesus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);



        //Database Connection
        myDB = new DBHelper(this);

        //Display values inside Spinners
        Spinner proType = (Spinner) findViewById(R.id.typeSpinner);
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
        spinnerType = (Spinner) findViewById(R.id.typeSpinner);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
                editType= parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        imageView = (ImageView) findViewById(R.id.imageView1);
        btnView = (Button)findViewById(R.id.buttonView);
        btnAddImage = (Button)findViewById(R.id.buttonAddImage);


        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewintent = new Intent(AddItem.this, AdminFoodList.class);
                startActivity(viewintent);

            }
        });

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

    }

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

        if(isInserted == true)
        {
            spinnerType.setSelection(0);
            imageView.setImageResource((R.drawable.imageupload));

            Toast.makeText(AddItem.this, "Item successfully Added", Toast.LENGTH_SHORT).show();


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
    //Input Validation


    public void confirmInput(View view) {
        if(!validateName() | !validatesPrice() | !validatemPrice() | !validatelPrice() ){
            return;
        }
        else{
            AddData();
        }

    }

    //newvid
//    public void viewAll(){
//        btnViewData.setOnClickListener(
//            new View.OnClickListener(){
//
//                @Override
//                public void onClick(View v) {
//                    Cursor res = myDB.getAllData();
//                        if(res.getCount() == 0){
//                            showMessage("Error","No data found");
//                            return;
//                        }
//
//                        StringBuffer buffer = new StringBuffer();
//                        while(res.moveToNext()){
//                            buffer.append("ID :"+ res.getString(0)+"\n");
//                            buffer.append("Name :"+ res.getString(1)+"\n");
//                            buffer.append("Description :"+ res.getString(2)+"\n");
//                            buffer.append("Small Price :"+ res.getString(3)+"\n");
//                            buffer.append("Size :"+ res.getString(4)+"\n\n");
//                            buffer.append("Type :"+ res.getString(5)+"\n\n");
//                        }
//
//                        showMessage("Data",buffer.toString());
//
//                }
//            }
//        );
//    }

    //newvid
//    public void showMessage(String title, String Message){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setCancelable(true);
//        builder.setTitle(title);
//        builder.setMessage(Message);
//        builder.show();
//    }


}
