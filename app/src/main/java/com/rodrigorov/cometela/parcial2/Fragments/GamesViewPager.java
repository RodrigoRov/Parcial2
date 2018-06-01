package com.rodrigorov.cometela.parcial2.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rodrigorov.cometela.parcial2.Adapters.ViewPagerAdapter;
import com.rodrigorov.cometela.parcial2.R;

public class GamesViewPager extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_pager,container,false);


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPagerAdapter.AddItem("Noticias",new GeneralNews());
        viewPagerAdapter.AddItem("Top Players",new TopPlayers());
        viewPagerAdapter.AddItem("Imagenes",new ImagesFragment());


        android.support.v4.view.ViewPager viewPager = v.findViewById(R.id.view_pager);
        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabLayout = v.findViewById(R.id.tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager,true);

        return v;
    }
}
