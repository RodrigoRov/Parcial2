package com.rodrigorov.cometela.parcial2.Database.Daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.rodrigorov.cometela.parcial2.Models.TopPlayers;

import java.util.List;

@Dao
public interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TopPlayers... players);

    @Query("DELETE FROM toplayer_table")
    void deleteAll();

    @Query("SELECT * FROM toplayer_table WHERE player_game = :game")
    List<TopPlayers> getAllPlayersByGame(String game);
}
