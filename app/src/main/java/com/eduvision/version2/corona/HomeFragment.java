package com.eduvision.version2.corona;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.eduvision.version2.corona.Adapter.Home;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;


public class HomeFragment extends Fragment {
    public HomeFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home , container , false);


        TabLayout tabLayout=view.findViewById(R.id.tablayouts);
        AppBarLayout appBarLayout=view.findViewById(R.id.appBar);
        ViewPager viewPager =view.findViewById(R.id.view);
        Home adapter=new Home (getChildFragmentManager());
        adapter.AddFragment(new NewsFragment(),"News");
        adapter.AddFragment(new CategoriFragmnet(),"Categories");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

}
