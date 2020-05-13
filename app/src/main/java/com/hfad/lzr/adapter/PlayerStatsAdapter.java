package com.hfad.lzr.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.lzr.R;
import com.hfad.lzr.model.Player;
import com.hfad.lzr.model.PlayerGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PlayerStatsAdapter extends RecyclerView.Adapter<PlayerStatsAdapter.PlayerStatsViewHolder> {

    private ArrayList<Player> mPlayersList;
    String mSelectedParameter;

    static class PlayerStatsViewHolder extends RecyclerView.ViewHolder {

        TextView postionTV, playerTV, parameterTV;
        public PlayerStatsViewHolder(@NonNull View itemView) {
            super(itemView);

            postionTV = itemView.findViewById(R.id.position_tv);
            playerTV = itemView.findViewById(R.id.player_tv);
            parameterTV = itemView.findViewById(R.id.parameter_tv);
        }
    }

    public PlayerStatsAdapter(ArrayList<Player> players, String selectedParameter) {
        mPlayersList = players;
        mSelectedParameter = selectedParameter;
    }

    @NonNull
    @Override
    public PlayerStatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_stats_item, parent, false);
        return new PlayerStatsViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull PlayerStatsViewHolder holder, int position) {
        Player player = mPlayersList.get(position);
        holder.postionTV.setText(String.valueOf(position + 1));
        holder.playerTV.setText(player.getNameAndLastname());
        if(mSelectedParameter.equals("PTS")) {
            holder.parameterTV.setText(String.valueOf(player.getTotalPoints()));
            Collections.sort(mPlayersList, Comparator.comparing(Player::getTotalPoints).reversed());
        } else if (mSelectedParameter.equals("AST")) {
            holder.parameterTV.setText(String.valueOf(player.getTotalAssists()));
            Collections.sort(mPlayersList, Comparator.comparing(Player::getTotalAssists).reversed());
        } else if (mSelectedParameter.equals("REB")) {
            holder.parameterTV.setText(String.valueOf(player.getTotalRebs()));
            Collections.sort(mPlayersList, Comparator.comparing(Player::getTotalRebs).reversed());
        } else if (mSelectedParameter.equals("STL")) {
            holder.parameterTV.setText(String.valueOf(player.getTotalSteals()));
            Collections.sort(mPlayersList, Comparator.comparing(Player::getTotalSteals).reversed());
        } else if (mSelectedParameter.equals("BLK")) {
            holder.parameterTV.setText(String.valueOf(player.getTotalBlocks()));
            Collections.sort(mPlayersList, Comparator.comparing(Player::getTotalBlocks).reversed());
        } else if (mSelectedParameter.equals("TOV")) {
            holder.parameterTV.setText(String.valueOf(player.getTotalTurnovers()));
            Collections.sort(mPlayersList, Comparator.comparing(Player::getTotalTurnovers).reversed());
        } else if (mSelectedParameter.equals("PF")) {
            holder.parameterTV.setText(String.valueOf(player.getTotalFouls()));
            Collections.sort(mPlayersList, Comparator.comparing(Player::getTotalFouls).reversed());
        } else if (mSelectedParameter.equals("TEH")) {
            holder.parameterTV.setText(String.valueOf(player.getTotalTehnical()));
            Collections.sort(mPlayersList, Comparator.comparing(Player::getTotalTehnical).reversed());
        } else {
            holder.parameterTV.setText(String.valueOf(player.getTotalEff()));
            Collections.sort(mPlayersList, Comparator.comparing(Player::getTotalEff).reversed());
        }
    }

    @Override
    public int getItemCount() {
        return mPlayersList.size();
    }
}
