package com.hfad.lzr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hfad.lzr.adapter.PlayerStatsAdapter;
import com.hfad.lzr.adapter.PlayersGameAdapter;
import com.hfad.lzr.adapter.TeamAdapter;
import com.hfad.lzr.model.Player;
import com.hfad.lzr.model.PlayerGame;
import com.hfad.lzr.model.Team;

import java.util.ArrayList;
import java.util.Collections;

public class PlayerStatsActivity extends AppCompatActivity {

    RecyclerView playerStatsRV;
    Spinner leagueSpinner;
    ArrayAdapter<String> adapterList;
    ArrayList<String> leagues;
    DatabaseReference databaseReference;
    ArrayList<Player> players;
    PlayerStatsAdapter playerStatsAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Toolbar toolbar;

    Spinner parameterSpinner;
    ArrayAdapter<String> adapterListOfParameters;
    ArrayList<String> parameters;

    String selectedLeague;
    String selectedParameter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_stats);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        playerStatsRV = findViewById(R.id.player_stats_rv);
        leagueSpinner = findViewById(R.id.choose_league);

        parameterSpinner = findViewById(R.id.choose_parameter);

        leagues = new ArrayList<>();
        leagues.add("Liga A");
        leagues.add("Liga B");
        adapterListOfParameters = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, leagues);
        adapterListOfParameters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        parameters = new ArrayList<>();
        parameters.add("PTS");
        parameters.add("AST");

        adapterList = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, leagues);
        adapterList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider));
        playerStatsRV.addItemDecoration(itemDecorator);
        playerStatsRV.setLayoutManager(new LinearLayoutManager(this));
        playerStatsRV.setHasFixedSize(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("players");

        players = new ArrayList<>();

        leagueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLeague = parent.getItemAtPosition(position).toString();
                fetch(selectedLeague, selectedParameter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        parameterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedParameter = parent.getItemAtPosition(position).toString();
                fetch(selectedLeague, selectedParameter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fetch("Liga A", "PTS");
    }

    private void fetch(String league, final String parameter) {
        databaseReference.orderByChild("league").equalTo(league).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                players.clear();
                for (DataSnapshot dsChild : dataSnapshot.getChildren()) {
                    Player player = dsChild.getValue(Player.class);
                    players.add(player);
                }

                Collections.sort(players);

                playerStatsRV.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                playerStatsAdapter = new PlayerStatsAdapter(players, parameter);
                playerStatsRV.setLayoutManager(mLayoutManager);
                playerStatsRV.setAdapter(playerStatsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
