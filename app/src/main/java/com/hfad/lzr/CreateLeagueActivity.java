package com.hfad.lzr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hfad.lzr.R;
import com.hfad.lzr.model.League;

public class CreateLeagueActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    EditText leagueNameET;
    Button saveLeague;
    String leagueId;
    String leagueName;
    League league;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_league);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create league");
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
