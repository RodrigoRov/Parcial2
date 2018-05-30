package com.rodrigorov.cometela.parcial2.Models;

import android.graphics.Bitmap;
import android.net.Uri;

public class Noticia {

    private String nombre;
    private String imagen;

    public Noticia(String nombre,String imagen){
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
