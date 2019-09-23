package com.cheesuscrust.Product;


import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.cheesuscrust.R;

public class HomeActivity extends AppCompatActivity {

    private ViewPager SlideViewPager;
    private LinearLayout DotLayout;
    private HomeSliderAdapter sliderAdapter;
    private TextView[] Dots;
    private Button NextButton, BackButton;
    private int CurrentPage, backButtonCount =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_main);

        SlideViewPager =  findViewById(R.id.slideViewPager);
        DotLayout =  findViewById(R.id.dotLayout);

        sliderAdapter = new HomeSliderAdapter(this);

        //Set Layouts
        NextButton = findViewById(R.id.nxtbttn);
        BackButton = findViewById(R.id.prvsbttn);
        SlideViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);

        SlideViewPager.addOnPageChangeListener(viewListener);

        //Button click
        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SlideViewPager.setCurrentItem(CurrentPage + 1);
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SlideViewPager.setCurrentItem(CurrentPage - 1);
            }
        });


    }

    //Add the search icon to Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    //Navigation Dots
    public void addDotsIndicator(int position){

        Dots = new TextView[3];
        DotLayout.removeAllViews();

        for(int i = 0; i < Dots.length; i++){
            Dots[i] = new TextView(this);
            Dots[i].setText(Html.fromHtml("&#8226;"));
            Dots[i].setTextSize(35);
            Dots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            DotLayout.addView(Dots[i]);



        }

        if(Dots.length> 0){
            Dots[position].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
        }
    }

    //Page scrolls
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            addDotsIndicator(i);

            CurrentPage = i;

            if(i == 0){
                NextButton.setEnabled(true);
                BackButton.setEnabled(false);
                BackButton.setVisibility(View.INVISIBLE);

                String next = "Next";
                NextButton.setText(next);
                BackButton.setText("");
            }
            else if(i == Dots.length -1 ){
                NextButton.setEnabled(true);
                BackButton.setEnabled(true);
                BackButton.setVisibility(View.VISIBLE);

                String previous = "Previous";
                String finish = "Finish";
                NextButton.setText(finish);
                BackButton.setText(previous);

            }

            else {

                NextButton.setEnabled(true);
                BackButton.setEnabled(true);
                BackButton.setVisibility(View.VISIBLE);

                String next = "Next";
                String previous = "Previous";
                NextButton.setText(next);
                BackButton.setText(previous);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            backButtonCount--;
            finish();
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

}
