package com.rodrigorov.cometela.parcial2.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.rodrigorov.cometela.parcial2.Models.User;
import com.rodrigorov.cometela.parcial2.R;
import com.rodrigorov.cometela.parcial2.ViewModel.UserViewModel;

import java.util.Objects;

public class SettingsFragment extends android.support.v4.app.Fragment{

    EditText username,pass,confpass;
    AppCompatButton guardar,cancelar;
    Boolean editar = true;
    UserViewModel viewModel;
    android.support.v4.app.Fragment fragment = this;
    User thisUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings,container,false);

        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        final String token = sharedPref.getString("TOKEN","");

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        username = v.findViewById(R.id.fragment_settings_username);
        pass = v.findViewById(R.id.fragment_settings_password);
        confpass = v.findViewById(R.id.fragment_settings_confpassword);
        guardar =  v.findViewById(R.id.guardar_cambios_perfil);
        cancelar = v.findViewById(R.id.cancelar_cambios_perfil);

        viewModel.getUser().observe(fragment, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                thisUser = user;
                username.setText(user.getUser());
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pass.getText().toString().equals(confpass.getText().toString())) {
                    username.setEnabled(false);
                    pass.setEnabled(false);
                    confpass.setEnabled(false);
                    cancelar.setText(R.string.edit);
                    guardar.setEnabled(false);
                    editar = true;
                    viewModel.modifyUser(token,thisUser.getId(),pass.getText().toString());
                    pass.setText("");
                    confpass.setText("");
                    Toast.makeText(getContext(),"Se han realizado los cambios",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(),"Las contraseñas deben ser iguales",Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editar){
                    username.setEnabled(editar);
                    pass.setEnabled(editar);
                    confpass.setEnabled(editar);
                    guardar.setEnabled(editar);
                    editar = !editar;
                    cancelar.setText(R.string.cancel);
                }
                else{
                    username.setEnabled(editar);
                    pass.setEnabled(editar);
                    confpass.setEnabled(editar);
                    guardar.setEnabled(false);
                    editar = !editar;
                    cancelar.setText(R.string.edit);
                }
            }
        });

        return v;
    }
}
