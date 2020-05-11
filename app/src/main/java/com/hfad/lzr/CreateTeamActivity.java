package com.hfad.lzr;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hfad.lzr.adapter.PlayersAdapter;
import com.hfad.lzr.model.League;
import com.hfad.lzr.model.Player;
import com.hfad.lzr.model.Team;

import java.util.ArrayList;

public class CreateTeamActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PlayersAdapter mPlayersAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Player> players = new ArrayList<>();

    private Button saveTeam;
    private EditText teamName;
    private DatabaseReference databaseReferencePlayers, databaseReferenceTeams, databaseReferenceLeagues;
    Spinner leagueSpinner;
    ArrayAdapter<String> adapterList;
    ArrayList<String> leaguesSpinnerList;
    ArrayList<League> leagues;
    ValueEventListener listener;

    private TextView playerNumber, playerName;
    private ImageView addPlayer;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        saveTeam = findViewById(R.id.save_team);
        teamName = findViewById(R.id.team_name);
        leagueSpinner = findViewById(R.id.choose_league);
        databaseReferenceTeams = FirebaseDatabase.getInstance().getReference("teams");

        addPlayer = findViewById(R.id.add_image);
        playerNumber = findViewById(R.id.player_number_edt);
        playerName = findViewById(R.id.player_name_edt);
        databaseReferencePlayers = FirebaseDatabase.getInstance().getReference("players");

        databaseReferenceLeagues = FirebaseDatabase.getInstance().getReference("leagues");

        leagues = new ArrayList<>();

        leaguesSpinnerList = new ArrayList<>();
        adapterList = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, leaguesSpinnerList);

        leagueSpinner.setAdapter(adapterList);

        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addPlayer();
                buildRecyclerView();
            }
        });

        saveTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addTeam();
                if(getIntent().getStringExtra("prev_activity") == null){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else if(getIntent().getStringExtra("prev_activity").equals("CreatingMatchActivity")){
                    Intent intent = new Intent(getApplicationContext(), CreateMatchActivity.class);
                    startActivity(intent);
                }
            }
        });

        fetchLeagues();
    }

    private void fetchLeagues() {
        listener = databaseReferenceLeagues.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot league : dataSnapshot.getChildren()) {
                    leaguesSpinnerList.add(league.child("name").getValue().toString());
                    leagues.add(league.getValue(League.class));
                }

                adapterList.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mPlayersAdapter = new PlayersAdapter(players);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mPlayersAdapter);

        mPlayersAdapter.setOnItemClickListener(new PlayersAdapter.OnItemClickListener() {

            @Override
            public void onAcceptClick(int position, String name, String number) {
                editPlayer(position, name, number);
            }

            @Override
            public void onDeleteClick(int position) {
                removePlayer(position);
            }
        });
    }

    public void editPlayer(int position, String name, String number){
        players.get(position).setNameAndLastname(name);
        players.get(position).setNumber(number);
        mPlayersAdapter.notifyItemChanged(position);
    }

    public void removePlayer(int position) {
        players.remove(position);
        mPlayersAdapter.notifyItemRemoved(position);
    }

    private void addTeam() {
        String name = teamName.getText().toString();
        String league = String.valueOf(leagueSpinner.getSelectedItem());
        if (!TextUtils.isEmpty(name)) {
            String id = databaseReferenceTeams.push().getKey();
            Team team = new Team(id, name, league);
            databaseReferenceTeams.child(id).setValue(team);

            for (Player p : players) {
                String idPlayer = databaseReferencePlayers.push().getKey();
                p.setId(idPlayer);
                p.setTeamId(id);

                databaseReferencePlayers.child(idPlayer).setValue(p);
            }
            Toast.makeText(this, R.string.team_added, Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(name)) {
            saveTeam.setActivated(false);
            Toast.makeText(this, R.string.add_team_name, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.team_not_added, Toast.LENGTH_LONG).show();
        }
    }

    private void addPlayer() {
        String number = playerNumber.getText().toString();
        String name = playerName.getText().toString();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)) {
            Player player = new Player(name, number);
            players.add(player);
            playerName.setText("");
            playerNumber.setText("");
            Toast.makeText(this, R.string.player_added, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.player_not_added, Toast.LENGTH_LONG).show();
        }
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
