package com.hfad.lzr.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.lzr.R;

public class GameViewHolder extends RecyclerView.ViewHolder {

    public TextView teamAnameTV, teamBnameTV, resAtv, resBtv, gameDateTV, gameTimeTV, gameVenueTV;
    public ImageView teamAimage, teamBimage;
    public LinearLayout upcomingGameLL;
    public GameViewHolder(@NonNull View itemView) {
        super(itemView);

        teamAnameTV = itemView.findViewById(R.id.teamAname_tv);
        teamBnameTV = itemView.findViewById(R.id.teamBname_tv);
        resAtv = itemView.findViewById(R.id.resA_tv);
        resBtv = itemView.findViewById(R.id.resB_tv);
        gameDateTV = itemView.findViewById(R.id.gameDate_tv);
        gameTimeTV = itemView.findViewById(R.id.gameTime_tv);
        gameVenueTV = itemView.findViewById(R.id.gameVenue_tv);
        teamAimage = itemView.findViewById(R.id.teamA_image);
        teamBimage = itemView.findViewById(R.id.teamB_image);
        upcomingGameLL = itemView.findViewById(R.id.upcomingGame_ll);
    }
}
