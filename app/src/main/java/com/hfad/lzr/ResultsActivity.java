package com.hfad.lzr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hfad.lzr.adapter.GameAdapter;
import com.hfad.lzr.adapter.ResultsViewHolder;
import com.hfad.lzr.adapter.TeamAdapter;
import com.hfad.lzr.model.Game;
import com.hfad.lzr.model.League;
import com.hfad.lzr.model.Team;

import java.util.ArrayList;
import java.util.Collections;

public class ResultsActivity extends AppCompatActivity {

    Game game;
    RecyclerView resultsRV;
    FirebaseRecyclerOptions<Game> options;
    GameAdapter adapter;
    DatabaseReference databaseReferenceGames, databaseReferenceLeagues;
    Toolbar toolbar;

    Spinner spinnerLeague;
    ValueEventListener listener;
    ArrayAdapter<String> adapterLeague;
    ArrayList<String> leagueSpinnerList;
    ArrayList<League> leagues;

    ArrayList<Game> games = new ArrayList<>();

    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        leagues = new ArrayList<>();
        spinnerLeague = findViewById(R.id.choose_league);
        databaseReferenceLeagues = FirebaseDatabase.getInstance().getReference("leagues");
        leagueSpinnerList = new ArrayList<>();
        leagueSpinnerList.add(0, getString(R.string.leagues_title));
        leagueSpinnerList.add(getString(R.string.exhibition));
        adapterLeague = new ArrayAdapter<String>(this, R.layout.spinner_item2, leagueSpinnerList);
        adapterLeague.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLeague.setAdapter(adapterLeague);

        game = ( Game ) getIntent().getSerializableExtra("game");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.results);

        resultsRV = findViewById(R.id.results_rv);
        /*resultsRV.setHasFixedSize(true);
        resultsRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));*/

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider));
        resultsRV.addItemDecoration(itemDecorator);
        resultsRV.setLayoutManager(new LinearLayoutManager(this));
        resultsRV.setHasFixedSize(true);

        databaseReferenceGames = FirebaseDatabase.getInstance().getReference("games");

       /* Query query = databaseReferenceGames;
        options = new FirebaseRecyclerOptions.Builder<Game>().setQuery(query, Game.class).build();
        adapter = new FirebaseRecyclerAdapter<Game, ResultsViewHolder>(options) {
            @NonNull
            @Override
            public ResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item, parent, false);
                return new ResultsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ResultsViewHolder holder, int position, @NonNull Game model) {
                holder.resA.setText(String.valueOf(model.getResA()));
                holder.resB.setText(String.valueOf(model.getResB()));
                holder.teamA.setText(model.getTeamAnaziv());
                holder.teamB.setText(model.getTeamBnaziv());
            }
        };
        adapter.startListening();
        resultsRV.setAdapter(adapter);*/

        spinnerLeague.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                fetch(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        fetchLeagues();
    }

    private void fetchLeagues() {
        listener = databaseReferenceLeagues.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot league : dataSnapshot.getChildren()) {
                    leagueSpinnerList.add(league.child("name").getValue().toString());
                    leagues.add(league.getValue(League.class));
                }

                adapterLeague.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void fetch(String league) {
        databaseReferenceGames.orderByChild("league").equalTo(league).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                games.clear();
                for (DataSnapshot dsChild : dataSnapshot.getChildren()) {
                    Game game = dsChild.getValue(Game.class);
                    games.add(game);
                }

                /*Collections.sort(teams);*/

                resultsRV.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                adapter = new GameAdapter(games);
                resultsRV.setLayoutManager(mLayoutManager);
                resultsRV.setAdapter(adapter);
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


