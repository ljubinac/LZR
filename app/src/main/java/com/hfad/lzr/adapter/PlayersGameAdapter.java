package com.hfad.lzr.adapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.lzr.R;
import com.hfad.lzr.model.PlayerGame;
import com.hfad.lzr.ui.main.CustomLinearLayout;

import java.util.ArrayList;

public class PlayersGameAdapter extends RecyclerView.Adapter<PlayersGameAdapter.ViewHolder> {

    private ArrayList<PlayerGame> mPlayersList;
    //private ArrayList<Integer> mStarting5;
    private OnItemClickListener mListener;
    public int selectedPos = RecyclerView.NO_POSITION;
    private String mTeam;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onLongClick(int position);
    }

    public void onItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView playerGameNumberTV;
        TextView playerLastnameTV;
        CustomLinearLayout cll;
        View dividerView;
        ImageView jeseyIV;

        ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            playerGameNumberTV = itemView.findViewById(R.id.player_game_number);
            playerLastnameTV = itemView.findViewById(R.id.lastNameTV);
            cll = itemView.findViewById(R.id.cll);
            dividerView = itemView.findViewById(R.id.dividerView);
            jeseyIV = itemView.findViewById(R.id.jersey_id);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(16, 0, 8, 0);
            cll.setLayoutParams(params);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onLongClick(position);
                        }
                    }
                    return true;
                }
            });
        }
    }

    public PlayersGameAdapter(ArrayList<PlayerGame> playersGame, String team) {
        mPlayersList = playersGame;
        mTeam = team;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_game_item, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        PlayerGame playerGame = mPlayersList.get(position);
        holder.playerGameNumberTV.setText(playerGame.getNumber());
        String[] playerName = playerGame.getNameAndLastname().split(" ");
        if (playerName.length < 2) {
            holder.playerLastnameTV.setText(playerName[0]);
        } else {
            holder.playerLastnameTV.setText(playerName[1]);
        }
        holder.itemView.setSelected(selectedPos == position);
        if (mTeam.equals("teamA")) {
            if (holder.itemView.isSelected()) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

                params.setMargins(0, 0, pxTOdp(8), 0);
                holder.cll.setLayoutParams(params);
            } else {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(pxTOdp(16), 0, pxTOdp(8), 0);
                holder.cll.setLayoutParams(params);
            }
        }

        if (mTeam.equals("teamB")) {
            if (holder.itemView.isSelected()) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(pxTOdp(8), 0, 0, 0);
                holder.cll.setLayoutParams(params);
            } else {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(pxTOdp(8), 0, pxTOdp(16), 0);
                holder.cll.setLayoutParams(params);
            }
        }

        if (position == 4)
            holder.dividerView.setVisibility(View.VISIBLE);
        else
            holder.dividerView.setVisibility(View.GONE);

        if (position < 5) {
            holder.cll.setmIsIn(true);
        } else {
            holder.cll.setmIsOut(true);
        }
        if (playerGame.ismIsEnabled()) {
            holder.itemView.setEnabled(true);
        } else {
            holder.itemView.setEnabled(false);
        }
        if (playerGame.ismIsChangeOut()) {
            holder.cll.setmIsChangeOut(true);
            holder.cll.setmIsIn(false);
            holder.cll.setmIsOut(false);
        }
        if (playerGame.ismIsChangeIn()) {
            holder.cll.setmIsChangeIn(true);
            holder.cll.setmIsOut(false);
        }

    }

    @Override
    public int getItemCount() {
        return mPlayersList.size();
    }

    private int pxTOdp(int dpValue){
        float d = Resources.getSystem().getDisplayMetrics().density;
        int margin = (int)(dpValue * d); // margin in pixels
        return margin;
    }

}
