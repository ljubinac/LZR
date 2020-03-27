package com.hfad.lzr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.lzr.model.Player;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<Player> mPlayersList;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mPlayerName;
        public TextView mPlayerNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mPlayerName = itemView.findViewById(R.id.player_name_edt);
            mPlayerNumber = itemView.findViewById(R.id.player_number_edt);
        }
    }

    public Adapter (ArrayList<Player> playersList){
        mPlayersList = playersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
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
