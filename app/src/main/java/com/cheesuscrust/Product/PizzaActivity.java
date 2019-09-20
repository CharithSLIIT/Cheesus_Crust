package com.cheesuscrust.Product;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cheesuscrust.R;

import java.util.ArrayList;

public class PizzaActivity extends AppCompatActivity {


    GridView gridView;
    ArrayList<CustomerFood> list;
    CustomerFoodListAdapter adapter = null;
    DBHelper mydb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_food_list_activity);

        mydb = new DBHelper(this);
        gridView = (GridView) findViewById(R.id.adGridView);
        list = new ArrayList<>();
        adapter = new CustomerFoodListAdapter(this, R.layout.customer_food_items, list);
        gridView.setAdapter(adapter);
        Spinner spinner;


        //Get data from Database
        Cursor cursor = mydb.getData("SELECT * FROM Product WHERE p_type = 'Pizza' ORDER by p_type DESC ");
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

            list.add(new CustomerFood(id,name,description,sprice,mprice,lprice,type,image));

        }

        adapter.notifyDataSetChanged();

    }
}
