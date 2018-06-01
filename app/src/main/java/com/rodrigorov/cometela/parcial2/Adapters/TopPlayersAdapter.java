package com.rodrigorov.cometela.parcial2.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rodrigorov.cometela.parcial2.R;

import java.util.ArrayList;
import java.util.List;

public class TopPlayersAdapter extends RecyclerView.Adapter<TopPlayersAdapter.ViewHolder>{

    Context context;
    ArrayList<String> players;

    public TopPlayersAdapter(Context context, ArrayList<String> nombres){
        this.context = context;
        players = nombres;
    }

    @NonNull
    @Override
    public TopPlayersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_top_players,parent,false);
        return new TopPlayersAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TopPlayersAdapter.ViewHolder holder, int position) {

        /*Player player = players.get(position);
        * holder.nombre.setText(player.getName());
        * */
        holder.nombre.setText(players.get(position));

    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nombre,desc;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cardview_top_players_imagen);
            nombre = itemView.findViewById(R.id.cardview_top_players_nombre);
            desc = itemView.findViewById(R.id.cardview_top_players_info);
        }
    }
}
