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
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cheesuscrust.R;

import java.util.ArrayList;

public class CustomerFoodListAdapter extends BaseAdapter {

    //Declaration
    private Context context;
    private int layout, selectedsize = 1;
    private ArrayList<CustomerFood> foodList,originalFoodList;
    private Spinner spinner;
    private String size;


    public CustomerFoodListAdapter(Context context, int layout, ArrayList<CustomerFood> foodList) {
        this.context = context;
        this.layout = layout;
        this.foodList = foodList;
        this.originalFoodList = foodList;
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


        //Set Layouts
        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtname =  row.findViewById(R.id.cusItemName);
            holder.imageview = row.findViewById(R.id.cusProductImage);
            holder.txtdesc = row.findViewById(R.id.cusItemDesc);
            holder.txtprice = row.findViewById(R.id.cusPrice);
            holder.cart = row.findViewById(R.id.cartButton);
            spinner = row.findViewById(R.id.cusSpinner);

            row.setTag(holder);
        }

        else{
            holder = (ViewHolder) row.getTag();
        }

        //Get current position
        CustomerFood food = foodList.get(position);

        String smallPrice = context.getString(R.string.small_price) + food.getSprice();
        String mediumPrice = context.getString(R.string.medium_price)+ food.getMprice();
        String largePrice = context.getString(R.string.large_price)+ food.getLprice();
        String price = smallPrice + mediumPrice+ largePrice;

        //Set Values
        holder.txtname.setText(food.getName());
        holder.txtdesc.setText(food.getDescription());
        holder.txtprice.setText(price);


        holder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"The Cart is under maintenance",Toast.LENGTH_SHORT).show();
            }
        });

        //Spinner values
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.size, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
                size= parent.getSelectedItem().toString();
                if(size.equals("Small")){
                    selectedsize = 1;
                }
                else if(size.equals("Medium")){
                    selectedsize = 2;

                }
                else
                    selectedsize = 3;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Convert BLOB to image
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

    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                //Get filtered values
                foodList = (ArrayList<CustomerFood>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                //To hold results of filtering
                FilterResults results = new FilterResults();
                ArrayList<CustomerFood> FilteredArrList = new ArrayList<CustomerFood>();

                if (originalFoodList == null) {
                    originalFoodList = new ArrayList<CustomerFood>(foodList); // saves the original data in mOriginalValues
                }

                //if sequence is null, then return the original dataset
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = originalFoodList.size();
                    results.values = originalFoodList;
                }
                //Else, filter results
                else
                {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < originalFoodList.size(); i++) {
                        String data = originalFoodList.get(i).getName();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new CustomerFood(originalFoodList.get(i).getId(),
                                                                    originalFoodList.get(i).getName(),
                                                                    originalFoodList.get(i).getDescription(),
                                                                    originalFoodList.get(i).getSprice(),
                                                                    originalFoodList.get(i).getMprice(),
                                                                    originalFoodList.get(i).getLprice(),
                                                                    originalFoodList.get(i).getType(),
                                                                    originalFoodList.get(i).getImage()));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }


}
