package com.hfad.lzr;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hfad.lzr.model.Game;
import com.hfad.lzr.model.Team;

import java.util.ArrayList;

public class CreatingMatchActivity extends AppCompatActivity {

    Spinner spinner1, spinner2, leagueSpinner;
    DatabaseReference databaseReference;
    ValueEventListener listener;
    ArrayAdapter<String> adapterTeamA, adapterList;
    ArrayList<String> teamsSpinnerA, teamsSpinnerB, leagues;
    ArrayList<Team> teams;
    Button saveGame;
    Toolbar toolbar;
    DatabaseReference databaseReferenceGames;
    Button datePickerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_match);

//        datePickerBtn = findViewById(R.id.pick_date_btn);


        databaseReferenceGames = FirebaseDatabase.getInstance().getReference("games");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        teams = new ArrayList<>();
        spinner1 = findViewById(R.id.choose_teamA);
        spinner2 = findViewById(R.id.choose_teamB);
        leagueSpinner = findViewById(R.id.choose_league);
        saveGame = findViewById(R.id.choose_lineup);
        saveGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                String id = databaseReferenceGames.push().getKey();
                Team teamA = teams.get(spinner1.getSelectedItemPosition());
                Team teamB = teams.get(spinner2.getSelectedItemPosition());
                Game game = new Game(id, teamA.getId(), teamB.getId(), "Date", "Time", teamA.getName(), teamB.getName(), false);
                databaseReferenceGames.child(id).setValue(game);
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

// nesto ne radi, evo greske:
      //  com.google.android.material.datepicker.MaterialDatePicker requires a value for the com.hfad.lzr:attr/materialCalendarTheme attribute to be set in your app theme. You can either set the attribute in your theme or update your theme to inherit from Theme.MaterialComponents (or a descendant).

 /*       MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a date");
        final MaterialDatePicker materialDatePicker = builder.build();

        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });*/
    }

    public void fetch(final String league) {
        listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot team : dataSnapshot.getChildren()) {
                    if (team.child("league").getValue().equals(league)) {
                        teamsSpinnerA.add(team.child("name").getValue().toString());
                        teamsSpinnerB.add(team.child("name").getValue().toString());
                        teams.add(team.getValue(Team.class));
                    }
                }

                adapterTeamA.notifyDataSetChanged();
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