package com.hfad.lzr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hfad.lzr.model.Team;

import java.util.ArrayList;
import java.util.List;

public class CreatingMatchActivity extends AppCompatActivity {

    Spinner spinner1;
    Spinner spinner2;
    Spinner leagueSpinner;
    DatabaseReference databaseReference;
    ValueEventListener listener;
    ArrayAdapter<String> adapterTeamA;
    ArrayAdapter<String> adapterTeamB;
    ArrayList<String> teamsSpinnerA;
    ArrayList<String> teamsSpinnerB;

    Button chooseLineup;
    ArrayAdapter<String> adapterList;
    ArrayList<String> leagues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_match);

        spinner1 = (Spinner) findViewById(R.id.choose_teamA);
        spinner2 = (Spinner) findViewById(R.id.choose_teamB);
        leagueSpinner = findViewById(R.id.choose_league);
        chooseLineup = findViewById(R.id.choose_lineup);
        chooseLineup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LineupActivity.class);
                intent.putExtra("teamA", spinner1.getSelectedItem().toString());
                intent.putExtra("teamB", spinner2.getSelectedItem().toString());
                startActivity(intent);
            }
        });
        leagues = new ArrayList<>();
        leagues.add("Liga A");
        leagues.add("Liga B");
        adapterList = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, leagues);
        adapterList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        databaseReference = FirebaseDatabase.getInstance().getReference("teams");

        teamsSpinnerA = new ArrayList<>();
        adapterTeamA = new ArrayAdapter<String>(CreatingMatchActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                teamsSpinnerA);

        teamsSpinnerB = new ArrayList<>();
        adapterTeamA = new ArrayAdapter<String>(CreatingMatchActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                teamsSpinnerB);

        spinner1.setAdapter(adapterTeamA);
        spinner2.setAdapter(adapterTeamA);

        leagueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                teamsSpinnerA.clear();
                teamsSpinnerB.clear();
                fetch(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void fetch(final String league) {
        listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot team : dataSnapshot.getChildren()) {
                    if (team.child("league").getValue().equals(league)) {
                        teamsSpinnerA.add(team.child("name").getValue().toString());
                        teamsSpinnerB.add(team.child("name").getValue().toString());
                    }
                }

                adapterTeamA.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addTeam(View view) {
        Intent intent = new Intent(this, CreateTeamActivity.class);
        intent.putExtra("prev_activity", "CreatingMatchActivity");
        startActivity(intent);
    }
}
