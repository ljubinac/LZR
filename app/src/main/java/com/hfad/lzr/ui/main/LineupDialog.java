package com.hfad.lzr.ui.main;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.hfad.lzr.GameActivity;
import com.hfad.lzr.R;
import com.hfad.lzr.adapter.DialogSubsAdapter;
import com.hfad.lzr.adapter.PlayersGameAdapter;
import com.hfad.lzr.model.Game;
import com.hfad.lzr.model.Player;
import com.hfad.lzr.model.PlayerGame;
import com.hfad.lzr.model.Team;

import java.util.ArrayList;
import java.util.Objects;

public class LineupDialog extends DialogFragment implements AdapterView.OnItemClickListener {

    private static final String TAG = "LineupDialog";
    private TextView mActionOk, mActionCancel;
    private RecyclerView secondLineupRV;
    private DialogSubsAdapter adapter;
    private TextView playerNumberTV;
    private TextView playerNameTV;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<PlayerGame> players;

    private ArrayList<PlayerGame> substitutions;
    private PlayerGame playerGame, playerGameOut;

    private DialogListener dialogListener;

    private String team;

    private TextView playerOutNameTv, playerOutNumberTV;


    public static LineupDialog newInstance(ArrayList<PlayerGame> players, PlayerGame playerGameOut, String team) {
        LineupDialog dialog = new LineupDialog();

        Bundle args = new Bundle();
        args.putSerializable("playersList", players);
        args.putSerializable("playerGameOut", playerGameOut);
        args.putString("team", team);
        dialog.setArguments(args);

        return dialog;
    }

       @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        players = ( ArrayList<PlayerGame> ) getArguments().getSerializable("playersList");
        playerGameOut = ( PlayerGame ) getArguments().getSerializable("playerGameOut");
        team = getArguments().getString("team");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_lineup, container, false);


        playerOutNameTv = view.findViewById(R.id.player_out_name_tv);
        playerOutNumberTV = view.findViewById(R.id.player_out_number_tv);

        playerOutNumberTV.setText(playerGameOut.getNumber());
        playerOutNameTv.setText(playerGameOut.getNameAndLastname());

        secondLineupRV = view.findViewById(R.id.secondLineupArv);
        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionOk = view.findViewById(R.id.action_ok);
        playerNameTV = view.findViewById(R.id.player_name_tv);
        playerNumberTV = view.findViewById(R.id.player_number_tv);

        substitutions = new ArrayList<>();
        for (int i = 5; i < players.size(); i++){
            PlayerGame substitution = players.get(i);
            substitutions.add(substitution);
        }

        secondLineupRV.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new DialogSubsAdapter(getContext(), substitutions);
        secondLineupRV.setLayoutManager(layoutManager);
        secondLineupRV.setAdapter(adapter);

        adapter.setOnItemClickListener(this);

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
                if(playerGame != null) {
                    dialogListener.doOkClick(playerGame, team);
                    getDialog().dismiss();
                } else {
                    Toast.makeText(getContext(), R.string.msg_for_non_selected_lineup_dialog, Toast.LENGTH_LONG).show();
                }
            }
        });
        return  view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        playerGame = substitutions.get(position);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            dialogListener = ( DialogListener ) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString() + " must implement dialog listener.");
        }
    }

    public interface DialogListener{
        void doOkClick(PlayerGame playerGame, String team);
    }

}
