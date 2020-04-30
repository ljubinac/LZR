package com.hfad.lzr.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.lzr.R;

public class StandingsViewHolder extends RecyclerView.ViewHolder {

    private TextView positionTV, teamNameTV, totalGamesTV, wGamesTV, lGamesTV, ptsPlusMinusTV, pointsTV;

    public StandingsViewHolder(@NonNull View itemView) {
        super(itemView);
        positionTV = itemView.findViewById(R.id.positionTV);
        teamNameTV = itemView.findViewById(R.id.teamNameTV);
        totalGamesTV = itemView.findViewById(R.id.totalGamesTV);
        wGamesTV = itemView.findViewById(R.id.wGamesTV);
        lGamesTV = itemView.findViewById(R.id.lGamesTV);
        ptsPlusMinusTV = itemView.findViewById(R.id.ptsPlusMinusTV);
        pointsTV = itemView.findViewById(R.id.pointsTV);
    }
}
