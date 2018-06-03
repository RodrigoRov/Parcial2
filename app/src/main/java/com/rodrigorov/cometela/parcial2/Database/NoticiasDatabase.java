package com.rodrigorov.cometela.parcial2.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.rodrigorov.cometela.parcial2.Database.Daos.NoticiaDao;
import com.rodrigorov.cometela.parcial2.Database.Daos.UserDao;
import com.rodrigorov.cometela.parcial2.Models.Noticia;
import com.rodrigorov.cometela.parcial2.Models.User;

@Database(entities = {User.class, Noticia.class}, version = 1)
public abstract class NoticiasDatabase extends RoomDatabase{
    public abstract UserDao userDao();
    public abstract NoticiaDao noticiaDao();

    private static NoticiasDatabase INSTANCE;

    public static NoticiasDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (NoticiasDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NoticiasDatabase.class,"noticias_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
