package com.hfad.lzr.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        void onAcceptClick(int position, String name, String number);
        //void onEditClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mDeletePlayer;
        ImageView mEditPlayer;
        EditText mPlayerNameEt;
        EditText mPlayerNumberEt;
        LinearLayout ll1;
        LinearLayout ll2;
        TextView mPlayerNameTv;
        TextView mPlayerNumberTv;
        ImageView mAccept;
        ImageView mCancel;

        ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mDeletePlayer = itemView.findViewById(R.id.image_delete);
            mEditPlayer = itemView.findViewById(R.id.image_edit);
            mPlayerNameEt = itemView.findViewById(R.id.player_name_et);
            mPlayerNumberEt = itemView.findViewById(R.id.player_number_et);
            ll1 = itemView.findViewById(R.id.ll1);
            ll2 = itemView.findViewById(R.id.ll2);
            mPlayerNameTv = itemView.findViewById(R.id.player_name_tv);
            mPlayerNumberTv = itemView.findViewById(R.id.player_number_tv);
            mAccept = itemView.findViewById(R.id.image_accept);
            mCancel = itemView.findViewById(R.id.cancel_edit_image);



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
        return new ViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Player currentPlayer = mPlayersList.get(position);

        holder.mPlayerNameTv.setText(currentPlayer.getNameAndLastname());
        holder.mPlayerNumberTv.setText(currentPlayer.getNumber());

        holder.mEditPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mPlayerNameEt.setText(holder.mPlayerNameTv.getText().toString());
                holder.mPlayerNumberEt.setText(holder.mPlayerNumberTv.getText().toString());
                holder.ll1.setVisibility(View.GONE);
                holder.ll2.setVisibility(View.VISIBLE);
            }
        });

        holder.mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ll1.setVisibility(View.VISIBLE);
                holder.ll2.setVisibility(View.GONE);
            }
        });

        holder.mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.mPlayerNameTv.setText(holder.mPlayerNameEt.getText().toString());
                holder.mPlayerNumberTv.setText(holder.mPlayerNumberEt.getText().toString());
                holder.ll1.setVisibility(View.VISIBLE);
                holder.ll2.setVisibility(View.GONE);

                mListener.onAcceptClick(position, holder.mPlayerNameTv.getText().toString(), holder.mPlayerNumberTv.getText().toString());
            }
        });


    }

    @Override
    public int getItemCount() {
        return mPlayersList.size();
    }
}
