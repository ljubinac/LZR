package com.hfad.lzr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hfad.lzr.model.Team;

public class TeamActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    TextView teamNameTV;
    TextView teamLeagueTV;
    LinearLayout teamNameLL1;
    LinearLayout teamNameLL2;
    EditText teamNameET;
    ImageView acceptTeamName;
    ImageView editTeamName;
    Adapter adapter;
    String teamName;
    String idTeam;

    Spinner leagueSpinner;
    ImageView acceptLeague;

    LinearLayout leagueLL1;
    LinearLayout leagueLL2;
    ImageView editLeague;

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



        databaseReference = FirebaseDatabase.getInstance().getReference("teams");

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
}
