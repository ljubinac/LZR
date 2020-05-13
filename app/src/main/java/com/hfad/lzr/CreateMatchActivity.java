package com.hfad.lzr;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

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
import com.hfad.lzr.model.League;
import com.hfad.lzr.model.Team;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateMatchActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Spinner spinner1, spinner2, leagueSpinner;
    DatabaseReference databaseReference, databaseReferenceLeagues;
    ValueEventListener listener;
    ArrayAdapter<String> adapterTeamA, adapterList;
    ArrayList<String> teamsSpinnerA, teamsSpinnerB, leaguesSpinnerList;
    ArrayList<Team> teams;
    ArrayList<League> leagues;
    Button saveGame;
    Toolbar toolbar;
    DatabaseReference databaseReferenceGames;
    Button datePickerBtn;
    Button timePickerBtn;
    String date;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_match);

        datePickerBtn = findViewById(R.id.pick_date_btn);
        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        timePickerBtn = findViewById(R.id.pick_time_btn);
        timePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });

        databaseReferenceGames = FirebaseDatabase.getInstance().getReference("games");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create game");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        teams = new ArrayList<>();
        leagues = new ArrayList<>();
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
                Game game = new Game(id, teamA.getId(), teamB.getId(), date, time, teamA.getName(), teamB.getName(), false);
                databaseReferenceGames.child(id).setValue(game);
                intent.putExtra("activity", "CreatingMatchActivity");
                startActivity(intent);
            }
        });

        databaseReferenceLeagues = FirebaseDatabase.getInstance().getReference("leagues");
        leaguesSpinnerList = new ArrayList<>();
        adapterList = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, leaguesSpinnerList);
        adapterList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        databaseReference = FirebaseDatabase.getInstance().getReference("teams");

        teamsSpinnerA = new ArrayList<>();
        adapterTeamA = new ArrayAdapter<String>(CreateMatchActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                teamsSpinnerA);

        teamsSpinnerB = new ArrayList<>();
        adapterTeamA = new ArrayAdapter<String>(CreateMatchActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                teamsSpinnerB);

        leagueSpinner.setAdapter(adapterList);
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


    private void fetch(final String league) {
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


    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        date = day + "/" + month + "/" + year;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int min) {
        time = hour + ":" + min;
    }
}