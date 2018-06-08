package com.rodrigorov.cometela.parcial2.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rodrigorov.cometela.parcial2.Adapters.ImagesAdapter;
import com.rodrigorov.cometela.parcial2.Models.Noticia;
import com.rodrigorov.cometela.parcial2.R;
import com.rodrigorov.cometela.parcial2.ViewModel.NoticiaViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImagesFragment extends Fragment {
    NoticiaViewModel noticiaViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_images,container,false);

        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        String token = sharedPref.getString("TOKEN","");

        final RecyclerView recyclerView = v.findViewById(R.id.fragment_images_recycler_view);
        noticiaViewModel = ViewModelProviders.of(this).get(NoticiaViewModel.class);


        noticiaViewModel.getAllnoticias(token).observe(this, new Observer<List<Noticia>>() {
            @Override
            public void onChanged(@Nullable List<Noticia> noticias) {
                ImagesAdapter imagesAdapter = new ImagesAdapter(getContext(),noticias);
                recyclerView.setAdapter(imagesAdapter);
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(gridLayoutManager);

        return v;
    }

}
