package com.hfad.lzr;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
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
    Game game;
    Toolbar toolbar;
    TabLayout tabs;
    String teamId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lineup);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Choose lineup");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        game = ( Game ) getIntent().getSerializableExtra("game");
        teamId = getIntent().getStringExtra("teamId");


        viewPager = findViewById(R.id.view_pager);
        setupViewPager(viewPager);


        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager, true);
        tabs.setSelected(true);

        tabs.setTabTextColors(getResources().getColor(R.color.tab_not_selected),
                getResources().getColor(R.color.white));

        tabs.setSelectedTabIndicatorColor(getResources().getColor(R.color.orange_start));

        startGame = findViewById(R.id.start_game);

        databaseReferenceGames = FirebaseDatabase.getInstance().getReference("games");

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChooseLineupFragment fragment1 = (ChooseLineupFragment) sectionsPagerAdapter.getItem(0);
                ChooseLineupFragment fragment2 = (ChooseLineupFragment) sectionsPagerAdapter.getItem(1);

                List<Player> playersTeamA = fragment1.getData();
                List<Player> playersTeamB = fragment2.getData();

                if (playersTeamA.size() >= 5 && playersTeamB.size() >= 5) {
                    String id = databaseReferenceGames.push().getKey();

                    List<PlayerGame> playersGameA = new ArrayList<>();
                    for (int i = 0; i < playersTeamA.size(); i++) {
                        Player player = playersTeamA.get(i);
                        PlayerGame playerGame = new PlayerGame(id, player);
                        if (i < 5) {
                            playerGame.setWhenGoingIn(6);
                            playerGame.setmIsEnabled(true);
                        } else {
                            playerGame.setmIsEnabled(false);
                        }
                        playersGameA.add(playerGame);
                    }

                    List<PlayerGame> playersGameB = new ArrayList<>();
                    for (int i = 0; i < playersTeamB.size(); i++) {
                        Player player = playersTeamB.get(i);
                        PlayerGame playerGame = new PlayerGame(id, player);
                        if (i < 5) {
                            playerGame.setWhenGoingIn(6);
                            playerGame.setmIsEnabled(true);
                        } else {
                            playerGame.setmIsEnabled(false);
                        }
                        playersGameB.add(playerGame);
                    }

                    Intent intent = new Intent(LineupActivity.this, GameActivity.class);
                    intent.putExtra("playersGameA", (Serializable) playersGameA);
                    intent.putExtra("playersGameB", (Serializable) playersGameB);
                    intent.putExtra("game", game);

                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.teams_must_start_with_5_players, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        sectionsPagerAdapter.addFragment(ChooseLineupFragment.newInstance(game, game.getIdTeamA()), game.getTeamAnaziv());
        sectionsPagerAdapter.addFragment(ChooseLineupFragment.newInstance(game, game.getIdTeamB()), game.getTeamBnaziv());
        viewPager.setAdapter(sectionsPagerAdapter);


    }
}