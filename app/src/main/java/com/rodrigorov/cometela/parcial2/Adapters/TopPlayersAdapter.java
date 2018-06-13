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

import com.rodrigorov.cometela.parcial2.Models.TopPlayers;
import com.rodrigorov.cometela.parcial2.R;

import java.util.ArrayList;
import java.util.List;

public class TopPlayersAdapter extends RecyclerView.Adapter<TopPlayersAdapter.ViewHolder>{

    Context context;
    List<TopPlayers> players = null;

    public TopPlayersAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public TopPlayersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_top_players,parent,false);
        return new TopPlayersAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TopPlayersAdapter.ViewHolder holder, int position) {

        TopPlayers player = players.get(position);
        /*Player player = players.get(position);
        * holder.nombre.setText(player.getName());
        * */
        if(player != null) {
            holder.nombre.setText(player.getPlayerName());
            holder.desc.setText(player.getPlayerBiografia());
        }

    }

    @Override
    public int getItemCount() {
        if (players != null){
            System.out.println("PLAYERS SIZE"+ players.size());
            return players.size();
        }
        else {
            return 0;
        }
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

    public void setPlayers(List<TopPlayers> players) {
        this.players = players;
        notifyDataSetChanged();
    }
}
