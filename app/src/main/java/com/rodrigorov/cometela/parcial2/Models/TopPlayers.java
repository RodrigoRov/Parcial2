package com.rodrigorov.cometela.parcial2.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "toplayer_table")
public class TopPlayers {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "player_id")
    private String playerId;

    @ColumnInfo(name = "player_name")
    private String playerName;

    @ColumnInfo(name = "player_biografia")
    private String playerBiografia;

    @ColumnInfo(name = "player_avatar")
    private String playerAvatar;

    @ColumnInfo(name = "player_game")
    private String playerGame;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerBiografia() {
        return playerBiografia;
    }

    public void setPlayerBiografia(String playerBiografia) {
        this.playerBiografia = playerBiografia;
    }

    public String getPlayerAvatar() {
        return playerAvatar;
    }

    public void setPlayerAvatar(String playerAvatar) {
        this.playerAvatar = playerAvatar;
    }

    public String getPlayerGame() {
        return playerGame;
    }

    public void setPlayerGame(String playerGame) {
        this.playerGame = playerGame;
    }
}
