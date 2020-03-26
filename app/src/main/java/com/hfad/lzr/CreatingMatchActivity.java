package com.hfad.lzr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CreatingMatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_match);
    }

    public void addTeam(View view){
        Intent intent = new Intent(this, CreateTeamActivity.class);
        startActivity(intent);
    }
}
