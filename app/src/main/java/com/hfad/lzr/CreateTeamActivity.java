package com.hfad.lzr;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hfad.lzr.adapter.PlayersAdapter;
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
    private DatabaseReference databaseReferencePlayers;
    private DatabaseReference databaseReferenceTeams;
    private Spinner leagueName;

    private TextView playerNumber;
    private TextView playerName;
    private ImageView addPlayer;
    //private ImageView deletePlayer;
    //private  ImageView editPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);


        saveTeam = findViewById(R.id.save_team);
        teamName = findViewById(R.id.team_name);
        leagueName = findViewById(R.id.choose_league);
        databaseReferenceTeams = FirebaseDatabase.getInstance().getReference("teams");

        addPlayer = findViewById(R.id.add_image);
        playerNumber = findViewById(R.id.player_number_edt);
        playerName = findViewById(R.id.player_name_edt);
        // deletePlayer = findViewById(R.id.image_delete);
        //editPlayer = findViewById(R.id.image_edit);
        databaseReferencePlayers = FirebaseDatabase.getInstance().getReference("players");





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
                    Intent intent = new Intent(getApplicationContext(), CreatingMatchActivity.class);
                    startActivity(intent);
                }
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

   /* public void editPlayer(int position, EditText newNumber, EditText newName){
        players.get(position).changeText1(newNumber, newName);
        mAdapter.notifyItemChanged(position);
    }*/

    private void addTeam() {
        String name = teamName.getText().toString();
        String league = String.valueOf(leagueName.getSelectedItem());
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
            Toast.makeText(this, "Team added!", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(name)) {
            saveTeam.setActivated(false);
            Toast.makeText(this, "Add team name! ", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Team not added!", Toast.LENGTH_LONG).show();
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
            Toast.makeText(this, "Player added!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Player not added!", Toast.LENGTH_LONG).show();
        }
    }

}
