package com.hfad.lzr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hfad.lzr.adapter.TeamViewHolder;
import com.hfad.lzr.model.Team;

import java.util.ArrayList;

public class TeamsActivity extends AppCompatActivity {

    RecyclerView teamsRV;
    FirebaseRecyclerOptions<Team> options;
    FirebaseRecyclerAdapter adapter;
    Spinner leagueSpinner;
    ArrayAdapter<String> adapterList;
    ArrayList<String> leagues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        teamsRV = findViewById(R.id.teamsRV);
        leagueSpinner = findViewById(R.id.choose_league);

        leagues = new ArrayList<>();
        leagues.add("Liga A");
        leagues.add("Liga B");
        adapterList = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, leagues);
        adapterList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        teamsRV.setLayoutManager(new LinearLayoutManager(this));
        teamsRV.setHasFixedSize(true);


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

        Query query = FirebaseDatabase.getInstance().getReference().child("teams").orderByChild("league").equalTo(league);

        options = new FirebaseRecyclerOptions.Builder<Team>().setQuery(query, Team.class).build();

        adapter = new FirebaseRecyclerAdapter<Team, TeamViewHolder>(options) {
            @NonNull
            @Override
            public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_item, parent, false);
                return  new TeamViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull TeamViewHolder holder, int position, @NonNull final Team model) {
                holder.teamName.setText(model.getName());

                holder.teamName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TeamsActivity.this, TeamActivity.class);
                        intent.putExtra("team_name", model.getName());
                        startActivity(intent);
                    }
                });
            }
        };
        adapter.startListening();
        teamsRV.setAdapter(adapter);
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
