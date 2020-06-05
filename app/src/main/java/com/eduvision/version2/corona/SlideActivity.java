package com.eduvision.version2.corona;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduvision.version2.corona.Adapter.SlideAdapter;

public class SlideActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private SlideAdapter slideAdapter;
    private LinearLayout Layout_dots;
    private TextView[] tv_dots;
    private Button Next1;
    private int mCurrentPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_slide);
        this.getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewPager = findViewById (R.id.view_parent);
        Layout_dots = findViewById (R.id.layout_dots);
        Next1 = findViewById (R.id.next1);

        slideAdapter = new SlideAdapter (SlideActivity.this);
        viewPager.setAdapter (slideAdapter);

        Next1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (getApplicationContext (),SplashActivity.class));
                finish ();
            }
        });
        AddDots (0);

        viewPager.addOnPageChangeListener (new ViewPager.OnPageChangeListener () {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                AddDots (position);

                mCurrentPage = position;
                if (position == 0){
                    Next1.setVisibility (View.INVISIBLE);
                    Next1.setEnabled (false);
                    Next1.setText ("");
                }else if (position == tv_dots.length - 2){
                    Next1.setVisibility (View.INVISIBLE);
                    Next1.setEnabled (false);
                    Next1.setText ("");
                }else {
                    Next1.setVisibility (View.VISIBLE);
                    Next1.setEnabled (true);
                    Next1.setText ("Start");
                }



            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void AddDots(int position){
        tv_dots = new TextView[3];
        Layout_dots.removeAllViews ();
        for (int i = 0 ; i< tv_dots.length ; i++){
            tv_dots[i] = new TextView (SlideActivity.this);
            tv_dots[i].setTextColor (getResources ().getColor (R.color.Trai));

            tv_dots[i].setText (Html.fromHtml ("&#8226;"));
            tv_dots[i].setTextSize (35);

            Layout_dots.addView (tv_dots[i]);
        }
        if (tv_dots.length > 0){
            tv_dots[position].setTextColor (getResources ().getColor (R.color.White));

        }
    }


}
