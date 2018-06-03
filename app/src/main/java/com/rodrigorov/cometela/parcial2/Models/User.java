package com.rodrigorov.cometela.parcial2.Models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name =  "user_id")
    private String id;

    @ColumnInfo(name = "user_name")
    private String user;

    @ColumnInfo(name = "user_password")
    private String password;

    @ColumnInfo(name = "user_favorite_news")
    private String[] favoriteNews;
}
