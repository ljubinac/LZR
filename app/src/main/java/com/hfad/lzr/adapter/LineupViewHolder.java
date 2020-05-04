package com.hfad.lzr.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.lzr.R;

public class LineupViewHolder extends RecyclerView.ViewHolder {

    public TextView playerNumberTV;
    public TextView playerNameTV;
    public CheckBox lineupCheckbox;
    public CheckBox firstLineupCheckBox;
    public LinearLayout ll1;

    public LineupViewHolder(@NonNull View itemView) {
        super(itemView);
        playerNumberTV = itemView.findViewById(R.id.player_number_tv);
        playerNameTV = itemView.findViewById(R.id.player_name_tv);
        lineupCheckbox = itemView.findViewById(R.id.lineup_checkbox);
        firstLineupCheckBox = itemView.findViewById(R.id.first_lineup_checkbox);
        ll1 = itemView.findViewById(R.id.ll1);
    }
}
