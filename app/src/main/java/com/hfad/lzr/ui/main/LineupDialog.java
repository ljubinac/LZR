package com.hfad.lzr.ui.main;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.hfad.lzr.adapter.PlayersGameAdapter;
import com.hfad.lzr.model.Game;
import com.hfad.lzr.model.PlayerGame;
import com.hfad.lzr.model.Team;

import java.util.ArrayList;
import java.util.Objects;

public class LineupDialog extends DialogFragment {

    private static final String TAG = "LineupDialog";
    private TextView mActionOk, mActionCancel;
    RecyclerView secondLineupRV;
    PlayersGameAdapter adapter;
    TextView playerNumberTV;
    TextView playerNameTV;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<PlayerGame> players;

    ArrayList<PlayerGame> izmene;

    boolean isChange = false;
    int goingOutPositionA;

   /* @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        players = ( ArrayList<PlayerGame> ) getArguments().getSerializable("playersGameA");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Izmene")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getDialog().dismiss();
            }
        });

        return builder.create();
    }*/

    public static LineupDialog newInstance(ArrayList<PlayerGame> players) {
        LineupDialog dialog = new LineupDialog();

        Bundle args = new Bundle();
        args.putSerializable("playersGameA", players);
        dialog.setArguments(args);

        return dialog;
    }

       @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        players = ( ArrayList<PlayerGame> ) getArguments().getSerializable("playersGameA");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_lineup, container, false);

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
        adapter = new PlayersGameAdapter(izmene, "teamA");
        secondLineupRV.setLayoutManager(layoutManager);
        secondLineupRV.setAdapter(adapter);



        adapter.onItemClickListener(new PlayersGameAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onLongClick(int position) {

            }
        });

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
            }
        });
        return  view;
    }
}
