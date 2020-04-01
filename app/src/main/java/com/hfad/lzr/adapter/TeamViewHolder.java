package com.hfad.lzr.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.lzr.R;

public class TeamViewHolder extends RecyclerView.ViewHolder {


    public TextView teamName;
    public ImageView teamDelete;
    public TeamViewHolder(@NonNull View itemView) {
        super(itemView);
        teamName = itemView.findViewById(R.id.teamNameTV);
        teamDelete = itemView.findViewById(R.id.image_delete);
    }
}
