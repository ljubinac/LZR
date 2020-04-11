package com.hfad.lzr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.hfad.lzr.model.PlayerGame;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    ArrayList<PlayerGame> playersGameA;
    ArrayList<PlayerGame> playersGameB;

    TableLayout ll;
    TableLayout ll2;

    TextView tableNum;
    TextView tableName;
    TextView tableFG;
    TextView table2pts;
    TextView table3pts;
    TextView table1pts;
    TextView tableTotalReb;
    TextView tableDefReb;
    TextView tableOffReb;
    TextView tableAssist;
    TextView tableBlock;
    TextView tableSteals;
    TextView tableTurnov;
    TextView tableFoul;
    TextView tableTeh;
    TextView tablePts;
    TextView tableEff;


    TextView number;
    TextView name;
    TextView fg;
    TextView pts2;
    TextView pts3;
    TextView pts1;
    TextView totalReb;
    TextView offReb;
    TextView defReb;
    TextView assist;
    TextView block;
    TextView steal;
    TextView turnov;
    TextView foul;
    TextView teh;
    TextView pts;
    TextView eff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

         ll = (TableLayout) findViewById(R.id.player_game_A);
         ll2 = (TableLayout) findViewById(R.id.player_game_B);

        playersGameA = ( ArrayList<PlayerGame> ) getIntent().getSerializableExtra("playersGameA");
        playersGameB = ( ArrayList<PlayerGame> ) getIntent().getSerializableExtra("playersGameB");

        init(ll, playersGameA);
        init(ll2, playersGameB);

    }

    public void init(TableLayout ll, ArrayList<PlayerGame> playersGameA){

        TableRow row= new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        row.setBackgroundResource(R.drawable.table_border);

        tableNum = new TextView(this);
        tableNum.setBackgroundResource(R.drawable.table_border);
        tableNum.setPadding(15,15,15,15);

        tableName = new TextView(this);
        tableName.setBackgroundResource(R.drawable.table_border);
        tableName.setPadding(15,15,15,15);

        tableFG = new TextView(this);
        tableFG.setBackgroundResource(R.drawable.table_border);
        tableFG.setPadding(15,15,15,15);

        table2pts = new TextView(this);
        table2pts.setBackgroundResource(R.drawable.table_border);
        table2pts.setPadding(15,15,15,15);

        table3pts = new TextView(this);
        table3pts.setBackgroundResource(R.drawable.table_border);
        table3pts.setPadding(15,15,15,15);

        table1pts = new TextView(this);
        table1pts.setBackgroundResource(R.drawable.table_border);
        table1pts.setPadding(15,15,15,15);

        tableTotalReb = new TextView(this);
        tableTotalReb.setBackgroundResource(R.drawable.table_border);
        tableTotalReb.setPadding(15,15,15,15);

        tableDefReb = new TextView(this);
        tableDefReb.setBackgroundResource(R.drawable.table_border);
        tableDefReb.setPadding(15,15,15,15);

        tableOffReb = new TextView(this);
        tableOffReb.setBackgroundResource(R.drawable.table_border);
        tableOffReb.setPadding(15,15,15,15);

        tableAssist = new TextView(this);
        tableAssist.setBackgroundResource(R.drawable.table_border);
        tableAssist.setPadding(15,15,15,15);

        tableBlock = new TextView(this);
        tableBlock.setBackgroundResource(R.drawable.table_border);
        tableBlock.setPadding(15,15,15,15);

        tableSteals = new TextView(this);
        tableSteals.setBackgroundResource(R.drawable.table_border);
        tableSteals.setPadding(15,15,15,15);

        tableTurnov = new TextView(this);
        tableTurnov.setBackgroundResource(R.drawable.table_border);
        tableTurnov.setPadding(15,15,15,15);

        tableFoul = new TextView(this);
        tableFoul.setBackgroundResource(R.drawable.table_border);
        tableFoul.setPadding(15,15,15,15);

        tablePts = new TextView(this);
        tablePts.setBackgroundResource(R.drawable.table_border);
        tablePts.setPadding(15,15,15,15);

        tableEff = new TextView(this);
        tableEff.setBackgroundResource(R.drawable.table_border);
        tableEff.setPadding(15,15,15,15);

        /*tableTeh = new TextView(this);
        tableTeh.setBackgroundResource(R.drawable.table_border);
        tableTeh.setPadding(15,15,15,15);*/


        tableNum.setText("#No");
        tableName.setText("PLAYER");
        tableFG.setText("FG");
        table2pts.setText("2pts");
        table3pts.setText("3pts");
        table1pts.setText("FT");
        tableOffReb.setText("O REB");
        tableDefReb.setText("D REB");
        tableTotalReb.setText("REB");
        tableAssist.setText("AST");
        tableBlock.setText("BLK");
        tableSteals.setText("STL");
        tableTurnov.setText("TOV");
        tableFoul.setText("PF");
        tablePts.setText("PTS");
        tableEff.setText("EFF");


        row.addView(tableNum);
        row.addView(tableName);
        row.addView(tableFG);
        row.addView(table2pts);
        row.addView(table3pts);
        row.addView(table1pts);
        row.addView(tableOffReb);
        row.addView(tableDefReb);
        row.addView(tableTotalReb);
        row.addView(tableAssist);
        row.addView(tableBlock);
        row.addView(tableSteals);
        row.addView(tableTurnov);
        row.addView(tableFoul);
        row.addView(tablePts);
        row.addView(tableEff);


        ll.addView(row, 0);

        for (int i = 0; i < playersGameA.size(); i++){

            TableRow row2 = new TableRow(this);
            row2.setLayoutParams(lp);
            row2.setBackgroundResource(R.drawable.table_border);


            number = new TextView(this);
            number.setBackgroundResource(R.drawable.table_border);
            number.setPadding(15,15,15,15);

            name = new TextView(this);
            name.setBackgroundResource(R.drawable.table_border);
            name.setPadding(15,15,15,15);

            fg = new TextView(this);
            fg.setBackgroundResource(R.drawable.table_border);
            fg.setPadding(15,15,15,15);

            pts2 = new TextView(this);
            pts2.setBackgroundResource(R.drawable.table_border);
            pts2.setPadding(15,15,15,15);

            pts3 = new TextView(this);
            pts3.setBackgroundResource(R.drawable.table_border);
            pts3.setPadding(15,15,15,15);

            pts1 = new TextView(this);
            pts1.setBackgroundResource(R.drawable.table_border);
            pts1.setPadding(15,15,15,15);

            offReb = new TextView(this);
            offReb.setBackgroundResource(R.drawable.table_border);
            offReb.setPadding(15,15,15,15);

            defReb = new TextView(this);
            defReb.setBackgroundResource(R.drawable.table_border);
            defReb.setPadding(15,15,15,15);

            totalReb = new TextView(this);
            totalReb.setBackgroundResource(R.drawable.table_border);
            totalReb.setPadding(15,15,15,15);

            assist = new TextView(this);
            assist.setBackgroundResource(R.drawable.table_border);
            assist.setPadding(15,15,15,15);

            block = new TextView(this);
            block.setBackgroundResource(R.drawable.table_border);
            block.setPadding(15,15,15,15);

            steal = new TextView(this);
            steal.setBackgroundResource(R.drawable.table_border);
            steal.setPadding(15,15,15,15);

            turnov = new TextView(this);
            turnov.setBackgroundResource(R.drawable.table_border);
            turnov.setPadding(15,15,15,15);

            foul = new TextView(this);
            foul.setBackgroundResource(R.drawable.table_border);
            foul.setPadding(15,15,15,15);

            pts = new TextView(this);
            pts.setBackgroundResource(R.drawable.table_border);
            pts.setPadding(15,15,15,15);

            eff = new TextView(this);
            eff.setBackgroundResource(R.drawable.table_border);
            eff.setPadding(15,15,15,15);

            number.setText(playersGameA.get(i).getNumber());
            name.setText(playersGameA.get(i).getNameAndLastname());

            String fgm = String.valueOf(playersGameA.get(i).getPm2() + playersGameA.get(i).getPm3());
            String fga = String.valueOf(playersGameA.get(i).getPa2() + playersGameA.get(i).getPa3());
            String totalFG = fgm + "/" + fga;
            fg.setText(totalFG);

            String p2 = playersGameA.get(i).getPm2() + "/" + playersGameA.get(i).getPa2();
            pts2.setText(p2);

            String p3 = playersGameA.get(i).getPm3() + "/" + playersGameA.get(i).getPa3();
            pts3.setText(p3);

            String p1 = playersGameA.get(i).getPm1() + "/" + playersGameA.get(i).getPa1();
            pts1.setText(p1);

            offReb.setText(String.valueOf(playersGameA.get(i).getOffReb()));
            defReb.setText(String.valueOf(playersGameA.get(i).getDefReb()));
            String tReb = String.valueOf(playersGameA.get(i).getOffReb() + playersGameA.get(i).getDefReb());
            totalReb.setText(tReb);
            assist.setText(String.valueOf(playersGameA.get(i).getAsist()));
            block.setText(String.valueOf(playersGameA.get(i).getBlock()));
            steal.setText(String.valueOf(playersGameA.get(i).getSteal()));
            turnov.setText(String.valueOf(playersGameA.get(i).getTurnover()));
            foul.setText(String.valueOf(playersGameA.get(i).getFoul()));

            String totalPoints = String.valueOf((playersGameA.get(i).getPm2() * 2)
                    + (playersGameA.get(i).getPm3() * 3) + playersGameA.get(i).getPm1());
            pts.setText(totalPoints);

            String index = String.valueOf(((playersGameA.get(i).getPm2() * 2)
                    + (playersGameA.get(i).getPm3() * 3)
                    + playersGameA.get(i).getPm1())
                    + (playersGameA.get(i).getOffReb() + playersGameA.get(i).getDefReb())
                    + playersGameA.get(i).getAsist() + playersGameA.get(i).getBlock()
                    + playersGameA.get(i).getSteal() - playersGameA.get(i).getTurnover()
                    - playersGameA.get(i).getFoul() - (playersGameA.get(i).getPa2()-playersGameA.get(i).getPm2())
                    - (playersGameA.get(i).getPa3()-playersGameA.get(i).getPm3())
                    - (playersGameA.get(i).getPa1()-playersGameA.get(i).getPm1()) );
            eff.setText(index);

            row2.addView(number);
            row2.addView(name);
            row2.addView(fg);
            row2.addView(pts2);
            row2.addView(pts3);
            row2.addView(pts1);
            row2.addView(offReb);
            row2.addView(defReb);
            row2.addView(totalReb);
            row2.addView(assist);
            row2.addView(block);
            row2.addView(steal);
            row2.addView(turnov);
            row2.addView(foul);
            row2.addView(pts);
            row2.addView(eff);

            ll.addView(row2, i+1);
        }
    }
}
