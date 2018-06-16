package com.rodrigorov.cometela.parcial2.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.rodrigorov.cometela.parcial2.Models.TopPlayers;
import com.rodrigorov.cometela.parcial2.Repositories.Repository;

import java.util.List;

public class PlayerViewModel extends AndroidViewModel{

    private Repository userRepository;

    public PlayerViewModel(@NonNull Application application) {
        super(application);
        userRepository = new Repository(application);
    }

    public LiveData<List<TopPlayers>> getPlayerByGame(String game){
        return userRepository.getPlayersByGame(game);
    }

    public void getAllPlayers(String token){
        userRepository.getAllPlayers(token);
    }

    public void deleteAll(){
        userRepository.deletePlayers();
    }
}
