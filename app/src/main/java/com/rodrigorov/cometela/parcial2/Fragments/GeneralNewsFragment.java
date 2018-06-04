package com.rodrigorov.cometela.parcial2.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rodrigorov.cometela.parcial2.Adapters.GeneralNewsAdapter;
import com.rodrigorov.cometela.parcial2.Models.Noticia;
import com.rodrigorov.cometela.parcial2.R;
import com.rodrigorov.cometela.parcial2.ViewModel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class GeneralNewsFragment extends Fragment {
    GridLayoutManager mLayoutManager;
    RecyclerView recyclerView;
    UserViewModel userViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_generalnews,container,false);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);


        recyclerView = v.findViewById(R.id.generalnews_fragment_recyclerview);
        List<Noticia> noticias = new ArrayList<Noticia>();
        //fillList(noticias);


        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position % 3 == 0 ? 2 : 1);
            }
        });

        recyclerView.setLayoutManager(mLayoutManager);

        userViewModel.getAllnoticias().observe(this, new Observer<List<Noticia>>() {
            @Override
            public void onChanged(@Nullable List<Noticia> noticias) {
                GeneralNewsAdapter adapter = new GeneralNewsAdapter(getContext(),noticias);
                recyclerView.setAdapter(adapter);
            }
        });

        return v;
    }

    /*void fillList(List<Noticia> noticias){
        noticias.add(new Noticia("Zed MasterBaiter","https://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png"));
        noticias.add(new Noticia("Zoe bb","https://images.google.com.sv/imgres?imgurl=https%3A%2F%2Fvignette.wikia.nocookie.net%2Fleagueoflegends%2Fimages%2Fe%2Fe0%2FZoe_OriginalCentered.jpg%2Frevision%2Flatest%2Fscale-to-width-down%2F1215%3Fcb%3D20180414203803&imgrefurl=http%3A%2F%2Fleagueoflegends.wikia.com%2Fwiki%2FZoe&docid=NUr1bO66W9l0RM&tbnid=VP8_yhqK1OikVM%3A&vet=1&w=1215&h=683&source=sh%2Fx%2Fim"));
        noticias.add(new Noticia("Jhin en 4","https://images.google.com.sv/imgres?imgurl=https%3A%2F%2Fvignette.wikia.nocookie.net%2Fleagueoflegends%2Fimages%2F0%2F0f%2FJhin_OriginalCentered.jpg%2Frevision%2Flatest%2Fscale-to-width-down%2F1215%3Fcb%3D20180414203247&imgrefurl=http%3A%2F%2Fleagueoflegends.wikia.com%2Fwiki%2FJhin&docid=7QAnSIddwVmNTM&tbnid=cZlPjdRaHZz20M%3A&vet=1&w=1215&h=683&source=sh%2Fx%2Fim"));
        noticias.add(new Noticia("Irelia bb :3","https://images.google.com.sv/imgres?imgurl=https%3A%2F%2Fvignette.wikia.nocookie.net%2Fleagueoflegends%2Fimages%2F0%2F0f%2FJhin_OriginalCentered.jpg%2Frevision%2Flatest%2Fscale-to-width-down%2F1215%3Fcb%3D20180414203247&imgrefurl=http%3A%2F%2Fleagueoflegends.wikia.com%2Fwiki%2FJhin&docid=7QAnSIddwVmNTM&tbnid=cZlPjdRaHZz20M%3A&vet=1&w=1215&h=683&source=sh%2Fx%2Fim"));
    }*/


}
