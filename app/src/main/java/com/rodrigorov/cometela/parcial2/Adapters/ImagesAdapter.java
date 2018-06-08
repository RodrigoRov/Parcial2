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
import android.widget.ImageView;

import com.rodrigorov.cometela.parcial2.Models.Noticia;
import com.rodrigorov.cometela.parcial2.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder>{

    Context context;
    List<Noticia> noticias;

    public ImagesAdapter(Context context, List<Noticia> noticias){
        this.context = context;
        this.noticias = noticias;
    }

    @NonNull
    @Override
    public ImagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_images,parent,false);
        return new ImagesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesAdapter.ViewHolder holder, int position) {
        new DownloadImageTask(holder.imagen).execute(noticias.get(position).getCoverImage());
    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imagen;

        ViewHolder(View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.cardview_images_imagen);
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
}
