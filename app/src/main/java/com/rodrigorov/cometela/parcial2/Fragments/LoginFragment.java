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

import com.rodrigorov.cometela.parcial2.Adapters.GeneralNewsAdapter;
import com.rodrigorov.cometela.parcial2.Api.GameNewsApi;
import com.rodrigorov.cometela.parcial2.Models.Token;
import com.rodrigorov.cometela.parcial2.R;
import com.rodrigorov.cometela.parcial2.Repositories.UserNoticiasRepository;

import java.util.Objects;

import retrofit2.Call;

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
        View v = inflater.inflate(R.layout.fragment_login,container,false);

        userNoticiasRepository = new UserNoticiasRepository(application);
        username = v.findViewById(R.id.fragment_login_username);
        pass = v.findViewById(R.id.fragment_login_password);

        button = v.findViewById(R.id.fragment_login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        token = userNoticiasRepository.login(username.getText().toString(),pass.getText().toString());
                    }
                });
                if (getFragmentManager() != null) {
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    GeneralNewsFragment fragment = new GeneralNewsFragment();
                    fragment.setToken(token);
                    fragmentTransaction.replace(R.id.contentFrame,fragment);
                    fragmentTransaction.commit();
                }
                else{
                    Log.d("Fragmento","Vacio");
                }

            }
        });
        return v;
    }
}
