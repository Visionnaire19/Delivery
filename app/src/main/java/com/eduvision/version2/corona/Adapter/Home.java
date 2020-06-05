package com.eduvision.version2.corona.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Home extends FragmentPagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<> ();
    private final List<String> FragmentTitles = new ArrayList<>();

    public Home(@NonNull FragmentManager fm) {
        super (fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);


    }

    @Override
    public int getCount() {
        return FragmentTitles.size();
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return FragmentTitles.get(position);
    }

    public void AddFragment(Fragment fragment, String Title) {
        fragmentList.add(fragment);
        FragmentTitles.add(Title);

    }
}
