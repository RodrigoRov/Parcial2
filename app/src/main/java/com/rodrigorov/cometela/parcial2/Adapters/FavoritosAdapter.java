package com.rodrigorov.cometela.parcial2.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rodrigorov.cometela.parcial2.R;

import java.io.InputStream;
import java.util.ArrayList;

public class FavoritosAdapter extends RecyclerView.Adapter<FavoritosAdapter.ViewHolder>{
    Context context;
    ArrayList<String> urls;
    ArrayList<String> noticias;

    public FavoritosAdapter(Context context, ArrayList<String> urls,ArrayList<String> noticias){
        this.context = context;
        this.urls = urls;
        this.noticias = noticias;
    }

    @NonNull
    @Override
    public FavoritosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_favoritos,parent,false);
        return new FavoritosAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        new DownloadImageTask(holder.imageView).execute(urls.get(position));
        holder.nombre.setText(noticias.get(position));
    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView nombre,desc;
        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cardview_favoritos_imagen);
            nombre = itemView.findViewById(R.id.cardview_favoritos_nombre);
            desc = itemView.findViewById(R.id.cardview_favoritos_nota);
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
