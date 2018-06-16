package com.rodrigorov.cometela.parcial2.Fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rodrigorov.cometela.parcial2.Adapters.ViewPagerAdapter;
import com.rodrigorov.cometela.parcial2.Models.TopPlayers;
import com.rodrigorov.cometela.parcial2.R;

public class GamesViewPagerFragment extends Fragment{

    String [] cate;
    int filtro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_pager,container,false);

        System.out.println("Filtro" + filtro);
        System.out.println(cate[filtro-1]);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        GeneralNewsFragment fragment = new GeneralNewsFragment();
        fragment.setCate(cate);
        fragment.setFiltro(filtro);
        viewPagerAdapter.AddItem("Noticias",fragment);

        TopPlayersFragment toplayerfragment = new TopPlayersFragment();
        toplayerfragment.setCate(cate);
        toplayerfragment.setFiltro(filtro);
        viewPagerAdapter.AddItem("Top Players",toplayerfragment);

        ImagesFragment imagesFragment = new ImagesFragment();
        imagesFragment.setCate(cate);
        imagesFragment.setFiltro(filtro);
        viewPagerAdapter.AddItem("Imagenes",imagesFragment);

        android.support.v4.view.ViewPager viewPager = v.findViewById(R.id.view_pager);
        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabLayout = v.findViewById(R.id.tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager,true);

        return v;
    }

    public void setCate(String[] cate) {
        this.cate = cate;
    }

    public void setFiltro(int filtro) {
        this.filtro = filtro;
    }
}
