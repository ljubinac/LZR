package com.hfad.lzr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeamsActivity extends ListActivity {

    DatabaseReference databaseReference;
    ListView teamsList;
    ValueEventListener listener;
    ArrayAdapter<String> teamsAdapter;
    ArrayList<String> teams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_teams);

        teamsList = getListView();

        databaseReference = FirebaseDatabase.getInstance().getReference("teams");

        teams = new ArrayList<>();
        teamsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                teams);

        teamsList.setAdapter(teamsAdapter);

        getTeams();
    }

    public void getTeams(){
        listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot team : dataSnapshot.getChildren()){
                    teams.add(team.child("name").getValue().toString());

                }

                teamsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(TeamsActivity.this, TeamActivity.class);
        intent.putExtra("team_name", teams.get(position));
        startActivity(intent);
    }
}
