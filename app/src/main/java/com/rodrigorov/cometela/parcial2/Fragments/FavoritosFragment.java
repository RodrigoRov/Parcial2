package com.rodrigorov.cometela.parcial2.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rodrigorov.cometela.parcial2.Adapters.GeneralNewsAdapter;
import com.rodrigorov.cometela.parcial2.Models.User;
import com.rodrigorov.cometela.parcial2.R;
import com.rodrigorov.cometela.parcial2.ViewModel.NoticiaViewModel;
import com.rodrigorov.cometela.parcial2.ViewModel.UserViewModel;

import java.util.Objects;

public class FavoritosFragment  extends Fragment{

    NoticiaViewModel noticiaViewModel;
    UserViewModel userViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_generalnews,container,false);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        noticiaViewModel = ViewModelProviders.of(this).get(NoticiaViewModel.class);

        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        String token = sharedPref.getString("TOKEN","");

        RecyclerView recyclerView = v.findViewById(R.id.generalnews_fragment_recyclerview);

        final GeneralNewsAdapter adapter = new GeneralNewsAdapter(getContext(),noticiaViewModel);



        userViewModel.getUser(token).observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                adapter.setUser(user.getId());

            }
        });

        return v;
    }
}
