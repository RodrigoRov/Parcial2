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
import android.widget.TextView;

import com.rodrigorov.cometela.parcial2.Models.Noticia;
import com.rodrigorov.cometela.parcial2.R;

import java.io.InputStream;
import java.util.List;

public class GeneralNewsAdapter extends RecyclerView.Adapter<GeneralNewsAdapter.ViewHolder> {
    Context context;
    List<Noticia> noticias;

    public GeneralNewsAdapter(Context context,List<Noticia> noticias){
        this.context = context;
        this.noticias = noticias;
    }

    @NonNull
    @Override
    public GeneralNewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_generalnews,parent,false);
        return new GeneralNewsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Noticia noticia = noticias.get(position);

        new DownloadImageTask(holder.imageView).execute(noticia.getImagen());
        holder.textView.setText(noticia.getNombre());
    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cardview_generalnews_imagen);
            textView = itemView.findViewById(R.id.cardview_generalnews_nombre);
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
