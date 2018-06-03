package com.rodrigorov.cometela.parcial2.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.rodrigorov.cometela.parcial2.Models.User;
import com.rodrigorov.cometela.parcial2.Repositories.UserNoticiasRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel{

    private UserNoticiasRepository userRepository;
    private LiveData<List<User>> Allusers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserNoticiasRepository(application);
        Allusers = userRepository.getAllUsers();
    }

    public LiveData<List<User>> getAllusers() {
        return Allusers;
    }

    public void insert(User user){ userRepository.insertU(user);}



}
