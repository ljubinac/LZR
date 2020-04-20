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
import com.hfad.lzr.model.Team;
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
    String gameDate, gameTime;
    Team teamA;
    Team teamB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lineup);

        gameDate = getIntent().getStringExtra("gameDate");
        gameTime = getIntent().getStringExtra("gameTime");
        teamA = ( Team ) getIntent().getSerializableExtra("teamA");
        teamB = ( Team ) getIntent().getSerializableExtra("teamB");

        viewPager = findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        startGame = findViewById(R.id.start_game);

        databaseReferenceGames = FirebaseDatabase.getInstance().getReference("games");

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChooseLineupFragment fragment1 = (ChooseLineupFragment) sectionsPagerAdapter.getItem(0);
                ChooseLineupFragment fragment2 = (ChooseLineupFragment) sectionsPagerAdapter.getItem(1);

                List<Player> playersTeamA = fragment1.getData();
                //List<Integer> firstLineupTeamA = fragment1.getFirstData();
                List<Player> playersTeamB = fragment2.getData();
                //List<Integer> firstLineupTeamB = fragment2.getFirstData();

                if (playersTeamA.size() >= 5 && playersTeamB.size() >= 5) {
                    String id = databaseReferenceGames.push().getKey();
                    Game game = new Game(id, teamA.getId(), teamB.getId(), gameDate, gameTime, teamA.getName(), teamB.getName());
                    databaseReferenceGames.child(id).setValue(game);

                    List<PlayerGame> playersGameA = new ArrayList<>();
                    for (int i = 0; i < playersTeamA.size(); i++){
                        Player player = playersTeamA.get(i);
                        PlayerGame playerGame = new PlayerGame(id, player);
                        if (i < 5){
                            playerGame.setWhenGoingIn(10);
                        }
                        playersGameA.add(playerGame);
                    }

                    List<PlayerGame> playersGameB = new ArrayList<>();
                    for (int i = 0; i < playersTeamB.size(); i++){
                        Player player = playersTeamB.get(i);
                        PlayerGame playerGame = new PlayerGame(id, player);
                        if (i < 5){
                            playerGame.setWhenGoingIn(10);
                        }
                        playersGameB.add(playerGame);
                    }

                    Intent intent = new Intent(LineupActivity.this, GameActivity.class);
                    //intent.putExtra("firstLineupGameA", (Serializable) firstLineupTeamA);
                    //intent.putExtra("firstLineupGameB", (Serializable) firstLineupTeamB);
                    intent.putExtra("playersGameA", (Serializable) playersGameA);
                    intent.putExtra("playersGameB", (Serializable) playersGameB);
                    intent.putExtra("teamA", teamA);
                    intent.putExtra("teamB", teamB);
                    intent.putExtra("game", game);

                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Teams must start with 5 players!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        sectionsPagerAdapter.addFragment(ChooseLineupFragment.newInstance(teamA.getName(), teamA.getId()), teamA.getName());
        sectionsPagerAdapter.addFragment(ChooseLineupFragment.newInstance(teamB.getName(), teamB.getId()), teamB.getName());
        viewPager.setAdapter(sectionsPagerAdapter);
    }
}