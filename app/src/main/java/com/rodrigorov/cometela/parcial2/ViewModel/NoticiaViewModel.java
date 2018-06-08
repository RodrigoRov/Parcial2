package com.rodrigorov.cometela.parcial2.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.rodrigorov.cometela.parcial2.Models.Noticia;
import com.rodrigorov.cometela.parcial2.Repositories.UserNoticiasRepository;

import java.util.List;

public class NoticiaViewModel extends AndroidViewModel{

    private UserNoticiasRepository userRepository;

    public NoticiaViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserNoticiasRepository(application);
    }

    public LiveData<List<Noticia>> getAllnoticias(String token) {
        return userRepository.getAllNoticias(token);
    }

    public void setFavoritos(String token,String userId,String noticiaId){
        userRepository.setFavoritos(token,userId,noticiaId);
    }
}
