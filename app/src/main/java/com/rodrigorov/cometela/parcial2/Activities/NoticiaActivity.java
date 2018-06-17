package com.rodrigorov.cometela.parcial2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.rodrigorov.cometela.parcial2.R;
import com.squareup.picasso.Picasso;

public class NoticiaActivity extends AppCompatActivity{

    ImageView imagen;
    TextView titulo,contenido;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia);

        imagen = findViewById(R.id.activity_noticia_imagen);
        titulo = findViewById(R.id.activity_noticia_titulo);
        contenido = findViewById(R.id.activity_noticia_contenido);

        Intent intent = getIntent();
        String imagen = intent.getStringExtra("imagen");
        String titulo = intent.getStringExtra("titulo");
        String cont = intent.getStringExtra("contenido");

        Picasso.with(this).load(imagen).into(this.imagen);

        this.titulo.setText(titulo);
        this.contenido.setText(cont);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent resultIntent = new Intent();
        setResult(2,resultIntent);
        finish();
    }
}
