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
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.List;

public class GeneralNewsAdapter extends RecyclerView.Adapter<GeneralNewsAdapter.ViewHolder> {
    Context context;
    List<Noticia> noticias;
    NoticiaViewModel noticiaViewModel;
    String token;
    String user;
    String favs;
    Boolean [] clicked;
    private onItemClicked onClick;

    public interface onItemClicked{
        void onItemClick(int position);
    }

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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Noticia noticia = noticias.get(position);


        Picasso.with(context).load(noticia.getCoverImage()).into(holder.imageView);
        holder.titulo.setText(noticia.getTitle());
        holder.subtitulo.setText(noticia.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position);
            }
        });

        /*if (clicked[position]){
            holder.imageButton.setImageResource(android.R.drawable.btn_star_big_on);
        }
        else{*/
            holder.imageButton.setImageResource(android.R.drawable.btn_star_big_off);
        //}
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicked[position]){
                    holder.imageButton.setImageResource(android.R.drawable.btn_star_big_off);
                    clicked[position] = !clicked[position];
                    noticiaViewModel.deleteFavoritos(token,user,noticia.getId(),favs);
                }
                else{
                    holder.imageButton.setImageResource(android.R.drawable.btn_star_big_on);
                    clicked[position] = !clicked[position];
                    noticiaViewModel.setFavoritos(token,user,noticia.getId(),favs);
                }
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

    public void setNoticias(List<Noticia> noticias) {
        this.noticias = noticias;
        notifyDataSetChanged();
        clicked = new Boolean[noticias.size()];
        int i = 0;
        for(Noticia n:noticias){
            clicked[i] = n.getFav() > 0;
            i++;
        }
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

    public void setOnClick(onItemClicked onClick) {
        this.onClick = onClick;
    }

    public List<Noticia> getNoticias() {
        return noticias;
    }

    public void setFavs(String favs) {
        this.favs = favs;
    }
}
