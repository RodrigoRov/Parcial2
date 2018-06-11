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
        System.out.println(cate[1]);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        GeneralNewsFragment fragment = new GeneralNewsFragment();
        fragment.setCate(cate);
        fragment.setFiltro(filtro);
        viewPagerAdapter.AddItem("Noticias",fragment);
        TopPlayersFragment fragment1 = new TopPlayersFragment();
        fragment1.setCate(cate);
        fragment1.setFiltro(filtro);
        viewPagerAdapter.AddItem("Top Players",fragment1);
        viewPagerAdapter.AddItem("Imagenes",new ImagesFragment());


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
