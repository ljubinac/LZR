package com.hfad.lzr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hfad.lzr.adapter.PlayersAdapter;
import com.hfad.lzr.adapter.PlayersGameAdapter;
import com.hfad.lzr.model.Player;
import com.hfad.lzr.model.PlayerGame;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    ArrayList<PlayerGame> playersGameA;
    ArrayList<PlayerGame> playersGameB;
    String teamAid;
    String teamBid;
    RecyclerView teamArv;
    RecyclerView teamBrv;
    PlayersGameAdapter adapterA;
    PlayersGameAdapter adapterB;
    DatabaseReference databaseReferenceA;
    DatabaseReference databaseReferenceB;
    RecyclerView.LayoutManager layoutManager;
    String teamA;
    String teamB;
    TextView teamATV;
    TextView teamBTV;
    PlayerGame current;
    LinearLayout ll2pm;
    LinearLayout ll2pa;
    TextView tv2pm;
    TextView tv2pa;
    TextView resAtv;
    TextView resBtv;
    int resA;
    int resB;
    boolean currentTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        playersGameA = (ArrayList<PlayerGame>) getIntent().getSerializableExtra("playersGameA");
        playersGameB = (ArrayList<PlayerGame>) getIntent().getSerializableExtra("playersGameB");
        teamAid = getIntent().getStringExtra("teamAid");
        teamBid = getIntent().getStringExtra("teamBid");
        teamA = getIntent().getStringExtra("teamA");
        teamB = getIntent().getStringExtra("teamB");


        teamATV = findViewById(R.id.teamA);
        teamBTV = findViewById(R.id.teamB);

        teamATV.setText(teamA);
        teamBTV.setText(teamB);

        ll2pm = findViewById(R.id.ll_2PM);
        ll2pa = findViewById(R.id.ll_2PA);
        tv2pm = findViewById(R.id.tv2pm);
        tv2pa = findViewById(R.id.tv2pa);
        resAtv = findViewById(R.id.scoreTeamA);
        resBtv = findViewById(R.id.scoreTeamB);

        resA = 0;
        resB = 0;
        currentTeam = false;

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

        ll2pa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.setPa2(current.getPa2() + 1);
                tv2pa.setText(String.valueOf(current.getPa2()));
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
    }


    public void buildRecyclerViewA() {
        teamArv = findViewById(R.id.firstTeamRV);
        teamArv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        adapterA = new PlayersGameAdapter(playersGameA);

        teamArv.setLayoutManager(layoutManager);
        teamArv.setAdapter(adapterA);
        adapterA.onItemClickListener(new PlayersGameAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                current = playersGameA.get(position);
                setValues();
                currentTeam =false;
            }
        });
    }

    public void buildRecyclerViewB() {
        teamBrv = findViewById(R.id.secondTeamRV);
        teamBrv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        adapterB = new PlayersGameAdapter(playersGameB);

        teamBrv.setLayoutManager(layoutManager);
        teamBrv.setAdapter(adapterB);
        adapterB.onItemClickListener(new PlayersGameAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                current = playersGameB.get(position);
                setValues();
                currentTeam = true;
            }
        });
    }
}
