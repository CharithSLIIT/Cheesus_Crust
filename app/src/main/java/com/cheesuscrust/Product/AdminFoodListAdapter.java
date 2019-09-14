package com.cheesuscrust.Product;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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


        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtname = (TextView) row.findViewById(R.id.adTxtName);
            holder.txtcategory = (TextView) row.findViewById(R.id.adTextType);
            holder.imageview = (ImageView) row.findViewById(R.id.adImgFood);
            holder.txtdesc = (TextView) row.findViewById(R.id.adTxtDesc);
            holder.txtprice = (TextView) row.findViewById(R.id.adTxtPrice);
            holder.edit = (Button) row.findViewById(R.id.btnEdit);
            holder.delete = (Button) row.findViewById(R.id.btnDelete);
            row.setTag(holder);
        }

        else{
            holder = (ViewHolder) row.getTag();
        }

        AdminFood food = foodList.get(position);
        final int sendpos = food.getId();

        holder.txtname.setText(food.getName());
        holder.txtdesc.setText(food.getDescription());
        holder.txtprice.setText("Small: Rs." + food.getSprice() + " | Medium Rs." + food.getMprice() + " | Large Rs." + food.getLprice());
        holder.txtcategory.setText(food.getType());

        byte[] foodImage = food.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
        if (bitmap != null){
            Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, 600, 600, true);
            holder.imageview.setImageBitmap(bitmapScaled);
        }
        else{
            holder.imageview.setImageBitmap(bitmap);

        }

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editData(sendpos);

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDelete(sendpos);

            }
        });

        return row;
    }

    public void editData(int i){
        Intent editIntent = new Intent(context, AdminEdit.class);
        editIntent.putExtra("id", i);
        context.startActivity(editIntent);
    }

    private void showDialogDelete(final int id){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(context);
        dialogDelete.setTitle("Confirmation");
        dialogDelete.setMessage("Are you sure you want to remove this item?");
        dialogDelete.setPositiveButton("Yes, remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AddItem.myDB.deleteData(id);
                Toast.makeText(context, "Item Removed!", Toast.LENGTH_SHORT);
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
