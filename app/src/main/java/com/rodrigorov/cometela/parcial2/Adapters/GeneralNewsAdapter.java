package com.rodrigorov.cometela.parcial2.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rodrigorov.cometela.parcial2.Models.Noticia;
import com.rodrigorov.cometela.parcial2.R;
import com.rodrigorov.cometela.parcial2.ViewModel.NoticiaViewModel;

import java.io.InputStream;
import java.util.List;

public class GeneralNewsAdapter extends RecyclerView.Adapter<GeneralNewsAdapter.ViewHolder> {
    Context context;
    List<Noticia> noticias;
    NoticiaViewModel noticiaViewModel;
    String token;
    String user;


    public GeneralNewsAdapter(Context context, NoticiaViewModel noticiaViewModel){
        this.context = context;
        this.noticiaViewModel = noticiaViewModel;
    }

    @NonNull
    @Override
    public GeneralNewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_generalnews,parent,false);
        return new GeneralNewsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Noticia noticia = noticias.get(position);

        new DownloadImageTask(holder.imageView).execute(noticia.getCoverImage());
        holder.titulo.setText(noticia.getTitle());
        holder.subtitulo.setText(noticia.getDescription());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noticiaViewModel.setFavoritos(token,user,noticia.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (noticias != null)
            return noticias.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titulo,subtitulo;
        ImageButton imageButton;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cardview_generalnews_imagen);
            titulo = itemView.findViewById(R.id.cardview_generalnews_nombre);
            subtitulo = itemView.findViewById(R.id.cardview_generalnews_nota);
            imageButton = itemView.findViewById(R.id.cardview_generalnews_boton);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public void setNoticias(List<Noticia> noticias) {
        Log.d("Entra","Al set");
        this.noticias = noticias;
        notifyDataSetChanged();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
