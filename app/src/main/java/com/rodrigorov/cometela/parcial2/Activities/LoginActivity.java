package com.rodrigorov.cometela.parcial2.Activities;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rodrigorov.cometela.parcial2.Fragments.GeneralNewsFragment;
import com.rodrigorov.cometela.parcial2.R;
import com.rodrigorov.cometela.parcial2.Repositories.UserNoticiasRepository;
import com.rodrigorov.cometela.parcial2.ViewModel.UserViewModel;

public class LoginActivity extends AppCompatActivity{

    EditText username,pass;
    Button button;
    UserViewModel userViewModel;
    LiveData<String> token;
    AppCompatActivity appCompatActivity = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        username = findViewById(R.id.fragment_login_username);
        pass = findViewById(R.id.fragment_login_password);

        /*userViewModel.initToken(username.getText().toString(),pass.getText().toString());
        if(username.getText().toString().equals(" ")){
            Log.d("true","perro");
        }*/

        button = findViewById(R.id.fragment_login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Recube","click");
                userViewModel.initToken(username.getText().toString(),pass.getText().toString());

                userViewModel.getToken().observe(appCompatActivity, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("token",s);
                        setResult(1,returnIntent);
                        finish();
                    }
                });
            }
        });


    }


}
