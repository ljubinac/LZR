package com.hfad.lzr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.hfad.lzr.adapter.PlayersAdapter;
import com.hfad.lzr.adapter.PlayersGameAdapter;
import com.hfad.lzr.model.Player;
import com.hfad.lzr.model.PlayerGame;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private RecyclerView teamARV;
    private RecyclerView teamBRV;
    private PlayersGameAdapter teamAAdapter;
    private PlayersGameAdapter teamBAdapter;
    private RecyclerView.LayoutManager layoutManagerA;
    private RecyclerView.LayoutManager layoutManagerB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ArrayList<PlayerGame> playersTeamA = (ArrayList<PlayerGame>) getIntent().getSerializableExtra("playersTeamA");
        ArrayList<PlayerGame> playersTeamB = (ArrayList<PlayerGame>) getIntent().getSerializableExtra("playersTeamB");

        teamARV = findViewById(R.id.firstTeamRV);
        teamBRV = findViewById(R.id.secondTeamRV);
        teamARV.setHasFixedSize(true);
        teamBRV.setHasFixedSize(true);
        layoutManagerA = new LinearLayoutManager(getApplicationContext());
        layoutManagerB = new LinearLayoutManager(getApplicationContext());
        teamAAdapter = new PlayersGameAdapter(playersTeamA);
        teamBAdapter = new PlayersGameAdapter(playersTeamB);

        teamARV.setLayoutManager(layoutManagerA);
        teamBRV.setLayoutManager(layoutManagerB);
        teamARV.setAdapter(teamAAdapter);
        teamBRV.setAdapter(teamBAdapter);


    }
}
