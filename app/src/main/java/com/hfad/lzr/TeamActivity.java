package com.hfad.lzr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        teamNameTV = findViewById(R.id.teamNameTV);
        teamLeagueTV = findViewById(R.id.teamLeagueTV);


        final String teamName = getIntent().getStringExtra("team_name");

        databaseReference = FirebaseDatabase.getInstance().getReference("teams");

        databaseReference.orderByChild("name").equalTo(teamName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsChild : dataSnapshot.getChildren()){
                    Team team = dsChild.getValue(Team.class);

                    teamNameTV.setText(team.getName());
                    teamLeagueTV.setText(team.getLeague());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
}
