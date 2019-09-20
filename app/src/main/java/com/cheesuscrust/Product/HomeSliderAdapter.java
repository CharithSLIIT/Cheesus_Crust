package com.cheesuscrust.Product;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.cheesuscrust.R;

public class HomeSliderAdapter extends PagerAdapter {

    //Declaration
    Context context;
    LayoutInflater layoutInflater;

    public HomeSliderAdapter(Context context){

        this.context = context;

    }

    //Slide values
    public int[] slide_back = {

            R.drawable.homebackground1,
            R.drawable.homebackground2,
            R.drawable.homebackground3
    };

    public int[] slide_images = {

            R.drawable.pizza,
            R.drawable.beverage,
            R.drawable.dessert
    };

    public String[] slide_headings = {

            "Pizza",
            "Beverages",
            "Dessert"
    };

    public String[] slide_descs = {

            "Enjoy a variety of delicious Oven made Pizza that will give your tastebuds a new sensation",
            "Have a drink, be refreshed with our wide range of Soft Drink Beverages",
            "Not yet full? Have a fiesta with our new line of Desserts that will surely satisfy your tummy!"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.home_slide_layout, container, false);

        //Set Layouts
        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDesciption = (TextView) view.findViewById(R.id.slide_desc);
        Button getStarted = (Button) view.findViewById(R.id.getStarted);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDesciption.setText(slide_descs[position]);
        view.setBackgroundResource(slide_back[position]);

        //Click event for images
        slideImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position == 0) {
                    Intent viewintent = new Intent(context, PizzaActivity.class);
                    context.startActivity(viewintent);
                }
                else if(position == 1) {
                    Intent viewintent = new Intent(context, BeverageActivity.class);
                    context.startActivity(viewintent);
                }

                else{
                    Intent viewintent = new Intent(context, DessertActivity.class);
                    context.startActivity(viewintent);
                }
            }
        });

        //Click event for "Get Started" Button
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position == 0) {
                    Intent viewintent = new Intent(context, PizzaActivity.class);
                    context.startActivity(viewintent);
                }
                else if(position == 1) {
                    Intent viewintent = new Intent(context, BeverageActivity.class);
                    context.startActivity(viewintent);
                }

                else{
                    Intent viewintent = new Intent(context, DessertActivity.class);
                    context.startActivity(viewintent);
                }
            }
        });


        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout)object);
    }
}
