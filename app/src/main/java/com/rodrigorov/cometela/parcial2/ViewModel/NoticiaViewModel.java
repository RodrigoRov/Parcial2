package com.rodrigorov.cometela.parcial2.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

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
        Log.d("Token",token);
        Log.d("UserId",userId);
        Log.d("Noticia Id",noticiaId);
        userRepository.setFavoritos(token,userId,noticiaId);
    }

    public void deleteFavoritos(String token,String userId,String noticiaId){
        userRepository.deleteFavoritos(token,userId,noticiaId);
    }

    public LiveData<Noticia> getNoticia(String token,String idNoticia){
        return userRepository.getNoticia(token,idNoticia);
    }

    public LiveData<List<Noticia>> getNoticiaByGame(String token,String game){
        return userRepository.getNoticiaByGame(token,game);
    }

    public LiveData<String []> getCategorias(String token){
        return userRepository.getCategorias(token);
    }

}
