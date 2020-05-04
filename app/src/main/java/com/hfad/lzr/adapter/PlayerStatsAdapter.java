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

    @Override
    public void onBindViewHolder(@NonNull PlayerStatsViewHolder holder, int position) {
        Player player = mPlayersList.get(position);
        holder.postionTV.setText(String.valueOf(position + 1));
        holder.playerTV.setText(player.getNameAndLastname());
        if(mSelectedParameter.equals("PTS")) {
            holder.parameterTV.setText(String.valueOf(player.getTotalPoints()));
        } else if (mSelectedParameter.equals("AST")) {
            holder.parameterTV.setText(String.valueOf(player.getTotalAssists()));
        } else if (mSelectedParameter.equals("REB")) {
            holder.parameterTV.setText(String.valueOf(player.getTotalRebs()));
        } else if (mSelectedParameter.equals("STL")) {
            holder.parameterTV.setText(String.valueOf(player.getTotalSteals()));
        } else if (mSelectedParameter.equals("BLK")) {
            holder.parameterTV.setText(String.valueOf(player.getTotalBlocks()));
        } else if (mSelectedParameter.equals("TOV")) {
            holder.parameterTV.setText(String.valueOf(player.getTotalTurnovers()));
        } else if (mSelectedParameter.equals("PF")) {
            holder.parameterTV.setText(String.valueOf(player.getTotalFouls()));
        } else if (mSelectedParameter.equals("TEH")) {
            holder.parameterTV.setText(String.valueOf(player.getTotalTehnical()));
        }
    }

    @Override
    public int getItemCount() {
        return mPlayersList.size();
    }
}
