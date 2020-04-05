package com.hfad.lzr.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.lzr.R;
import com.hfad.lzr.model.PlayerGame;

import java.util.ArrayList;

public class PlayersGameAdapter extends RecyclerView.Adapter<PlayersGameAdapter.ViewHolder> {

    private ArrayList<PlayerGame> mPlayersList;

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView playerNumberTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            playerNumberTV = itemView.findViewById(R.id.player_number_tv);
        }
    }

    public PlayersGameAdapter(ArrayList<PlayerGame> playersList){
        mPlayersList = playersList;
    }

    @NonNull
    @Override
    public PlayersGameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_game_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayersGameAdapter.ViewHolder holder, int position) {
        PlayerGame currentPlayerGame = mPlayersList.get(position);
        holder.playerNumberTV.setText(currentPlayerGame.getNumber());
    }

    @Override
    public int getItemCount() {
        return mPlayersList.size();
    }

}
