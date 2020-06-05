package com.eduvision.version2.corona.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.eduvision.version2.corona.R;

public class SlideAdapter extends PagerAdapter {

    private Context context;

    public SlideAdapter(Context context) {
        this.context = context;
    }

    private int[] slider_images = {

    };

    private String [] slider_text = {
            "Rentable",
            "Sur",
            "Efficace"
    };

    private String[] slide_desc = {
            "Lorem ipsum dolor sit ametsed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud",
            "Lorem ipsum dolor sit ametsed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud",
            "Lorem ipsum dolor sit ametsed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud"

    };

    @Override
    public int getCount() {
        return slider_text.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater   layoutInflater = (LayoutInflater) context.getSystemService (context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate (R.layout.slide_layout,container,false);

        TextView tv_title = view.findViewById (R.id.tv_text);
        TextView tv_Desc = view.findViewById (R.id.tv_desc);

        tv_title.setText (slider_text[position]);
        tv_Desc.setText (slide_desc[position]);

        container.addView (view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView ((RelativeLayout)object);
    }
}














