package com.hfad.lzr.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.lzr.R;
import com.hfad.lzr.model.PlayerGame;
import com.hfad.lzr.ui.main.CustomLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class PlayersGameAdapter extends RecyclerView.Adapter<PlayersGameAdapter.ViewHolder> {

    private ArrayList<PlayerGame> mPlayersList;
    //private ArrayList<Integer> mStarting5;
    private OnItemClickListener mListener;
    public int selectedPos = RecyclerView.NO_POSITION;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onLongClick(int position);
    }

    public void onItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView playerGameNumberTV;
        public TextView playerLastnameTV;
        public CustomLinearLayout cll;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            playerGameNumberTV = itemView.findViewById(R.id.player_game_number);
            playerLastnameTV = itemView.findViewById(R.id.lastNameTV);
            cll = itemView.findViewById(R.id.cll);

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

    public PlayersGameAdapter(ArrayList<PlayerGame> playersGame) {
        mPlayersList = playersGame;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_game_item, parent, false);
        ViewHolder vh = new ViewHolder(view, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        PlayerGame playerGame = mPlayersList.get(position);
        holder.playerGameNumberTV.setText(playerGame.getNumber());
        holder.playerLastnameTV.setText(playerGame.getNameAndLastname().split(" ")[1]);
        holder.itemView.setSelected(selectedPos == position);
        if (position < 5) {
            holder.cll.setmIsIn(true);
        } else {
            holder.cll.setmIsOut(true);
            holder.itemView.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return mPlayersList.size();
    }


}
