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

import com.rodrigorov.cometela.parcial2.Adapters.FavoritosAdapter;
import com.rodrigorov.cometela.parcial2.Adapters.GeneralNewsAdapter;
import com.rodrigorov.cometela.parcial2.Models.Noticia;
import com.rodrigorov.cometela.parcial2.Models.User;
import com.rodrigorov.cometela.parcial2.R;
import com.rodrigorov.cometela.parcial2.ViewModel.NoticiaViewModel;
import com.rodrigorov.cometela.parcial2.ViewModel.UserViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FavoritosFragment  extends Fragment{
    GridLayoutManager mLayoutManager;
    NoticiaViewModel noticiaViewModel;
    UserViewModel userViewModel;
    FavoritosFragment fragment = this;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_generalnews,container,false);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        noticiaViewModel = ViewModelProviders.of(this).get(NoticiaViewModel.class);

        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        final String token = sharedPref.getString("TOKEN","");
        String user = sharedPref.getString("UserId","");

        RecyclerView recyclerView = v.findViewById(R.id.generalnews_fragment_recyclerview);

        final FavoritosAdapter adapter = new FavoritosAdapter(getContext(),noticiaViewModel);
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

        userViewModel.getUser(token).observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                String favs = user.getFavoriteNews();
                final List<Noticia> noticias = new ArrayList<>();
                String[] favo = favs.split(",");
                for(int i = 0;i<favo.length;i++) {
                    noticiaViewModel.getNoticia(token, favo[i]).observe(fragment, new Observer<Noticia>() {
                        @Override
                        public void onChanged(@Nullable Noticia noticia) {
                            noticias.add(noticia);
                            adapter.setNoticias(noticias);
                        }
                    });
                }

            }
        });

        return v;
    }
}
