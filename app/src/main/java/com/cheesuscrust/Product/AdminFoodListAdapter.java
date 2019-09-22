package com.cheesuscrust.Product;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.cheesuscrust.R;

import java.util.ArrayList;

public class AdminFoodListAdapter extends BaseAdapter {

    //Declaration
    private Context context;
    private int layout;
    private ArrayList<AdminFood> foodList;


    public AdminFoodListAdapter(Context context, int layout, ArrayList<AdminFood> foodList) {
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
        TextView txtname, txtcategory, txtdesc, txtprice;
        Button edit,delete;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        //Get layouts
        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtname = row.findViewById(R.id.adTxtName);
            holder.txtcategory = row.findViewById(R.id.adTextType);
            holder.imageview = row.findViewById(R.id.adImgFood);
            holder.txtdesc = row.findViewById(R.id.adTxtDesc);
            holder.txtprice = row.findViewById(R.id.adTxtPrice);
            holder.edit = row.findViewById(R.id.btnEdit);
            holder.delete = row.findViewById(R.id.btnDelete);
            row.setTag(holder);
        }

        else{
            holder = (ViewHolder) row.getTag();
        }

        //Set values
        AdminFood food = foodList.get(position);
        final int sendpos = food.getId();

        String smallPrice = context.getString(R.string.small_price) + food.getSprice();
        String mediumPrice = context.getString(R.string.medium_price)+ food.getMprice();
        String largePrice = context.getString(R.string.large_price)+ food.getLprice();
        String price = smallPrice + mediumPrice+ largePrice;

        holder.txtname.setText(food.getName());
        holder.txtdesc.setText(food.getDescription());
        holder.txtprice.setText(price);
        holder.txtcategory.setText(food.getType());

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

        //When Edit button is clicked
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editData(sendpos);

            }
        });

        //When Delete button is clicked
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDelete(sendpos);

            }
        });

        return row;
    }

    //Edit Data
    private void editData(int i){
        Intent editIntent = new Intent(context, AdminEdit.class);
        editIntent.putExtra("id", i);
        context.startActivity(editIntent);
    }

    //Dialog for Delete button
    private void showDialogDelete(final int id){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(context);
        dialogDelete.setTitle("Confirmation");
        dialogDelete.setMessage("Are you sure you want to remove this item?");
        dialogDelete.setPositiveButton("Yes, remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AddItem.myDB.deleteData(id);
                Toast.makeText(context, "Item Removed!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, AdminFoodList.class);
                context.startActivity(intent);
            }
        });

        dialogDelete.setNegativeButton("No, cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogDelete.show();
    }



}
