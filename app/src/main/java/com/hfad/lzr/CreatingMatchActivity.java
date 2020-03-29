package com.hfad.lzr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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
    DatabaseReference databaseReference;
    ValueEventListener listener;
    ArrayAdapter<String> adapterTeamA;
    ArrayAdapter<String> adapterTeamB;
    ArrayList<String> teamsSpinnerA;
    ArrayList<String> teamsSpinnerB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_match);

        spinner1 = (Spinner)findViewById(R.id.choose_teamA);
        spinner2 = (Spinner)findViewById(R.id.choose_teamB);

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
        
        retrieveData();

    }

    public void retrieveData(){
        listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot team : dataSnapshot.getChildren()){
                    teamsSpinnerA.add(team.child("name").getValue().toString());
                    teamsSpinnerB.add(team.child("name").getValue().toString());

                }

                adapterTeamA.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addTeam(View view){
        Intent intent = new Intent(this, CreateTeamActivity.class);
        intent.putExtra("prev_activity", "CreatingMatchActivity");
        startActivity(intent);
    }
}
