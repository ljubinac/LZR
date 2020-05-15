package com.hfad.lzr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.hfad.lzr.R;
import com.hfad.lzr.adapter.LeagueViewHolder;
import com.hfad.lzr.adapter.LineupViewHolder;
import com.hfad.lzr.model.League;

public class CreateLeagueActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    EditText leagueNameET;
    Button saveLeague;
    String leagueId;
    String leagueName;
    League league;
    Toolbar toolbar;

    RecyclerView recyclerViewLeague;
    FirebaseRecyclerOptions<League> options;
    FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_league);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_create_league);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        leagueNameET = findViewById(R.id.league_name_et);
        saveLeague = findViewById(R.id.save_league_btn);

        databaseReference = FirebaseDatabase.getInstance().getReference("leagues");

        saveLeague.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLeague();
            }
        });

        recyclerViewLeague = findViewById(R.id.league_recycler_view);
        recyclerViewLeague.setHasFixedSize(true);
        recyclerViewLeague.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider));
        recyclerViewLeague.addItemDecoration(itemDecorator);
        recyclerViewLeague.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewLeague.setHasFixedSize(true);

        Query query = databaseReference;
        options = new FirebaseRecyclerOptions.Builder<League>().setQuery(query, League.class).build();
        adapter = new FirebaseRecyclerAdapter<League, LeagueViewHolder>(options) {


            @NonNull
            @Override
            public LeagueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.league_item, parent, false);
                return new LeagueViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull LeagueViewHolder holder, int position, @NonNull League model) {
                holder.leagueNameTV.setText(model.getName());

                holder.deleteLeagueImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CreateLeagueActivity.this);

                        builder.setTitle("Confirm");
                        builder.setMessage("Are you sure?");

                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog
                                adapter.getRef(position).removeValue();
                                dialog.dismiss();
                            }
                        });

                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();

                    }
                });
            }
        };
        adapter.startListening();
        recyclerViewLeague.setAdapter(adapter);
    }

    public void addLeague(){
        leagueName = leagueNameET.getText().toString();
        if(!TextUtils.isEmpty(leagueName)){
            leagueId = databaseReference.push().getKey();
            league = new League(leagueId, leagueName);
            databaseReference.child(leagueId).setValue(league);
            Toast.makeText(this, R.string.league_added, Toast.LENGTH_LONG).show();
            leagueNameET.setText("");
        } else if (TextUtils.isEmpty(leagueName)){
            saveLeague.setActivated(false);
            Toast.makeText(this, R.string.league_name_fail, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.league_not_added, Toast.LENGTH_LONG).show();
        }
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
