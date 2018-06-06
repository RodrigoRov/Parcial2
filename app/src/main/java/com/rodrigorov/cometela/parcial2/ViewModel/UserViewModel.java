package com.rodrigorov.cometela.parcial2.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.rodrigorov.cometela.parcial2.Models.Noticia;
import com.rodrigorov.cometela.parcial2.Models.Token;
import com.rodrigorov.cometela.parcial2.Models.User;
import com.rodrigorov.cometela.parcial2.Repositories.UserNoticiasRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel{

    private UserNoticiasRepository userRepository;
    private LiveData<List<User>> Allusers;
    private LiveData<List<Noticia>> Allnoticias;
    private List<Noticia> noticias;
    private LiveData<String> token;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserNoticiasRepository(application);
        Allusers = userRepository.getAllUsers();
        if(getToken()!=null)
            Allnoticias = userRepository.getAllNoticias(getToken().getValue());
        else
            Allnoticias=userRepository.getAllNoticias();
    }

    public LiveData<List<User>> getAllusers() {
        return Allusers;
    }

    public LiveData<List<Noticia>> getAllnoticias() {
        return userRepository.getAllNoticias(getToken().getValue());
    }

    public void insert(User user){ userRepository.insertU(user);}

    public List<Noticia> getNoticias(String token) {
        Log.d("Token UVM",token);
        return userRepository.getNoticias(token);
    }

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
}
