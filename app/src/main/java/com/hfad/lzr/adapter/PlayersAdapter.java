package com.hfad.lzr.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.lzr.R;
import com.hfad.lzr.model.Player;

import java.util.ArrayList;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.ViewHolder> {

    private ArrayList<Player> mPlayersList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
       // void onItemClick(int position);
        void onDeleteClick(int position);
        //void onEditClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mPlayerName;
        public TextView mPlayerNumber;
        public ImageView mDeletePlayer;
        public ImageView mEditPlayer;
        public ImageView mAddPlayer;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mPlayerName = itemView.findViewById(R.id.player_name_edt);
            mPlayerNumber = itemView.findViewById(R.id.player_number_edt);
            mDeletePlayer = itemView.findViewById(R.id.image_delete);
            mEditPlayer = itemView.findViewById(R.id.image_edit);


          /*  mEditPlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onEditClick(position);
                        }
                    }
                }
            });*/

           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });*/

            mDeletePlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public PlayersAdapter(ArrayList<Player> playersList){
        mPlayersList = playersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false);
        ViewHolder vh = new ViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Player currentPlayer = mPlayersList.get(position);

        holder.mPlayerName.setText(currentPlayer.getNameAndLastname());
        holder.mPlayerNumber.setText(currentPlayer.getNumber());
    }

    @Override
    public int getItemCount() {
        return mPlayersList.size();
    }
}
