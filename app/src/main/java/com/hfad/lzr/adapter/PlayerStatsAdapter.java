package com.hfad.lzr.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.lzr.R;
import com.hfad.lzr.model.Player;
import com.hfad.lzr.model.PlayerGame;

import java.util.ArrayList;

public class PlayerStatsAdapter extends RecyclerView.Adapter<PlayerStatsAdapter.PlayerStatsViewHolder> {

    private ArrayList<Player> mPlayersList;

    static class PlayerStatsViewHolder extends RecyclerView.ViewHolder {

        TextView playerTV, ptsTV;
        public PlayerStatsViewHolder(@NonNull View itemView) {
            super(itemView);

            playerTV = itemView.findViewById(R.id.player_tv);
            ptsTV = itemView.findViewById(R.id.pts_tv);
        }
    }

    public PlayerStatsAdapter(ArrayList<Player> players) {
        mPlayersList = players;
    }

    @NonNull
    @Override
    public PlayerStatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_stats_item, parent, false);
        return new PlayerStatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerStatsViewHolder holder, int position) {
        Player player = mPlayersList.get(position);
        holder.playerTV.setText(player.getNameAndLastname());
        holder.ptsTV.setText(String.valueOf(player.getTotalPoints()));
    }

    @Override
    public int getItemCount() {
        return mPlayersList.size();
    }
}
