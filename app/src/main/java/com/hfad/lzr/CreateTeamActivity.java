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
import com.hfad.lzr.model.Team;

public class CreateTeamActivity extends AppCompatActivity {

    Button saveTeam;
    EditText teamName;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);

        saveTeam = findViewById(R.id.save_team);
        teamName = findViewById(R.id.team_name);
        databaseReference = FirebaseDatabase.getInstance().getReference("teams");
        saveTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addTeam();
            }
        });


    }

    private void addTeam() {
        String name = teamName.getText().toString();
        if (!TextUtils.isEmpty(name)) {
            String id = databaseReference.push().getKey();
            Team team = new Team(id, name);
            databaseReference.child(id).setValue(team);
            Toast.makeText(this, "Team added!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Team not added!", Toast.LENGTH_LONG).show();
        }
    }
}
