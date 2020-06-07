package com.hfad.lzr.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.lzr.R;

public class ResultsViewHolder extends RecyclerView.ViewHolder {

    public TextView resA, resB, teamA, teamB;

    public ResultsViewHolder(@NonNull View itemView) {
        super(itemView);

        resA = itemView.findViewById(R.id.resA_tv);
        resB = itemView.findViewById(R.id.resB_tv);
        teamA = itemView.findViewById(R.id.teamA_tv);
        teamB = itemView.findViewById(R.id.teamB_tv);
    }
}
