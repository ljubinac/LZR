package com.hfad.lzr.ui.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.lzr.R;
import com.hfad.lzr.model.Game;
import com.hfad.lzr.model.PlayerGame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatsFragment extends Fragment {

    ArrayList<PlayerGame> playersList;

    TextView tableNum, tableName, tableTime, tableFG, table2pts, table3pts, table1pts, tableTotalReb, tableDefReb, tableOffReb, tableAssist, tableBlock, tableSteals, tableTurnov, tableFoul, tablePts, tableEff;

    TextView number, name, time, fg, pts2, pts3, pts1, totalReb, offReb, defReb, assist, block, steal, turnov, foul, pts, eff;

    int teamTime, teamFGM, teamFGA, team2PTSpm, team2PTSpa, team3PTSpm, team3PTSpa, team1PTSpm, team1PTSpa, teamOffReb, teamDefReb, teamReb, teamAssist, teamBlocks, teamSteals, teamTurnovers, teamFouls, teamPoints, teamEff;
    TextView teamTV, teamTitle, teamTimeTV, teamFGtv, team2ptsTV, team3ptsTV, team1ptsTV, teamOffRebTV, teamDefRebTV, teamRebTV, teamAssistTV, teamBlocksTV, teamStealsTV, teamTurnTV, teamFoulsTV, teamPointsTV, teamEffTV;

    Game game;

    TableLayout table;


    public StatsFragment() {
        // Required empty public constructor
    }

    public static  StatsFragment newInstance(ArrayList<PlayerGame> playerGameList) {
        StatsFragment statsFragment = new StatsFragment();

        Bundle args = new Bundle();
        args.putSerializable("playerGameList", playerGameList);
        /*args.putSerializable("tableLayout", ( Serializable ) tableLayout);*/
        statsFragment.setArguments(args);
        return statsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        playersList = ( ArrayList<PlayerGame> ) getArguments().getSerializable("playerGameList");
        game = ( Game ) getArguments().getSerializable("game");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_stats, container, false);

        table = root.findViewById(R.id.player_game_A);

        init(playersList);
        setValue();

        return root;
    }

    public TableLayout getTable(){
        return table;
    }

    public void setValue() {
        teamTime = 0;
        teamFGM = 0;
        teamFGA = 0;
        team2PTSpm = 0;
        team2PTSpa = 0;
        team3PTSpm = 0;
        team3PTSpa = 0;
        team1PTSpm = 0;
        team1PTSpa = 0;
        teamDefReb = 0;
        teamOffReb = 0;
        teamReb = 0;
        teamAssist = 0;
        teamBlocks = 0;
        teamSteals = 0;
        teamTurnovers = 0;
        teamFouls = 0;
        teamPoints = 0;
        teamEff = 0;
    }

    public void init(ArrayList<PlayerGame> playersList) {

        TableRow row = new TableRow(getActivity());
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        row.setBackgroundResource(R.drawable.table_border);

        tableNum = new TextView(getActivity());
        tableNum.setBackgroundResource(R.drawable.table_border);
        tableNum.setPadding(15, 15, 15, 15);

        tableName = new TextView(getActivity());
        tableName.setBackgroundResource(R.drawable.table_border);
        tableName.setPadding(15, 15, 15, 15);

        tableTime = new TextView(getActivity());
        tableTime.setBackgroundResource(R.drawable.table_border);
        tableTime.setPadding(15, 15, 15, 15);

        tableFG = new TextView(getActivity());
        tableFG.setBackgroundResource(R.drawable.table_border);
        tableFG.setPadding(15, 15, 15, 15);

        table2pts = new TextView(getActivity());
        table2pts.setBackgroundResource(R.drawable.table_border);
        table2pts.setPadding(15, 15, 15, 15);

        table3pts = new TextView(getActivity());
        table3pts.setBackgroundResource(R.drawable.table_border);
        table3pts.setPadding(15, 15, 15, 15);

        table1pts = new TextView(getActivity());
        table1pts.setBackgroundResource(R.drawable.table_border);
        table1pts.setPadding(15, 15, 15, 15);

        tableTotalReb = new TextView(getActivity());
        tableTotalReb.setBackgroundResource(R.drawable.table_border);
        tableTotalReb.setPadding(15, 15, 15, 15);

        tableDefReb = new TextView(getActivity());
        tableDefReb.setBackgroundResource(R.drawable.table_border);
        tableDefReb.setPadding(15, 15, 15, 15);

        tableOffReb = new TextView(getActivity());
        tableOffReb.setBackgroundResource(R.drawable.table_border);
        tableOffReb.setPadding(15, 15, 15, 15);

        tableAssist = new TextView(getActivity());
        tableAssist.setBackgroundResource(R.drawable.table_border);
        tableAssist.setPadding(15, 15, 15, 15);

        tableBlock = new TextView(getActivity());
        tableBlock.setBackgroundResource(R.drawable.table_border);
        tableBlock.setPadding(15, 15, 15, 15);

        tableSteals = new TextView(getActivity());
        tableSteals.setBackgroundResource(R.drawable.table_border);
        tableSteals.setPadding(15, 15, 15, 15);

        tableTurnov = new TextView(getActivity());
        tableTurnov.setBackgroundResource(R.drawable.table_border);
        tableTurnov.setPadding(15, 15, 15, 15);

        tableFoul = new TextView(getActivity());
        tableFoul.setBackgroundResource(R.drawable.table_border);
        tableFoul.setPadding(15, 15, 15, 15);

        tablePts = new TextView(getActivity());
        tablePts.setBackgroundResource(R.drawable.table_border);
        tablePts.setPadding(15, 15, 15, 15);

        tableEff = new TextView(getActivity());
        tableEff.setBackgroundResource(R.drawable.table_border);
        tableEff.setPadding(15, 15, 15, 15);

        tableNum.setText(R.string.no);
        tableName.setText(R.string.PLAYER);
        tableTime.setText(R.string.Time);
        tableFG.setText(R.string.FG);
        table2pts.setText(R.string.pts2);
        table3pts.setText(R.string.pts3);
        table1pts.setText(R.string.FT);
        tableOffReb.setText(R.string.OREB);
        tableDefReb.setText(R.string.DREB);
        tableTotalReb.setText(R.string.REB);
        tableAssist.setText(R.string.AST);
        tableBlock.setText(R.string.BLK);
        tableSteals.setText(R.string.STL);
        tableTurnov.setText(R.string.TOV);
        tableFoul.setText(R.string.PF);
        tablePts.setText(R.string.PTS);
        tableEff.setText(R.string.EFF);


        row.addView(tableNum);
        row.addView(tableName);
        row.addView(tableTime);
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


        table.addView(row, 0);

        for (int i = 0; i < playersList.size(); i++) {

            TableRow row2 = new TableRow(getActivity());
            row2.setLayoutParams(lp);
            row2.setBackgroundResource(R.drawable.table_border);

            number = new TextView(getActivity());
            number.setBackgroundResource(R.drawable.table_border);
            number.setPadding(15, 15, 15, 15);

            name = new TextView(getActivity());
            name.setBackgroundResource(R.drawable.table_border);
            name.setPadding(15, 15, 15, 15);

            time = new TextView(getActivity());
            time.setBackgroundResource(R.drawable.table_border);
            time.setPadding(15, 15, 15, 15);

            fg = new TextView(getActivity());
            fg.setBackgroundResource(R.drawable.table_border);
            fg.setPadding(15, 15, 15, 15);

            pts2 = new TextView(getActivity());
            pts2.setBackgroundResource(R.drawable.table_border);
            pts2.setPadding(15, 15, 15, 15);

            pts3 = new TextView(getActivity());
            pts3.setBackgroundResource(R.drawable.table_border);
            pts3.setPadding(15, 15, 15, 15);

            pts1 = new TextView(getActivity());
            pts1.setBackgroundResource(R.drawable.table_border);
            pts1.setPadding(15, 15, 15, 15);

            offReb = new TextView(getActivity());
            offReb.setBackgroundResource(R.drawable.table_border);
            offReb.setPadding(15, 15, 15, 15);

            defReb = new TextView(getActivity());
            defReb.setBackgroundResource(R.drawable.table_border);
            defReb.setPadding(15, 15, 15, 15);

            totalReb = new TextView(getActivity());
            totalReb.setBackgroundResource(R.drawable.table_border);
            totalReb.setPadding(15, 15, 15, 15);

            assist = new TextView(getActivity());
            assist.setBackgroundResource(R.drawable.table_border);
            assist.setPadding(15, 15, 15, 15);

            block = new TextView(getActivity());
            block.setBackgroundResource(R.drawable.table_border);
            block.setPadding(15, 15, 15, 15);

            steal = new TextView(getActivity());
            steal.setBackgroundResource(R.drawable.table_border);
            steal.setPadding(15, 15, 15, 15);

            turnov = new TextView(getActivity());
            turnov.setBackgroundResource(R.drawable.table_border);
            turnov.setPadding(15, 15, 15, 15);

            foul = new TextView(getActivity());
            foul.setBackgroundResource(R.drawable.table_border);
            foul.setPadding(15, 15, 15, 15);

            pts = new TextView(getActivity());
            pts.setBackgroundResource(R.drawable.table_border);
            pts.setPadding(15, 15, 15, 15);

            eff = new TextView(getActivity());
            eff.setBackgroundResource(R.drawable.table_border);
            eff.setPadding(15, 15, 15, 15);

            number.setText(playersList.get(i).getNumber());
            name.setText(playersList.get(i).getNameAndLastname());

            int minutes = (playersList.get(i).getMinutes()) / 60;
            int seconds = (playersList.get(i).getMinutes()) % 60;
            String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
            time.setText(timeLeftFormatted);

            String fgm = String.valueOf(playersList.get(i).getPm2() + playersList.get(i).getPm3());
            String fga = String.valueOf(playersList.get(i).getPa2() + playersList.get(i).getPa3());
            String totalFG = fgm + "/" + fga;
            fg.setText(totalFG);

            String p2 = playersList.get(i).getPm2() + "/" + playersList.get(i).getPa2();
            pts2.setText(p2);

            String p3 = playersList.get(i).getPm3() + "/" + playersList.get(i).getPa3();
            pts3.setText(p3);

            String p1 = playersList.get(i).getPm1() + "/" + playersList.get(i).getPa1();
            pts1.setText(p1);

            offReb.setText(String.valueOf(playersList.get(i).getOffReb()));
            defReb.setText(String.valueOf(playersList.get(i).getDefReb()));
            String tReb = String.valueOf(playersList.get(i).getOffReb() + playersList.get(i).getDefReb());
            totalReb.setText(tReb);
            assist.setText(String.valueOf(playersList.get(i).getAsist()));
            block.setText(String.valueOf(playersList.get(i).getBlock()));
            steal.setText(String.valueOf(playersList.get(i).getSteal()));
            turnov.setText(String.valueOf(playersList.get(i).getTurnover()));
            foul.setText(String.valueOf(playersList.get(i).getFoul()));

            String totalPoints = String.valueOf((playersList.get(i).getPm2() * 2)
                    + (playersList.get(i).getPm3() * 3) + playersList.get(i).getPm1());
            pts.setText(totalPoints);

            String index = String.valueOf(((playersList.get(i).getPm2() * 2)
                    + (playersList.get(i).getPm3() * 3)
                    + playersList.get(i).getPm1())
                    + (playersList.get(i).getOffReb() + playersList.get(i).getDefReb())
                    + playersList.get(i).getAsist() + playersList.get(i).getBlock()
                    + playersList.get(i).getSteal() - playersList.get(i).getTurnover()
                    - playersList.get(i).getFoul() - (playersList.get(i).getPa2() - playersList.get(i).getPm2())
                    - (playersList.get(i).getPa3() - playersList.get(i).getPm3())
                    - (playersList.get(i).getPa1() - playersList.get(i).getPm1()));
            eff.setText(index);

            teamTime += playersList.get(i).getMinutes();
            teamFGM += (playersList.get(i).getPm2() + playersList.get(i).getPm3());
            teamFGA += (playersList.get(i).getPa2() + playersList.get(i).getPa3());
            team2PTSpm += playersList.get(i).getPm2();
            team2PTSpa += playersList.get(i).getPa2();
            team3PTSpm += playersList.get(i).getPm3();
            team3PTSpa += playersList.get(i).getPa3();
            team1PTSpm += playersList.get(i).getPm1();
            team1PTSpa += playersList.get(i).getPa1();
            teamOffReb += playersList.get(i).getOffReb();
            teamDefReb += playersList.get(i).getDefReb();
            teamReb += (playersList.get(i).getOffReb() + playersList.get(i).getDefReb());
            teamAssist += playersList.get(i).getAsist();
            teamBlocks += playersList.get(i).getBlock();
            teamSteals += playersList.get(i).getSteal();
            teamTurnovers += playersList.get(i).getTurnover();
            teamFouls += playersList.get(i).getFoul();
            teamPoints += ((playersList.get(i).getPm2() * 2)
                    + (playersList.get(i).getPm3() * 3) + playersList.get(i).getPm1());
            teamEff += ((playersList.get(i).getPm2() * 2)
                    + (playersList.get(i).getPm3() * 3)
                    + playersList.get(i).getPm1())
                    + (playersList.get(i).getOffReb() + playersList.get(i).getDefReb())
                    + playersList.get(i).getAsist() + playersList.get(i).getBlock()
                    + playersList.get(i).getSteal() - playersList.get(i).getTurnover()
                    - playersList.get(i).getFoul() - (playersList.get(i).getPa2() - playersList.get(i).getPm2())
                    - (playersList.get(i).getPa3() - playersList.get(i).getPm3())
                    - (playersList.get(i).getPa1() - playersList.get(i).getPm1());

            row2.addView(number);
            row2.addView(name);
            row2.addView(time);
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

            table.addView(row2, i + 1);
        }

        TableRow row3 = new TableRow(getActivity());
        row3.setLayoutParams(lp);
        row3.setBackgroundResource(R.drawable.table_border);

        teamTV = new TextView(getActivity());
        teamTV.setBackgroundResource(R.drawable.table_border);
        teamTV.setPadding(15, 15, 15, 15);

        teamTitle = new TextView(getActivity());
        teamTitle.setBackgroundResource(R.drawable.table_border);
        teamTitle.setPadding(15, 15, 15, 15);

        teamTimeTV = new TextView(getActivity());
        teamTimeTV.setBackgroundResource(R.drawable.table_border);
        teamTimeTV.setPadding(15, 15, 15, 15);

        teamFGtv = new TextView(getActivity());
        teamFGtv.setBackgroundResource(R.drawable.table_border);
        teamFGtv.setPadding(15, 15, 15, 15);

        team2ptsTV = new TextView(getActivity());
        team2ptsTV.setBackgroundResource(R.drawable.table_border);
        team2ptsTV.setPadding(15, 15, 15, 15);

        team3ptsTV = new TextView(getActivity());
        team3ptsTV.setBackgroundResource(R.drawable.table_border);
        team3ptsTV.setPadding(15, 15, 15, 15);

        team1ptsTV = new TextView(getActivity());
        team1ptsTV.setBackgroundResource(R.drawable.table_border);
        team1ptsTV.setPadding(15, 15, 15, 15);

        teamOffRebTV = new TextView(getActivity());
        teamOffRebTV.setBackgroundResource(R.drawable.table_border);
        teamOffRebTV.setPadding(15, 15, 15, 15);

        teamDefRebTV = new TextView(getActivity());
        teamDefRebTV.setBackgroundResource(R.drawable.table_border);
        teamDefRebTV.setPadding(15, 15, 15, 15);

        teamRebTV = new TextView(getActivity());
        teamRebTV.setBackgroundResource(R.drawable.table_border);
        teamRebTV.setPadding(15, 15, 15, 15);

        teamAssistTV = new TextView(getActivity());
        teamAssistTV.setBackgroundResource(R.drawable.table_border);
        teamAssistTV.setPadding(15, 15, 15, 15);

        teamBlocksTV = new TextView(getActivity());
        teamBlocksTV.setBackgroundResource(R.drawable.table_border);
        teamBlocksTV.setPadding(15, 15, 15, 15);

        teamStealsTV = new TextView(getActivity());
        teamStealsTV.setBackgroundResource(R.drawable.table_border);
        teamStealsTV.setPadding(15, 15, 15, 15);

        teamTurnTV = new TextView(getActivity());
        teamTurnTV.setBackgroundResource(R.drawable.table_border);
        teamTurnTV.setPadding(15, 15, 15, 15);

        teamFoulsTV = new TextView(getActivity());
        teamFoulsTV.setBackgroundResource(R.drawable.table_border);
        teamFoulsTV.setPadding(15, 15, 15, 15);

        teamPointsTV = new TextView(getActivity());
        teamPointsTV.setBackgroundResource(R.drawable.table_border);
        teamPointsTV.setPadding(15, 15, 15, 15);

        teamEffTV = new TextView(getActivity());
        teamEffTV.setBackgroundResource(R.drawable.table_border);
        teamEffTV.setPadding(15, 15, 15, 15);

        teamTV.setText(R.string.taraba);
        teamTitle.setText(R.string.total);
        teamTimeTV.setText(String.valueOf(teamTime));
        teamFGtv.setText(teamFGM + R.string.kosa_crta + teamFGA);
        team2ptsTV.setText(team2PTSpm + R.string.kosa_crta + team2PTSpa);
        team3ptsTV.setText(team3PTSpm + R.string.kosa_crta + team3PTSpa);
        team1ptsTV.setText(team1PTSpm + R.string.kosa_crta + team1PTSpa);
        teamOffRebTV.setText(String.valueOf(teamOffReb));
        teamDefRebTV.setText(String.valueOf(teamDefReb));
        teamRebTV.setText(String.valueOf(teamReb));
        teamAssistTV.setText(String.valueOf(teamAssist));
        teamBlocksTV.setText(String.valueOf(teamBlocks));
        teamStealsTV.setText(String.valueOf(teamSteals));
        teamTurnTV.setText(String.valueOf(teamTurnovers));
        teamFoulsTV.setText(String.valueOf(teamFouls));
        teamPointsTV.setText(String.valueOf(teamPoints));
        teamEffTV.setText(String.valueOf(teamEff));

        row3.addView(teamTV);
        row3.addView(teamTitle);
        row3.addView(teamTimeTV);
        row3.addView(teamFGtv);
        row3.addView(team2ptsTV);
        row3.addView(team3ptsTV);
        row3.addView(team1ptsTV);
        row3.addView(teamOffRebTV);
        row3.addView(teamDefRebTV);
        row3.addView(teamRebTV);
        row3.addView(teamAssistTV);
        row3.addView(teamBlocksTV);
        row3.addView(teamStealsTV);
        row3.addView(teamTurnTV);
        row3.addView(teamFoulsTV);
        row3.addView(teamPointsTV);
        row3.addView(teamEffTV);

        table.addView(row3);
    }
}
