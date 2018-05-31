package com.rodrigorov.cometela.parcial2.Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter{
    List<String> titleList;
    List<Fragment> fragments;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        titleList = new ArrayList<>();
        fragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    public void AddItem(String titleList,Fragment fragment){
        this.titleList.add(titleList);
        fragments.add(fragment);
    }
}

