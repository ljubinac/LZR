package com.hfad.lzr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hfad.lzr.adapter.PlayersAdapter;
import com.hfad.lzr.adapter.PlayersGameAdapter;
import com.hfad.lzr.model.Game;
import com.hfad.lzr.model.Player;
import com.hfad.lzr.model.PlayerGame;
import com.hfad.lzr.model.Team;
import com.hfad.lzr.ui.main.LineupDialog;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    ArrayList<PlayerGame> playersGameA, playersGameB;
    RecyclerView teamArv, teamBrv;
    PlayersGameAdapter adapterA, adapterB;
    RecyclerView.LayoutManager layoutManagerA, layoutManagerB;
    TextView teamATV, teamBTV;
    PlayerGame current;
    LinearLayout ll2pm, ll2pa, ll3pm, ll3pa, ll1pm, ll1pa, llOffReb, llDefReb, llAsist, llTurnov, llBlock, llFoul, llSteals, llTehnical, llFinishGame;
    TextView tv2pm, tv2pa, resAtv, resBtv, tv3pm, tv3pa, tv1pm, tv1pa, tvOffReb, tvDefReb, tvAsist, tvTurnov, tvBlock, tvFoul, tvSteals, tvTehnical, tvFinishGame;
    int resA, resB;
    boolean currentTeam;
    Game game;
    Team teamA;
    Team teamB;

    private static final String TAG = "GameActivity";

    private int seconds = 600;
    private  boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if(savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();

        playersGameA = (ArrayList<PlayerGame>) getIntent().getSerializableExtra("playersGameA");
        playersGameB = (ArrayList<PlayerGame>) getIntent().getSerializableExtra("playersGameB");
//        firstLineupGameA = (ArrayList<PlayerGame>) getIntent().getSerializableExtra("firstLineupGameA");
//        firstLineupGameB = (ArrayList<PlayerGame>) getIntent().getSerializableExtra("firstLineupGameB");
        teamA = ( Team ) getIntent().getSerializableExtra("teamA");
        teamB = ( Team ) getIntent().getSerializableExtra("teamB");
        game = ( Game ) getIntent().getSerializableExtra("game");

        teamATV = findViewById(R.id.teamA);
        teamBTV = findViewById(R.id.teamB);

        teamATV.setText(game.getTeamAnaziv());
        teamBTV.setText(game.getTeamBnaziv());

        ll2pm = findViewById(R.id.ll_2PM);
        ll2pa = findViewById(R.id.ll_2PA);
        tv2pm = findViewById(R.id.tv2pm);
        tv2pa = findViewById(R.id.tv2pa);
        resAtv = findViewById(R.id.scoreTeamA);
        resBtv = findViewById(R.id.scoreTeamB);
        ll3pm = findViewById(R.id.ll_3PM);
        ll3pa = findViewById(R.id.ll_3PA);
        tv3pm = findViewById(R.id.tv3pm);
        tv3pa = findViewById(R.id.tv3pa);
        ll1pm = findViewById(R.id.ll_1PM);
        ll1pa = findViewById(R.id.ll_1PA);
        tv1pm = findViewById(R.id.tv1pm);
        tv1pa = findViewById(R.id.tv1pa);
        llOffReb = findViewById(R.id.ll_offReb);
        llDefReb = findViewById(R.id.ll_defReb);
        tvOffReb = findViewById(R.id.offRebTV);
        tvDefReb = findViewById(R.id.defRebTV);
        llAsist = findViewById(R.id.ll_assist);
        llTurnov = findViewById(R.id.ll_turnovers);
        tvAsist = findViewById(R.id.assistTV);
        tvTurnov = findViewById(R.id.turnoversTV);
        llBlock = findViewById(R.id.ll_block);
        llFoul = findViewById(R.id.ll_foul);
        tvBlock = findViewById(R.id.blockTV);
        tvFoul = findViewById(R.id.foulTV);
        llSteals = findViewById(R.id.ll_steals);
        llTehnical = findViewById(R.id.ll_tehnicalFoul);
        tvSteals = findViewById(R.id.stealsTV);
        tvTehnical = findViewById(R.id.tehnicalFoulTV);

        llFinishGame = findViewById(R.id.ll_finishGame);
        tvFinishGame = findViewById(R.id.finishGameTV);

        databaseReference = FirebaseDatabase.getInstance().getReference("teams");

        resA = 0;
        resB = 0;
        currentTeam = false;
        current = playersGameA.get(0);

        buildRecyclerViewA();
        buildRecyclerViewB();

        resAtv.setText(String.valueOf(resA));
        resBtv.setText(String.valueOf(resB));

        ll2pm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.setPm2(current.getPm2() + 1);
                current.setPa2(current.getPa2() + 1);
                tv2pm.setText(String.valueOf(current.getPm2()));
                tv2pa.setText(String.valueOf(current.getPa2()));
                setRes(2);
            }
        });

        ll2pm.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                current.setPm2(current.getPm2() - 1);
                current.setPa2(current.getPa2() - 1);
                tv2pm.setText(String.valueOf(current.getPm2()));
                tv2pa.setText(String.valueOf(current.getPa2()));
                setRes(-2);
                return true;
            }
        });

        ll2pa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.setPa2(current.getPa2() + 1);
                tv2pa.setText(String.valueOf(current.getPa2()));
            }
        });

        ll2pa.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                current.setPa2(current.getPa2() - 1);
                tv2pa.setText(String.valueOf(current.getPa2()));
                return true;
            }
        });

        ll3pm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.setPm3(current.getPm3() + 1);
                current.setPa3(current.getPa3() + 1);
                tv3pm.setText(String.valueOf(current.getPm3()));
                tv3pa.setText(String.valueOf(current.getPa3()));
                setRes(3);
            }
        });

        ll3pm.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                current.setPm3(current.getPm3() - 1);
                current.setPa3(current.getPa3() - 1);
                tv3pm.setText(String.valueOf(current.getPm3()));
                tv3pa.setText(String.valueOf(current.getPa3()));
                setRes(-3);
                return true;
            }
        });

        ll3pa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.setPa3(current.getPa3() + 1);
                tv3pa.setText(String.valueOf(current.getPa3()));
            }
        });

        ll3pa.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                current.setPa3(current.getPa3() - 1);
                tv3pa.setText(String.valueOf(current.getPa3()));
                return true;
            }
        });

        ll1pm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.setPm1(current.getPm1() + 1);
                current.setPa1(current.getPa1() + 1);
                tv1pm.setText(String.valueOf(current.getPm1()));
                tv1pa.setText(String.valueOf(current.getPa1()));
                setRes(1);
            }
        });

        ll1pm.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                current.setPm1(current.getPm1() - 1);
                current.setPa1(current.getPa1() - 1);
                tv1pm.setText(String.valueOf(current.getPm1()));
                tv1pa.setText(String.valueOf(current.getPa1()));
                setRes(-1);
                return true;
            }
        });

        ll1pa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.setPa1(current.getPa1() + 1);
                tv1pa.setText(String.valueOf(current.getPa1()));
            }
        });

        ll1pa.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                current.setPa1(current.getPa1() - 1);
                tv1pa.setText(String.valueOf(current.getPa1()));
                return true;
            }
        });

        llOffReb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.setOffReb(current.getOffReb() + 1);
                tvOffReb.setText(String.valueOf(current.getOffReb()));
            }
        });

        llOffReb.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                current.setOffReb(current.getOffReb() - 1);
                tvOffReb.setText(String.valueOf(current.getOffReb()));
                return true;
            }
        });

        llDefReb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.setDefReb(current.getDefReb() + 1);
                tvDefReb.setText(String.valueOf(current.getDefReb()));
            }
        });

        llDefReb.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                current.setDefReb(current.getDefReb() - 1);
                tvDefReb.setText(String.valueOf(current.getDefReb()));
                return true;
            }
        });

        llAsist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.setAsist(current.getAsist() + 1);
                tvAsist.setText(String.valueOf(current.getAsist()));
            }
        });

        llAsist.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                current.setAsist(current.getAsist() - 1);
                tvAsist.setText(String.valueOf(current.getAsist()));
                return true;
            }
        });

        llBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.setBlock(current.getBlock() + 1);
                tvBlock.setText(String.valueOf(current.getBlock()));
            }
        });

        llBlock.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                current.setBlock(current.getBlock() - 1);
                tvBlock.setText(String.valueOf(current.getBlock()));
                return true;
            }
        });

        llSteals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.setSteal(current.getSteal() + 1);
                tvSteals.setText(String.valueOf(current.getSteal()));
            }
        });

        llSteals.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                current.setSteal(current.getSteal() - 1);
                tvSteals.setText(String.valueOf(current.getSteal()));
                return true;
            }
        });

        llTurnov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.setTurnover(current.getTurnover() + 1);
                tvTurnov.setText(String.valueOf(current.getTurnover()));
            }
        });

        llTurnov.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                current.setTurnover(current.getTurnover() - 1);
                tvTurnov.setText(String.valueOf(current.getTurnover()));
                return true;
            }
        });

        llFoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current.getFoul() > 4){
                    Toast.makeText(getApplicationContext(), "The player has 5 fouls!", Toast.LENGTH_LONG).show();
                } else {
                    current.setFoul(current.getFoul() + 1);
                    tvFoul.setText(String.valueOf(current.getFoul()));
                }
            }
        });

        llFoul.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                current.setFoul(current.getFoul() - 1);
                tvFoul.setText(String.valueOf(current.getFoul()));
                return true;
            }
        });

        llTehnical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.setTehnicalFoul(current.getTehnicalFoul() + 1);
                tvTehnical.setText(String.valueOf(current.getTehnicalFoul()));
            }
        });

        llTehnical.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                current.setTehnicalFoul(current.getTehnicalFoul() - 1);
                tvTehnical.setText(String.valueOf(current.getTehnicalFoul()));
                return true;
            }
        });

        llFinishGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setResA(resA);
                game.setResB(resB);
                teamA.setPointsScored(teamA.getPointsScored() + resA);
                teamA.setPointsReceived(teamA.getPointsReceived() + resB);
                teamB.setPointsScored(teamB.getPointsScored() + resB);
                teamB.setPointsReceived(teamB.getPointsReceived() + resA);
                if(resA > resB){
                    teamA.setPoints(teamA.getPoints() + 2);
                    teamB.setPoints(teamB.getPoints() + 1);
                } else {
                    teamB.setPoints(teamB.getPoints() + 2);
                    teamA.setPoints(teamA.getPoints() + 1);
                }
                teamA.setPlayed(teamA.getPlayed() + 1);
                teamB.setPlayed(teamB.getPlayed() + 1);
                if(resA > resB){
                    teamA.setWin(teamA.getWin() + 1);
                    teamB.setLost(teamB.getLost() + 1);
                } else {
                    teamB.setWin(teamB.getWin() + 1);
                    teamA.setLost(teamA.getLost() + 1);
                }
                databaseReference.child(teamA.getId()).setValue(teamA);
                databaseReference.child(teamB.getId()).setValue(teamB);
                Intent intent = new Intent(GameActivity.this, StatsActivity.class);
                intent.putExtra("playersGameA", playersGameA);
                intent.putExtra("playersGameB", playersGameB);
                intent.putExtra("game", game);
                startActivity(intent);
            }
        });
    }

    public void setRes(int points){
        if (currentTeam){
            resB = resB + points;
            resBtv.setText(String.valueOf(resB));
        } else {
            resA = resA + points;
            resAtv.setText(String.valueOf(resA));
        }
    }

    public void setValues(){
        tv2pm.setText(String.valueOf(current.getPm2()));
        tv2pa.setText(String.valueOf(current.getPa2()));

        tv3pm.setText(String.valueOf(current.getPm3()));
        tv3pa.setText(String.valueOf(current.getPa3()));

        tv1pm.setText(String.valueOf(current.getPm1()));
        tv1pa.setText(String.valueOf(current.getPa1()));

        tvOffReb.setText(String.valueOf(current.getOffReb()));
        tvDefReb.setText(String.valueOf(current.getDefReb()));

        tvAsist.setText(String.valueOf(current.getAsist()));
        tvBlock.setText(String.valueOf(current.getBlock()));

        tvSteals.setText(String.valueOf(current.getSteal()));
        tvTurnov.setText(String.valueOf(current.getTurnover()));

        tvFoul.setText(String.valueOf(current.getFoul()));
        tvTehnical.setText(String.valueOf(current.getTehnicalFoul()));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onPause(){
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected  void onResume(){
        super.onResume();
        if (wasRunning){
            running = true;
        }

    }

    public void onClickStart(View view){
        running = true;
    }

    public void onClickStop(View view){
        running = false;
    }

    public void onClickReset(View view){
        running = false;
        seconds = 600;
    }

    private void runTimer(){
        final TextView timeView = (TextView) findViewById(R.id.time);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%02d:%02d", minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds--;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }


    public void buildRecyclerViewA() {
        teamArv = findViewById(R.id.firstTeamRV);
        teamArv.setHasFixedSize(true);
        layoutManagerA = new LinearLayoutManager(getApplicationContext());
        adapterA = new PlayersGameAdapter(playersGameA);

        teamArv.setLayoutManager(layoutManagerA);
        teamArv.setAdapter(adapterA);
        adapterA.onItemClickListener(new PlayersGameAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                current = playersGameA.get(position);
                setValues();
                currentTeam = false;
                adapterA.notifyItemChanged(adapterA.selectedPos);
                adapterB.notifyItemChanged(adapterB.selectedPos);
                adapterA.selectedPos = position;
                adapterB.selectedPos = RecyclerView.NO_POSITION;
                adapterB.notifyItemChanged(adapterB.selectedPos);
                adapterA.notifyItemChanged(adapterA.selectedPos);
            }

            @Override
            public void onLongClick(int position) {
                Log.d(TAG, "onClick: opening dialog.");

                LineupDialog dialog = new LineupDialog();

                Bundle args = new Bundle();
                args.putSerializable("playersGameA", playersGameA);
                dialog.setArguments(args);
                dialog.show(getSupportFragmentManager(), "LineupDialog");
            }
        });
    }

    public void buildRecyclerViewB() {
        teamBrv = findViewById(R.id.secondTeamRV);
        teamBrv.setHasFixedSize(true);
        layoutManagerB = new LinearLayoutManager(getApplicationContext());
        adapterB = new PlayersGameAdapter(playersGameB);

        teamBrv.setLayoutManager(layoutManagerB);
        teamBrv.setAdapter(adapterB);
        adapterB.onItemClickListener(new PlayersGameAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                current = playersGameB.get(position);
                setValues();
                currentTeam = true;
                adapterB.notifyItemChanged(adapterB.selectedPos);
                adapterA.notifyItemChanged(adapterA.selectedPos);
                adapterB.selectedPos = position;
                adapterA.selectedPos = RecyclerView.NO_POSITION;
                adapterA.notifyItemChanged(adapterA.selectedPos);
                adapterB.notifyItemChanged(adapterB.selectedPos);
            }

            @Override
            public void onLongClick(int position) {
                Log.d(TAG, "onClick: opening dialog.");

                LineupDialog dialog = new LineupDialog();

                Bundle args = new Bundle();
                args.putSerializable("playersGameB", playersGameB);
                dialog.setArguments(args);
                dialog.show(getSupportFragmentManager(), "LineupDialog");
            }
        });
    }
}