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
    DatabaseReference databaseReferenceGames;
    String gameDate;
    String gameTime;
    String teamAid;
    String teamBid;
    String teamA;
    String teamB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lineup);

         gameDate = getIntent().getStringExtra("gameDate");
         gameTime = getIntent().getStringExtra("gameTime");
         teamAid = getIntent().getStringExtra("teamAId");
         teamBid = getIntent().getStringExtra("teamBId");
         teamA = getIntent().getStringExtra("teamA");
         teamB = getIntent().getStringExtra("teamB");



        viewPager = findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        startGame = findViewById(R.id.start_game);

        databaseReferenceGames = FirebaseDatabase.getInstance().getReference("games");

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = databaseReferenceGames.push().getKey();
                Game game = new Game(id, teamAid, teamBid, gameDate, gameTime);
                databaseReferenceGames.child(id).setValue(game);

                ChooseLineupFragment fragment1 = (ChooseLineupFragment) sectionsPagerAdapter.getItem(0);
                ChooseLineupFragment fragment2 = (ChooseLineupFragment) sectionsPagerAdapter.getItem(1);

                List<PlayerGame> playersGameA = new ArrayList<>();
                List<Player> playersTeamA = fragment1.getData();
                for (Player p : playersTeamA){
                    PlayerGame playerGame = new PlayerGame(id, p);
                    playersGameA.add(playerGame);
                }

                List<PlayerGame> playersGameB = new ArrayList<>();
                List<Player> playersTeamB = fragment2.getData();
                for (Player p : playersTeamB){
                    PlayerGame playerGame = new PlayerGame(id, p);
                    playersGameB.add(playerGame);
                }

                if (playersGameA.size() >= 5 && playersGameB.size() >= 5) {
                    Intent intent = new Intent(LineupActivity.this, GameActivity.class);
                    intent.putExtra("playersGameA", ( Serializable ) playersGameA);
                    intent.putExtra("playersGameB", ( Serializable ) playersGameB);
                    intent.putExtra("teamAid", teamAid);
                    intent.putExtra("teamBid", teamBid);
                    intent.putExtra("teamA", teamA);
                    intent.putExtra("teamB", teamB);

                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Teams must start with 5 players!", Toast.LENGTH_LONG).show();
                }
            }
        });

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