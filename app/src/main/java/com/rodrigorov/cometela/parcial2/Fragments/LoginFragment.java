package com.rodrigorov.cometela.parcial2.Fragments;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.rodrigorov.cometela.parcial2.R;
import com.rodrigorov.cometela.parcial2.Repositories.UserNoticiasRepository;

public class LoginFragment extends Fragment{

    EditText username,pass;
    AppCompatButton button;
    UserNoticiasRepository userNoticiasRepository;
    Application application;
    String token;

    public void setApplication(Application application) {
        this.application = application;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_login,container,false);



        return v;
    }
}
