package com.rodrigorov.cometela.parcial2.Fragments;

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
import com.rodrigorov.cometela.parcial2.R;

import java.util.ArrayList;

public class ImagesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_images,container,false);

        RecyclerView recyclerView = v.findViewById(R.id.fragment_images_recycler_view);

        ArrayList<String> ima = new ArrayList<>();
        fillList(ima);

        ImagesAdapter imagesAdapter = new ImagesAdapter(getContext(),ima);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setAdapter(imagesAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        return v;
    }

    void fillList(ArrayList<String> noticias){
        noticias.add("https://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png");
        noticias.add("https://images.google.com.sv/imgres?imgurl=https%3A%2F%2Fvignette.wikia.nocookie.net%2Fleagueoflegends%2Fimages%2Fe%2Fe0%2FZoe_OriginalCentered.jpg%2Frevision%2Flatest%2Fscale-to-width-down%2F1215%3Fcb%3D20180414203803&imgrefurl=http%3A%2F%2Fleagueoflegends.wikia.com%2Fwiki%2FZoe&docid=NUr1bO66W9l0RM&tbnid=VP8_yhqK1OikVM%3A&vet=1&w=1215&h=683&source=sh%2Fx%2Fim");
        noticias.add("https://images.google.com.sv/imgres?imgurl=https%3A%2F%2Fvignette.wikia.nocookie.net%2Fleagueoflegends%2Fimages%2F0%2F0f%2FJhin_OriginalCentered.jpg%2Frevision%2Flatest%2Fscale-to-width-down%2F1215%3Fcb%3D20180414203247&imgrefurl=http%3A%2F%2Fleagueoflegends.wikia.com%2Fwiki%2FJhin&docid=7QAnSIddwVmNTM&tbnid=cZlPjdRaHZz20M%3A&vet=1&w=1215&h=683&source=sh%2Fx%2Fim");
        noticias.add("https://images.google.com.sv/imgres?imgurl=https%3A%2F%2Fvignette.wikia.nocookie.net%2Fleagueoflegends%2Fimages%2F0%2F0f%2FJhin_OriginalCentered.jpg%2Frevision%2Flatest%2Fscale-to-width-down%2F1215%3Fcb%3D20180414203247&imgrefurl=http%3A%2F%2Fleagueoflegends.wikia.com%2Fwiki%2FJhin&docid=7QAnSIddwVmNTM&tbnid=cZlPjdRaHZz20M%3A&vet=1&w=1215&h=683&source=sh%2Fx%2Fim");
    }
}
