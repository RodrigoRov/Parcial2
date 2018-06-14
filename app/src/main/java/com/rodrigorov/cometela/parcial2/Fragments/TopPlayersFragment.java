package com.rodrigorov.cometela.parcial2.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rodrigorov.cometela.parcial2.Adapters.TopPlayersAdapter;
import com.rodrigorov.cometela.parcial2.Models.TopPlayers;
import com.rodrigorov.cometela.parcial2.R;
import com.rodrigorov.cometela.parcial2.ViewModel.PlayerViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TopPlayersFragment extends Fragment{

    PlayerViewModel playerViewModel;
    int filtro = 1;
    String cate[];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_top_players, container, false);

        playerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);

        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        final String token = sharedPref.getString("TOKEN","");
        System.out.println("TOKEN TPF" + token);

        final TopPlayersAdapter adapter = new TopPlayersAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = v.findViewById(R.id.fragment_top_players_recycler_view);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        switch (filtro){
            case 1:
                playerViewModel.getPlayerByGame(token,cate[0]).observe(this, new Observer<List<TopPlayers>>() {
                    @Override
                    public void onChanged(@Nullable List<TopPlayers> topPlayers) {
                        System.out.println("IS TOP PLAYERS EMPTY:" + topPlayers.isEmpty());
                        //System.out.println("TOP PLAYER NAME: " + topPlayers.get(0).getPlayerName());
                        //System.out.println("TOP PLAYER AVATAR: "+topPlayers.get(0).getPlayerAvatar());
                        adapter.setPlayers(topPlayers);
                    }
                });
                break;
            case 2:
                playerViewModel.getPlayerByGame(token,cate[1]).observe(this, new Observer<List<TopPlayers>>() {
                    @Override
                    public void onChanged(@Nullable List<TopPlayers> topPlayers) {
                        adapter.setPlayers(topPlayers);
                    }
                });;
                break;
            case 3:
                playerViewModel.getPlayerByGame(token,cate[2]).observe(this, new Observer<List<TopPlayers>>() {
                    @Override
                    public void onChanged(@Nullable List<TopPlayers> topPlayers) {
                        adapter.setPlayers(topPlayers);
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
