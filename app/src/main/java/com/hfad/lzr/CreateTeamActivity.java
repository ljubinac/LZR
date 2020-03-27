package com.hfad.lzr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hfad.lzr.model.Player;
import com.hfad.lzr.model.Team;

import java.util.ArrayList;
import java.util.List;

public class CreateTeamActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<Player> players = new ArrayList<>();

    Button saveTeam;
    EditText teamName;
    DatabaseReference databaseReferencePlayers;
    DatabaseReference databaseReferenceTeams;
    Spinner leagueName;

    TextView playerNumber;
    TextView playerName;
    Button addPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);

       /* ArrayList<Player> playersList = new ArrayList<>();
        playersList.add(new Player("Mihailo Ljubinac", "10"));
        playersList.add(new Player("Milos Nisic", "12"));
        playersList.add(new Player("Andreja Micovic", "33"));*/

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Adapter(players);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        saveTeam = findViewById(R.id.save_team);
        teamName = findViewById(R.id.team_name);
        leagueName = findViewById(R.id.choose_league);
        databaseReferenceTeams = FirebaseDatabase.getInstance().getReference("teams");

        addPlayer = findViewById(R.id.player_add_btn);
        playerNumber = findViewById(R.id.player_number_edt);
        playerName = findViewById(R.id.player_name_edt);
        databaseReferencePlayers = FirebaseDatabase.getInstance().getReference("players");

        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addPlayer();

                mRecyclerView = findViewById(R.id.recyclerView);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                mAdapter = new Adapter(players);

                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
           }
        });


        saveTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addTeam();
            }
        });


    }

    private void addTeam() {
        String name = teamName.getText().toString();
        String league = String.valueOf(leagueName.getSelectedItem());
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(league)) {
            String id = databaseReferenceTeams.push().getKey();
            Team team = new Team(id, name, league);
            databaseReferenceTeams.child(id).setValue(team);

            for (Player p : players){
                String idPlayer = databaseReferencePlayers.push().getKey();
                p.setId(idPlayer);
                p.setTeamId(id);

                databaseReferencePlayers.child(idPlayer).setValue(p);
            }
            Toast.makeText(this, "Team added!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Team not added!", Toast.LENGTH_LONG).show();
        }
    }

    private void addPlayer(){
        String number = playerNumber.getText().toString();
        String name = playerName.getText().toString();
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)){

            Player player = new Player(name, number);

            players.add(player);

            Toast.makeText(this, "Player added!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Player not added!", Toast.LENGTH_LONG).show();
        }
    }

}
