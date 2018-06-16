package com.rodrigorov.cometela.parcial2.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rodrigorov.cometela.parcial2.Activities.NoticiaActivity;
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
    private SwipeRefreshLayout swipeContainer;
    GeneralNewsFragment fragment = this;
    int filtro =0;
    String [] cate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_generalnews,container,false);
        noticiaViewModel = ViewModelProviders.of(this).get(NoticiaViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        final String token = sharedPref.getString("TOKEN","");
        String user = sharedPref.getString("UserId","");

        final GeneralNewsAdapter adapter = new GeneralNewsAdapter(getContext(),noticiaViewModel);
        userViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user !=null)
                    adapter.setFavs(user.getFavoriteNews());
            }
        });


        swipeContainer = v.findViewById(R.id.swipeContainer);
        adapter.setToken(token);
        adapter.setUser(user);

        adapter.setOnClick(new GeneralNewsAdapter.onItemClicked() {
            @Override
            public void onItemClick(int position) {
                Noticia noticia = adapter.getNoticias().get(position);
                Intent newIntent = new Intent(getContext(),NoticiaActivity.class);
                newIntent.putExtra("imagen",noticia.getCoverImage());
                newIntent.putExtra("titulo",noticia.getTitle());
                newIntent.putExtra("contenido",noticia.getBody());
                startActivityForResult(newIntent,2);

            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.setNoticias(new ArrayList<Noticia>());
                noticiaViewModel.getAllnoticias(token);
                //TODO REFRESH NOTICIAS
                swipeContainer.setRefreshing(false);
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
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
                noticiaViewModel.getNoticiaByGame(cate[0]).observe(this, new Observer<List<Noticia>>() {
                    @Override
                    public void onChanged(@Nullable List<Noticia> noticias) {
                        adapter.setNoticias(noticias);
                    }
                });
                break;
            case 2:
                noticiaViewModel.getNoticiaByGame(cate[1]).observe(this, new Observer<List<Noticia>>() {
                    @Override
                    public void onChanged(@Nullable List<Noticia> noticias) {
                        adapter.setNoticias(noticias);
                    }
                });
                break;
            case 3:
                noticiaViewModel.getNoticiaByGame(cate[2]).observe(this, new Observer<List<Noticia>>() {
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

    public void setCate(String[] cate) {
        this.cate = cate;
    }
}
