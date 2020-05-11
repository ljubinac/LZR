package com.hfad.lzr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_league);

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
        } else if (TextUtils.isEmpty(leagueName)){
            saveLeague.setActivated(false);
            Toast.makeText(this, R.string.league_name_fail, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.league_not_added, Toast.LENGTH_LONG).show();
        }
    }
}
