package com.hfad.lzr.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.lzr.R;

public class ArenaViewHolder extends RecyclerView.ViewHolder {

    public TextView arenaNameTV;
    public ImageView deleteArenaImage;
    public ArenaViewHolder(@NonNull View itemView) {
        super(itemView);

        arenaNameTV = itemView.findViewById(R.id.arena_name_tv);
        deleteArenaImage = itemView.findViewById(R.id.image_delete_arena);
    }
}
