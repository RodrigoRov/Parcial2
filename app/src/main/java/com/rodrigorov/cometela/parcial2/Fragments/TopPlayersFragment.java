package com.rodrigorov.cometela.parcial2.Fragments;

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
import com.rodrigorov.cometela.parcial2.R;

import java.util.ArrayList;

public class TopPlayersFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_top_players,container,false);

        ArrayList<String> nombres = new ArrayList<>();
        nombres.add("Faker");
        nombres.add("Onii-chan");

        TopPlayersAdapter adapter = new TopPlayersAdapter(getContext(), nombres);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = v.findViewById(R.id.fragment_top_players_recycler_view);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        return v;
    }
}
