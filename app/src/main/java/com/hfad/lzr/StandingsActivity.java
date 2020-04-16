package com.hfad.lzr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hfad.lzr.adapter.PlayersGameAdapter;
import com.hfad.lzr.adapter.StandingsViewHolder;
import com.hfad.lzr.adapter.TeamAdapter;
import com.hfad.lzr.adapter.TeamViewHolder;
import com.hfad.lzr.model.Team;

import java.util.ArrayList;

public class StandingsActivity extends AppCompatActivity {

    RecyclerView standingsRV;
    FirebaseRecyclerOptions<Team> options;
    FirebaseRecyclerAdapter adapter;
    Spinner leagueSpinner;
    ArrayAdapter<String> adapterList;
    ArrayList<String> leagues;
    DatabaseReference databaseReference;
    ArrayList<Team> teams;
    TeamAdapter teamAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standings);

        standingsRV = findViewById(R.id.standingsRV);
        leagueSpinner = findViewById(R.id.choose_league);

        leagues = new ArrayList<>();
        leagues.add("Liga A");
        leagues.add("Liga B");
        adapterList = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, leagues);
        adapterList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        databaseReference = FirebaseDatabase.getInstance().getReference("teams");

        teams = new ArrayList<>();

        leagueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                fetch(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        fetch("Liga A");

    }

    private void fetch(String league){
        databaseReference.orderByChild("league").equalTo(league).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                teams.clear();
                for (DataSnapshot dsChild : dataSnapshot.getChildren()){
                    Team team = dsChild.getValue(Team.class);
                    teams.add(team);
                }

                standingsRV.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                teamAdapter = new TeamAdapter(teams);
                standingsRV.setLayoutManager(mLayoutManager);
                standingsRV.setAdapter(teamAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    /*private void fetch(String league){

        Query query = FirebaseDatabase.getInstance().getReference().child("teams").orderByChild("league").equalTo(league);

        options = new FirebaseRecyclerOptions.Builder<Team>().setQuery(query, Team.class).build();

        adapter = new FirebaseRecyclerAdapter<Team, StandingsViewHolder>(options) {
            @NonNull
            @Override
            public StandingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.standings_item, parent, false);
                return  new StandingsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull StandingsViewHolder holder, final int position, @NonNull final Team model) {
                holder.teamNameTV.setText(model.getName());
                holder.ptsPlusTV.setText(String.valueOf(model.getPointsScored()));
                holder.ptsMinusTV.setText(String.valueOf(model.getPointsReceived()));

                holder.teamNameTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(StandingsActivity.this, TeamActivity.class);
                        intent.putExtra("team_name", model.getName());
                        intent.putExtra("idTeam", model.getId());
                        startActivity(intent);
                    }
                });
            }
        };
        adapter.startListening();
        standingsRV.setAdapter(adapter);
    }*/

}
