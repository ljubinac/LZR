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
    RecyclerView secondLineupRV;
    DialogSubsAdapter adapter;
    TextView playerNumberTV;
    TextView playerNameTV;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<PlayerGame> players;

    ArrayList<PlayerGame> izmene;
    PlayerGame playerGame, playerGameOut;

    DialogListener dialogListener;

    String team;

    TextView a, b;


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

        a = view.findViewById(R.id.player_in_name_tv);
        b = view.findViewById(R.id.player_out_name_tv);

        secondLineupRV = view.findViewById(R.id.secondLineupArv);
        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionOk = view.findViewById(R.id.action_ok);
        playerNameTV = view.findViewById(R.id.player_name_tv);
        playerNumberTV = view.findViewById(R.id.player_number_tv);

        izmene = new ArrayList<>();
        for (int i = 5; i < players.size(); i++){
            PlayerGame izmena = players.get(i);
            izmene.add(izmena);
        }

        secondLineupRV.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new DialogSubsAdapter(getContext(), izmene);
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
                dialogListener.doOkClick(playerGame, team);
                getDialog().dismiss();
            }
        });
        return  view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        playerGame = izmene.get(position);
        a.setText(playerGame.getNameAndLastname());
        b.setText(playerGameOut.getNameAndLastname());
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
