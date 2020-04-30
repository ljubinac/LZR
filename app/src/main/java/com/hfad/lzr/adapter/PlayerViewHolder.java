package com.hfad.lzr.adapter;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.lzr.R;

public class PlayerViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout playerLL1;
    public LinearLayout playerLL2;
    public TextView playerNumberTV;
    public TextView playerNameTV;
    public ImageView playerEdit;
    public ImageView playerDelete;
    public EditText playerNumberET;
    public EditText playerNameET;
    public ImageView playerAccept, cancelEdit;

    public PlayerViewHolder(@NonNull View itemView) {
        super(itemView);
        playerLL1 = itemView.findViewById(R.id.ll1);
        playerLL2 = itemView.findViewById(R.id.ll2);
        playerNumberTV = itemView.findViewById(R.id.player_number_tv);
        playerNameTV = itemView.findViewById(R.id.player_name_tv);
        playerEdit = itemView.findViewById(R.id.image_edit);
        playerDelete = itemView.findViewById(R.id.image_delete);
        playerNumberET = itemView.findViewById(R.id.player_number_et);
        playerNameET = itemView.findViewById(R.id.player_name_et);
        playerAccept = itemView.findViewById(R.id.image_accept);
        cancelEdit = itemView.findViewById(R.id.cancel_edit_image);
    }
}
