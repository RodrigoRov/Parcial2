package com.rodrigorov.cometela.parcial2.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.rodrigorov.cometela.parcial2.R;

public class LoginFragment extends Fragment{

    EditText username,pass;
    AppCompatButton button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login,container,false);

        username = v.findViewById(R.id.fragment_login_username);
        pass = v.findViewById(R.id.fragment_login_password);

        button = v.findViewById(R.id.fragment_login_button);
        return v;
    }
}
