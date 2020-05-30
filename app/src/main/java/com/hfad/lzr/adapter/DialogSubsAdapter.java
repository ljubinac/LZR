package com.hfad.lzr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.lzr.R;
import com.hfad.lzr.model.PlayerGame;

import java.util.ArrayList;

public class DialogSubsAdapter extends RecyclerView.Adapter<DialogSubsAdapter.DialogSubsViewHolder> {

    private int selectedStartPosition = -1;
    private ArrayList<PlayerGame> mSubsList;
    private Context c;
    private AdapterView.OnItemClickListener onItemClickListener;

    public  DialogSubsAdapter(Context context, ArrayList<PlayerGame> subsList){
        this.c = context;
        this.mSubsList = subsList;
    }

    @Override
    public DialogSubsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        final View v = LayoutInflater.from(c).inflate(R.layout.dialog_item, viewGroup, false);
        return  new DialogSubsViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogSubsAdapter.DialogSubsViewHolder holder, final int position) {
        PlayerGame playerGame = mSubsList.get(position);
        try {
            holder.bindData(playerGame, position);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mSubsList.size();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void onItemHolderClick(DialogSubsViewHolder holder){
        if (onItemClickListener != null){
            onItemClickListener.onItemClick(null, holder.itemView,
                    holder.getAdapterPosition(), holder.getItemId());
        }
    }

    class DialogSubsViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener{

        private DialogSubsAdapter mAdapter;
        private RadioButton mRadioButton;
        private TextView mSubsName;
        private TextView mSubsNumber;


        public DialogSubsViewHolder(View itemView, final DialogSubsAdapter mAdapter){
            super(itemView);
            this.mAdapter = mAdapter;

            mSubsNumber = itemView.findViewById(R.id.subs_number_tv);
            mSubsName = itemView.findViewById(R.id.subs_name_tv);
            mRadioButton = itemView.findViewById(R.id.subs_radio_btn);

            itemView.setOnClickListener(this);
            mRadioButton.setOnClickListener(this);
        }

        public void bindData(PlayerGame playerGame, int position){
            mRadioButton.setChecked(position == selectedStartPosition);
            mSubsName.setText(playerGame.getNameAndLastname());
            mSubsNumber.setText(playerGame.getNumber());
        }

        @Override
        public void onClick(View v) {
            selectedStartPosition = getAdapterPosition();
            notifyItemRangeChanged(0, mSubsList.size());
            mAdapter.onItemHolderClick(DialogSubsViewHolder.this);
        }
    }
}
