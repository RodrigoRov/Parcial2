package com.rodrigorov.cometela.parcial2.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.rodrigorov.cometela.parcial2.Models.TopPlayers;
import com.rodrigorov.cometela.parcial2.Repositories.UserNoticiasRepository;

import java.util.List;

public class PlayerViewModel extends AndroidViewModel{

    private UserNoticiasRepository userRepository;

    public PlayerViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserNoticiasRepository(application);
    }

    public LiveData<List<TopPlayers>> getPlayerByGame(String token, String game){
        return userRepository.getPlayersByGame(token,game);
    }
}
