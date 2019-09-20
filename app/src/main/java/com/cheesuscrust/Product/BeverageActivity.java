package com.cheesuscrust.Product;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.cheesuscrust.Database.Cheesus_Crust_Db;
import com.cheesuscrust.R;
import com.cheesuscrust.Contact.activity_dash;
import java.util.ArrayList;

public class BeverageActivity extends AppCompatActivity {

    //Declaration
    GridView gridView;
    ArrayList<CustomerFood> list;
    CustomerFoodListAdapter adapter = null;
    Cheesus_Crust_Db mydb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_food_list_activity);

        //Database Connection
        mydb = new Cheesus_Crust_Db(this);

        //Set Layouts
        gridView =  findViewById(R.id.adGridView);
        list = new ArrayList<>();
        adapter = new CustomerFoodListAdapter(this, R.layout.customer_food_items, list);
        gridView.setAdapter(adapter);


        //Get data from Database
        Cursor cursor = mydb.getData("SELECT * FROM Product WHERE p_type = 'Beverage' ORDER by p_type DESC ");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.customer_search_menu, menu);
        MenuItem item = menu.findItem(R.id.adGridView);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
