package com.cheesuscrust.Product;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cheesuscrust.Database.Cheesus_Crust_Db;
import com.cheesuscrust.R;

import java.util.ArrayList;

public class AdminFoodList extends AppCompatActivity {

    public static Cheesus_Crust_Db myDB;
    GridView gridView;
    ArrayList<AdminFood> list;
    AdminFoodListAdapter adapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_food_list_activity);

        //Database Connection
        myDB = new Cheesus_Crust_Db(this);

        gridView = (GridView) findViewById(R.id.adGridView);
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

    }
}
