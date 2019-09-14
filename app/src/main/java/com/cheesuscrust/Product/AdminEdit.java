package com.cheesuscrust.Product;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.cheesuscrust.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AdminEdit extends AppCompatActivity {

    public static DBHelper myDB;
    EditText updateame,updateDesc,updatesPrice,updatemPrice,updatelPrice;
    Button btnUpdateData , btnUpdateImage;
    ImageView updateImage, updateImage1;
    final int REQUEST_CODE_GALLERY = 888;
    int getid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit);

        Intent getintent = getIntent();
        getid = getintent.getIntExtra("id", 0);

        //Database Connection
        myDB = new DBHelper(this);


        //Display the current values
        updateame = (EditText)findViewById(R.id.updateTextName);
        updateDesc = (EditText)findViewById(R.id.updateTextDesc);
        updatesPrice = (EditText)findViewById(R.id.updateTextsPrice);
        updatemPrice = (EditText)findViewById(R.id.updateTextmPrice);
        updatelPrice = (EditText)findViewById(R.id.updateTextlPrice);
        updateImage = (ImageView)findViewById(R.id.updateimageView1);
        btnUpdateData = (Button)findViewById(R.id.buttonUpdate);
        btnUpdateImage = (Button)findViewById(R.id.buttonUpdateImage);


        //Get values from the Database
        Cursor cursor = AddItem.myDB.getData("SELECT * FROM product WHERE p_id = " + getid + ";");
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            String sprice = cursor.getString(3);
            String mprice = cursor.getString(4);
            String lprice = cursor.getString(5);
            byte[] image = cursor.getBlob(7);

            Log.i("res1", "name is " +name);

            updateame.setText(name);
            updateDesc.setText(description);
            updatesPrice.setText(sprice);
            updatemPrice.setText(mprice);
            updatelPrice.setText(lprice);


            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            updateImage.setImageBitmap(bitmap);

        }

        btnUpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        AdminEdit.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddData();
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
                updateImage.setImageBitmap(bitmapScaled);
//                Drawable drawable = new BitmapDrawable(bitmapScaled);
//                imageView.setBackgroundDrawable(drawable);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    public void AddData(){
        updateImage1 = (ImageView)findViewById(R.id.updateimageView1);
             myDB.updateData(getid,
                     updateame.getText().toString().trim(),
                     updateDesc.getText().toString().trim(),
                     updatesPrice.getText().toString().trim(),
                     updatemPrice.getText().toString().trim(),
                     updatelPrice.getText().toString().trim(),
                     imageViewToByte(updateImage1));

             Toast.makeText(getApplicationContext(), "Item Updated",Toast.LENGTH_SHORT);
    }

    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        Log.i("res1", "image byte is " +byteArray);
        return byteArray;
    }
}
