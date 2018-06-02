package com.rodrigorov.cometela.parcial2.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.rodrigorov.cometela.parcial2.R;

public class SettingsFragment extends android.support.v4.app.Fragment{

    EditText username,pass,confpass;
    AppCompatButton guardar,cancelar;
    Boolean editar = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings,container,false);


        username = v.findViewById(R.id.fragment_settings_username);
        pass = v.findViewById(R.id.fragment_settings_password);
        confpass = v.findViewById(R.id.fragment_settings_confpassword);
        guardar =  v.findViewById(R.id.guardar_cambios_perfil);
        cancelar = v.findViewById(R.id.cancelar_cambios_perfil);

        /*username.setText(user.getFirstName());
        pass.setText(user.getLastName());
        confpass.setText(user.getPhone());*/

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* User nuevoUsu = new User(firstName.getText().toString(),
                        lastName.getText().toString(),
                        telefono.getText().toString(),
                        email.getText().toString(),
                        facebook.getText().toString());

                new UpdateUser(database,nuevoUsu);
                user = nuevoUsu;
                Log.d("User",user.getFirstName());*/

                /*username.setText(user.getFirstName());
                pass.setText(user.getLastName());
                confpass.setText(user.getPhone());*/

                username.setEnabled(false);
                pass.setEnabled(false);
                confpass.setEnabled(false);
                cancelar.setText(R.string.edit);
                guardar.setEnabled(false);
                editar = true;
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
                    /*username.setText(user.getFirstName());
                    pass.setText(user.getLastName());
                    confpass.setText(user.getPhone());*/
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
