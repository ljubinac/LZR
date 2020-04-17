package com.hfad.lzr.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.hfad.lzr.GameActivity;
import com.hfad.lzr.R;
import com.hfad.lzr.adapter.PlayersGameAdapter;
import com.hfad.lzr.model.PlayerGame;
import com.hfad.lzr.model.Team;

import java.util.ArrayList;

public class LineupDialog extends DialogFragment {

    private static final String TAG = "LineupDialog";

    private TextView mActionOk, mActionCancel;

    RecyclerView secondLineupArv, secondLineupBrv;

    ArrayList<PlayerGame> playersGameA, playersGameB;

    PlayersGameAdapter adapterA, adapterB;
    TextView playerNumberTV;
    TextView playerNameTV;

    RecyclerView.LayoutManager layoutManagerA, layoutManagerB;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        playersGameA = ( ArrayList<PlayerGame> ) getArguments().getSerializable("playersGameA");
        playersGameB = ( ArrayList<PlayerGame> ) getArguments().getSerializable("playersGameB");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_lineup, container, false);

        secondLineupArv = view.findViewById(R.id.secondLineupArv);
        secondLineupBrv = view.findViewById(R.id.secondLineupBrv);
        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionOk = view.findViewById(R.id.action_ok);
        playerNameTV = view.findViewById(R.id.player_name_tv);
        playerNumberTV = view.findViewById(R.id.player_number_tv);

        secondLineupArv.setHasFixedSize(true);
        layoutManagerA = new LinearLayoutManager(getContext());
        adapterA = new PlayersGameAdapter(playersGameA);

        secondLineupArv.setLayoutManager(layoutManagerA);
        secondLineupArv.setAdapter(adapterA);

        secondLineupBrv.setHasFixedSize(true);
        layoutManagerB = new LinearLayoutManager(getContext());
        adapterB = new PlayersGameAdapter(playersGameB);

        secondLineupBrv.setLayoutManager(layoutManagerB);
        secondLineupBrv.setAdapter(adapterB);



 /*       adapter.onItemClickListener(new PlayersGameAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onLongClick(int position) {

            }
        });*/

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog");
                getDialog().dismiss();
            }
        });

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: capturing input");

                ((GameActivity)getActivity()).buildRecyclerViewA();
            }
        });
        return  view;
    }

}
