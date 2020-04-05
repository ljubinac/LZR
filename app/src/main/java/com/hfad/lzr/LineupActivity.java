package com.hfad.lzr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.tabs.TabLayout;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hfad.lzr.model.Game;
import com.hfad.lzr.model.Player;
import com.hfad.lzr.model.PlayerGame;
import com.hfad.lzr.ui.main.ChooseLineupFragment;
import com.hfad.lzr.ui.main.SectionsPagerAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class LineupActivity extends AppCompatActivity {

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private Button startGame;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lineup);

        viewPager = findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        databaseReference = FirebaseDatabase.getInstance().getReference("game");

        startGame = findViewById(R.id.start_game);

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseLineupFragment fragment1 = (ChooseLineupFragment) sectionsPagerAdapter.getItem(0);
                ChooseLineupFragment fragment2 = (ChooseLineupFragment) sectionsPagerAdapter.getItem(1);

                List<Player> playersTeamA = fragment1.getData();
                Toast.makeText(getApplicationContext(), " TeamA = " + playersTeamA.size() + " players", Toast.LENGTH_LONG).show();
                List<Player> playersTeamB = fragment2.getData();
                Toast.makeText(getApplicationContext(), "TeamB = " + playersTeamB.size() + " players", Toast.LENGTH_LONG).show();

                String gameId = saveGame();

                List<PlayerGame> playerGamesA = new ArrayList<>();
                List<PlayerGame> playerGamesB = new ArrayList<>();

                for (Player player : playersTeamA){
                    playerGamesA.add(new PlayerGame(player, gameId));
                }

                for (Player player : playersTeamB){
                    playerGamesB.add(new PlayerGame(player, gameId));
                }

                Intent intent = new Intent(LineupActivity.this, GameActivity.class);
                intent.putExtra("playersTeamA", ( Serializable ) playerGamesA);
                intent.putExtra("playersTeamB", ( Serializable ) playerGamesB);
                intent.putExtra("gameId", gameId);
                startActivity(intent);
            }
        });

    }

    private String saveGame() {
        String time = getIntent().getStringExtra("time");
        String date = getIntent().getStringExtra("date");
        String teamAid = getIntent().getStringExtra("teamAId");
        String teamBid = getIntent().getStringExtra("teamBId");

        String id = databaseReference.push().getKey();
        Game game = new Game(date, teamAid, teamBid, id, time);
        databaseReference.child(id).setValue(game);
        return id;
    }


    private void setupViewPager(ViewPager viewPager){
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        String teamAName = getIntent().getStringExtra("teamA");
        String teamBName = getIntent().getStringExtra("teamB");
        String teamAId = getIntent().getStringExtra("teamAId");
        String teamBId = getIntent().getStringExtra("teamBId");

        sectionsPagerAdapter.addFragment(ChooseLineupFragment.newInstance(teamAName, teamAId), getIntent().getStringExtra("teamA"));
        sectionsPagerAdapter.addFragment(ChooseLineupFragment.newInstance(teamBName, teamBId), getIntent().getStringExtra("teamB"));
        viewPager.setAdapter(sectionsPagerAdapter);
    }
}