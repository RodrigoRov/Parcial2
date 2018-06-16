package com.rodrigorov.cometela.parcial2.Database.Daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.text.BoringLayout;

import com.rodrigorov.cometela.parcial2.Models.Noticia;

import java.util.List;

@Dao
public interface NoticiaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Noticia... noticias);

    @Query("DELETE FROM noticia_table")
    void deleteAll();

    @Query("SELECT * FROM noticia_table")
    LiveData<List<Noticia>> getAllNoticias();

    @Query("SELECT * FROM noticia_table WHERE noticia_juego = :game")
    List<Noticia> getNoticiaByGame(String game);

    @Query("UPDATE noticia_table SET noticia_fav = :isfav WHERE noticia_id = :id")
    void setNoticiaFav(Boolean isfav, String id);




}
