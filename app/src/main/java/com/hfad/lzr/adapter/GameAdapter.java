package com.hfad.lzr.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.lzr.R;
import com.hfad.lzr.model.Game;

import java.util.ArrayList;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ResultsViewHolder> {

    private ArrayList<Game> mGameList;

    static class ResultsViewHolder extends  RecyclerView.ViewHolder{

        public TextView resA, resB, teamA, teamB;

        ResultsViewHolder(@NonNull View itemView) {
            super(itemView);
            resA = itemView.findViewById(R.id.resA_tv);
            resB = itemView.findViewById(R.id.resB_tv);
            teamA = itemView.findViewById(R.id.teamA_tv);
            teamB = itemView.findViewById(R.id.teamB_tv);
        }

    }

    public GameAdapter(ArrayList<Game> games){
        mGameList = games;
    }

    @NonNull
    @Override
    public ResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item, parent, false);
        return new ResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ResultsViewHolder holder, final int position) {
        Game game = mGameList.get(position);
        holder.resA.setText(String.valueOf(game.getResA()));
        holder.resB.setText(String.valueOf(game.getResB()));
        holder.teamA.setText(game.getTeamAnaziv());
        holder.teamB.setText(game.getTeamBnaziv());
        /*holder.itemView.setSelected(selectedPos == position);*/
    }

    @Override
    public int getItemCount() {
        return mGameList.size();
    }


}
