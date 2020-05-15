package com.hfad.lzr.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.lzr.R;

public class LeagueViewHolder extends RecyclerView.ViewHolder {
    public TextView leagueNameTV;
    public ImageView deleteLeagueImage;
    public LeagueViewHolder(@NonNull View itemView) {
        super(itemView);
        leagueNameTV = itemView.findViewById(R.id.league_name_tv);
        deleteLeagueImage = itemView.findViewById(R.id.image_delete_league);
    }
}
