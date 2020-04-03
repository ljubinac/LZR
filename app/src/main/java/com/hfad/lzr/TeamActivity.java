package com.hfad.lzr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hfad.lzr.adapter.PlayerViewHolder;
import com.hfad.lzr.adapter.PlayersAdapter;
import com.hfad.lzr.model.Player;
import com.hfad.lzr.model.Team;

import java.util.ArrayList;

public class TeamActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    TextView teamNameTV;
    TextView teamLeagueTV;
    LinearLayout teamNameLL1;
    LinearLayout teamNameLL2;
    EditText teamNameET;
    ImageView acceptTeamName;
    ImageView editTeamName;
    String teamName;
    String idTeam;

    Spinner leagueSpinner;
    ImageView acceptLeague;
    LinearLayout leagueLL1;
    LinearLayout leagueLL2;
    ImageView editLeague;

    DatabaseReference databaseReferencePlayers;
    RecyclerView playersRV;
    FirebaseRecyclerOptions<Player> options;
    FirebaseRecyclerAdapter adapter;
    EditText playerNumberET;
    EditText playerNameET;
    ImageView addPlayer;
    ImageView editPlayer;
    ImageView deletePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        teamNameTV = findViewById(R.id.teamNameTV);
        teamLeagueTV = findViewById(R.id.teamLeagueTV);
        teamNameET = findViewById(R.id.teamNameET);
        teamNameLL1 = findViewById(R.id.teamNameLL1);
        teamNameLL2 = findViewById(R.id.teamNameLL2);
        acceptTeamName = findViewById(R.id.acceptTeamName);
        editTeamName = findViewById(R.id.image_editTeamName);
        teamName = getIntent().getStringExtra("team_name");

        editLeague = findViewById(R.id.image_editLeague);
        leagueSpinner = findViewById(R.id.choose_league);
        acceptLeague = findViewById(R.id.image_acceptLeague);
        leagueLL1 = findViewById(R.id.leagueLL1);
        leagueLL2 = findViewById(R.id.leagueLL2);

        idTeam = getIntent().getStringExtra("idTeam");
        playerNumberET = findViewById(R.id.player_number_edt);
        playerNameET = findViewById(R.id.player_name_edt);
        addPlayer = findViewById(R.id.add_image);
        playersRV = findViewById(R.id.recyclerView);
        editPlayer = findViewById(R.id.image_edit);
        deletePlayer = findViewById(R.id.image_delete);

        databaseReference = FirebaseDatabase.getInstance().getReference("teams");

        databaseReferencePlayers = FirebaseDatabase.getInstance().getReference("players");

        databaseReference.orderByChild("name").equalTo(teamName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsChild : dataSnapshot.getChildren()){
                    Team team = dsChild.getValue(Team.class);
                    idTeam = dsChild.getKey();
                    teamNameTV.setText(team.getName());
                    teamLeagueTV.setText(team.getLeague());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlayer();
                playersRV = findViewById(R.id.recyclerView);
                playersRV.setHasFixedSize(true);
                playersRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                fetch(idTeam);
            }
        });

        playersRV.setLayoutManager(new LinearLayoutManager(this));
        playersRV.setHasFixedSize(true);
        fetch(idTeam);

        editTeamName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTeamName();
            }
        });

        acceptTeamName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTeamName();
            }
        });

        editLeague.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLeague();
            }
        });

        acceptLeague.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptChanges();
            }
        });
    }

    private void addPlayer(){
        String number = playerNumberET.getText().toString();
        String name = playerNameET.getText().toString();
        if(!TextUtils.isEmpty(number) || !TextUtils.isEmpty(name)){
            String id = databaseReferencePlayers.push().getKey();
            Player player = new Player(id, name, number, idTeam);
            player.setId(id);
            player.setTeamId(idTeam);
            databaseReferencePlayers.child(id).setValue(player);
            playerNumberET.setText("");
            playerNameET.setText("");

            Toast.makeText(this, "Player added!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Player not added!", Toast.LENGTH_LONG).show();
        }
    }

    private void fetch(final String idTeam) {

        final Query query = FirebaseDatabase.getInstance().getReference("players").orderByChild("teamId").equalTo(idTeam);

        options = new FirebaseRecyclerOptions.Builder<Player>().setQuery(query, Player.class).build();

        adapter = new FirebaseRecyclerAdapter<Player, PlayerViewHolder>(options) {

            @NonNull
            @Override
            public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false);
                return new PlayerViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final PlayerViewHolder holder, final int position, @NonNull final Player model) {
                holder.playerNumberTV.setText(model.getNumber());
                holder.playerNameTV.setText(model.getNameAndLastname());

                holder.playerDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.getRef(position).removeValue();
                    }
                });
                holder.playerEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.playerNumberET.setText(holder.playerNumberTV.getText().toString());
                        holder.playerNameET.setText(holder.playerNameTV.getText().toString());
                        holder.playerLL1.setVisibility(View.INVISIBLE);
                        holder.playerLL2.setVisibility(View.VISIBLE);
                    }
                });

                holder.playerAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        holder.playerNumberTV.setText(holder.playerNumberET.getText().toString());
                        holder.playerNameTV.setText(holder.playerNameET.getText().toString());
                        holder.playerLL1.setVisibility(View.VISIBLE);
                        holder.playerLL2.setVisibility(View.INVISIBLE);

                        databaseReferencePlayers.child(model.getId()).child("number").setValue(holder.playerNumberTV.getText().toString());
                        databaseReferencePlayers.child(model.getId()).child("nameAndLastname").setValue(holder.playerNameTV.getText().toString());



                    }
                });;
            }
        };
        adapter.startListening();
        playersRV.setAdapter(adapter);
    }

    public void editLeague(){
        leagueLL1.setVisibility(View.INVISIBLE);
        leagueLL2.setVisibility(View.VISIBLE);
    }

    public void acceptChanges(){
        teamLeagueTV.setText(leagueSpinner.getSelectedItem().toString());
        leagueLL1.setVisibility(View.VISIBLE);
        leagueLL2.setVisibility(View.INVISIBLE);

        databaseReference.child(idTeam).child("league").setValue(teamLeagueTV.getText().toString());
    }

    public void editTeamName(){
        teamNameET.setText(teamNameTV.getText().toString());
        teamNameLL1.setVisibility(View.INVISIBLE);
        teamNameLL2.setVisibility(View.VISIBLE);
    }

    public void saveTeamName(){

        teamNameTV.setText(teamNameET.getText().toString());
        teamNameLL1.setVisibility(View.VISIBLE);
        teamNameLL2.setVisibility(View.INVISIBLE);

        databaseReference.child(idTeam).child("name").setValue(teamNameTV.getText().toString());

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
