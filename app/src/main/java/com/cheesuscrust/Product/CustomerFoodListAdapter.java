package com.cheesuscrust.Product;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cheesuscrust.R;

import java.util.ArrayList;

public class CustomerFoodListAdapter extends BaseAdapter {

    private Context context;
    private int layout, selectedsize = 1;
    private ArrayList<CustomerFood> foodList;
    private Spinner spinner;
    private String size;


    public CustomerFoodListAdapter(Context context, int layout, ArrayList<CustomerFood> foodList) {
        this.context = context;
        this.layout = layout;
        this.foodList = foodList;
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageview;
        TextView txtname, txtdesc, txtprice;
        Button cart;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();


        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtname = (TextView) row.findViewById(R.id.cusItemName);
            holder.imageview = (ImageView) row.findViewById(R.id.cusProductImage);
            holder.txtdesc = (TextView) row.findViewById(R.id.cusItemDesc);
            holder.txtprice = (TextView) row.findViewById(R.id.cusPrice);
            holder.cart = (Button) row.findViewById(R.id.cartButton);
            spinner= row.findViewById(R.id.cusSpinner);

            row.setTag(holder);
        }

        else{
            holder = (ViewHolder) row.getTag();
        }

        CustomerFood food = foodList.get(position);
        holder.txtname.setText(food.getName());
        holder.txtdesc.setText(food.getDescription());
        holder.txtprice.setText("Small: Rs." + food.getSprice() + "  |  Medium Rs." + food.getMprice() + "  |  Large Rs." + food.getLprice());


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.size, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
                size= parent.getSelectedItem().toString();
                if(size == "Small"){
                    selectedsize = 1;
                }
                else if(size == "Medium"){
                    selectedsize = 2;

                }
                else
                    selectedsize = 3;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        byte[] foodImage = food.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
        if (bitmap != null){
            Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, 600, 600, true);
            holder.imageview.setImageBitmap(bitmapScaled);
        }
        else{
            holder.imageview.setImageBitmap(bitmap);

        }

        return row;


    }



}
