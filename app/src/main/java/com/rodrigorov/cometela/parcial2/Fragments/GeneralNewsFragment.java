package com.rodrigorov.cometela.parcial2.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rodrigorov.cometela.parcial2.Adapters.GeneralNewsAdapter;
import com.rodrigorov.cometela.parcial2.Models.Noticia;
import com.rodrigorov.cometela.parcial2.Models.User;
import com.rodrigorov.cometela.parcial2.R;
import com.rodrigorov.cometela.parcial2.ViewModel.NoticiaViewModel;
import com.rodrigorov.cometela.parcial2.ViewModel.UserViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GeneralNewsFragment extends Fragment {
    GridLayoutManager mLayoutManager;
    RecyclerView recyclerView;
    NoticiaViewModel noticiaViewModel;
    UserViewModel userViewModel;
    int filtro =0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_generalnews,container,false);


        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        String token = sharedPref.getString("TOKEN","");
        String user = sharedPref.getString("UserId","");

        noticiaViewModel = ViewModelProviders.of(this).get(NoticiaViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        final GeneralNewsAdapter adapter = new GeneralNewsAdapter(getContext(),noticiaViewModel);
        adapter.setToken(token);
        adapter.setUser(user);

        recyclerView = v.findViewById(R.id.generalnews_fragment_recyclerview);


        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position % 3 == 0 ? 2 : 1);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(mLayoutManager);

        switch (filtro){
            case 0:
                noticiaViewModel.getAllnoticias(token).observe(this, new Observer<List<Noticia>>() {
                    @Override
                    public void onChanged(@Nullable List<Noticia> noticias) {
                        adapter.setNoticias(noticias);
                    }
                });
                break;
            case 1:
                noticiaViewModel.getNoticiaByGame(token,"lol").observe(this, new Observer<List<Noticia>>() {
                    @Override
                    public void onChanged(@Nullable List<Noticia> noticias) {
                        adapter.setNoticias(noticias);
                    }
                });
                break;
            case 2:
                noticiaViewModel.getNoticiaByGame(token,"lol").observe(this, new Observer<List<Noticia>>() {
                    @Override
                    public void onChanged(@Nullable List<Noticia> noticias) {
                        adapter.setNoticias(noticias);
                    }
                });
                break;
            case 3:
                noticiaViewModel.getNoticiaByGame(token,"lol").observe(this, new Observer<List<Noticia>>() {
                    @Override
                    public void onChanged(@Nullable List<Noticia> noticias) {
                        adapter.setNoticias(noticias);
                    }
                });
                break;
        }



        return v;
    }

    public void setFiltro(int filtro) {
        this.filtro = filtro;
    }
}
