package com.hfad.lzr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.hfad.lzr.model.Player;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        List<Player> playersTeamA = (ArrayList<Player>) getIntent().getSerializableExtra("playersTeamA");
        Toast.makeText(getApplicationContext(), "Game activity A", Toast.LENGTH_LONG).show();
        List<Player> playersTeamB = (ArrayList<Player>) getIntent().getSerializableExtra("playersTeamB");
        Toast.makeText(getApplicationContext(), "Game activity B", Toast.LENGTH_LONG).show();
    }
}
