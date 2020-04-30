package com.hfad.lzr.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.lzr.R;
import com.hfad.lzr.model.Team;

import java.util.ArrayList;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {

    private ArrayList<Team> mTeamList;
   /* private OnItemClickListener mListener;
    public int selectedPos = RecyclerView.NO_POSITION;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void onItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }*/

    static class ViewHolder extends  RecyclerView.ViewHolder{

        TextView positionTV, teamNameTV, totalGamesTV, wGamesTV, lGamesTV, ptsPlusMinusTV, pointsTV;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            positionTV = itemView.findViewById(R.id.positionTV);
            teamNameTV = itemView.findViewById(R.id.teamNameTV);
            totalGamesTV = itemView.findViewById(R.id.totalGamesTV);
            wGamesTV = itemView.findViewById(R.id.wGamesTV);
            lGamesTV = itemView.findViewById(R.id.lGamesTV);
            ptsPlusMinusTV = itemView.findViewById(R.id.ptsPlusMinusTV);
            pointsTV = itemView.findViewById(R.id.pointsTV);
        }

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }

                }
            });*/
        }

    public TeamAdapter(ArrayList<Team> teams){
        mTeamList = teams;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.standings_item, parent, false);
       /* ViewHolder vh = new ViewHolder(view, mListener);*/
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Team team = mTeamList.get(position);
        holder.teamNameTV.setText(team.getName());
        holder.ptsPlusMinusTV.setText(String.valueOf(team.getPointsScored() - team.getPointsReceived()));
        holder.pointsTV.setText(String.valueOf(team.getPoints()));
        holder.totalGamesTV.setText(String.valueOf((team.getPlayed())));
        holder.wGamesTV.setText(String.valueOf(team.getWin()));
        holder.lGamesTV.setText(String.valueOf(team.getLost()));
        holder.positionTV.setText(String.valueOf(position + 1));
        /*holder.itemView.setSelected(selectedPos == position);*/
    }

    @Override
    public int getItemCount() {
        return mTeamList.size();
    }


}
