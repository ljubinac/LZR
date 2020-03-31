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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

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

    DatabaseReference databaseReference;
    RecyclerView teamsRV;
    FirebaseRecyclerOptions<Team> options;
    FirebaseRecyclerAdapter adapter;
    Spinner spinner;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);


        teamsRV = findViewById(R.id.teamsRV);

        teamsRV.setLayoutManager(new LinearLayoutManager(this));
        teamsRV.setHasFixedSize(true);


        Query query = FirebaseDatabase.getInstance().getReference().child("teams").orderByChild("league").equalTo("Liga B");

        options = new FirebaseRecyclerOptions.Builder<Team>().setQuery(query, Team.class).build();

        adapter = new FirebaseRecyclerAdapter<Team, TeamViewHolder>(options) {
            @NonNull
            @Override
            public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_item, parent, false);
                return  new TeamViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull TeamViewHolder holder, int position, @NonNull Team model) {
                holder.teamName.setText(model.getName());

            }
        };


        teamsRV.setAdapter(adapter);






    }

}
