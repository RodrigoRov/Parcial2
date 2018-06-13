package com.rodrigorov.cometela.parcial2.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.rodrigorov.cometela.parcial2.Models.User;
import com.rodrigorov.cometela.parcial2.Repositories.Repository;

public class UserViewModel extends AndroidViewModel{

    private Repository userRepository;
    private LiveData<String> token;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new Repository(application);
    }

    public void insert(User user){ userRepository.insertU(user);}

    public void initToken(String user,String password){
        if (this.token != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        token = userRepository.login(user,password);
    }

    public LiveData<String> getToken() {
        return token;
    }

    public LiveData<User> getUser(String token) {
        return userRepository.getUserDetail(token);
    }
}
