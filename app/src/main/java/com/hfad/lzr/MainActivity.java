package com.hfad.lzr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createGame(View view){
        Intent intent = new Intent(this, CreatingMatchActivity.class);
        startActivity(intent);
    }

    public void teams(View view){
        Intent intent = new Intent(this, TeamsActivity.class);
        startActivity(intent);
    }

    public void createTeam(View view){
        Intent intent = new Intent(this, CreateTeamActivity.class);
        startActivity(intent);
    }

}
