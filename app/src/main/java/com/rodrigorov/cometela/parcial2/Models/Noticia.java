package com.rodrigorov.cometela.parcial2.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "noticia_table")
public class Noticia {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "noticia_id")
    private int id;

    @ColumnInfo(name = "noticia_titulo")
    private String title;

    @ColumnInfo(name = "noticia_imagen")
    private String coverImage;

    @ColumnInfo(name = "noticia_fecha")
    private String create_date;

    @ColumnInfo(name = "noticia_descripcion")
    private String description;

    @ColumnInfo(name = "noticia_contenido")
    private String body;

    @ColumnInfo(name = "noticia_juego")
    private String game;

    public Noticia(String title, String imagen){
        this.title = title;
        this.coverImage = imagen;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }
}
