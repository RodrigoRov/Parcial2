package com.rodrigorov.cometela.parcial2.Repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.rodrigorov.cometela.parcial2.Database.Daos.NoticiaDao;
import com.rodrigorov.cometela.parcial2.Database.Daos.UserDao;
import com.rodrigorov.cometela.parcial2.Database.NoticiasDatabase;
import com.rodrigorov.cometela.parcial2.Models.Noticia;
import com.rodrigorov.cometela.parcial2.Models.User;

import java.util.List;

public class UserNoticiasRepository {

    private NoticiaDao noticiaDao;
    private UserDao userDao;
    private LiveData<List<User>> AllUsers;
    private LiveData<List<Noticia>> AllNoticias;

    public UserNoticiasRepository(Application application){
        NoticiasDatabase db = NoticiasDatabase.getDatabase(application);
        userDao = db.userDao();
        noticiaDao = db.noticiaDao();
        AllUsers = userDao.getAllUsers();
        AllNoticias = noticiaDao.getAllNoticias();
    }

    public LiveData<List<User>> getAllUsers() {
        return AllUsers;
    }

    public LiveData<List<Noticia>> getAllNoticias() {
        return AllNoticias;
    }

    public void insertU(User user){
        new insertUAsyncTask(userDao).execute(user);
    }

    public void insertN(Noticia noticia){

    }

    private static class insertUAsyncTask extends AsyncTask<User,Void,Void>{

        private UserDao userDao;

        insertUAsyncTask(UserDao userDao){
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

    private static class insertNAsyncTask extends AsyncTask<Noticia,Void,Void>{

        private NoticiaDao noticiaDao;

        insertNAsyncTask(NoticiaDao noticiaDao){
            this.noticiaDao = noticiaDao;
        }

        @Override
        protected Void doInBackground(Noticia... noticias) {
            noticiaDao.insert(noticias[0]);
            return null;
        }
    }


}
