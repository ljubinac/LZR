package com.hfad.lzr.ui.main;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.hfad.lzr.R;
import com.hfad.lzr.model.Game;
import com.hfad.lzr.model.PlayerGame;

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

        // Ovo je jedna opcija da se oboji red, druga je da se promeni boja u table_border.xml
        //u xml_u mogu da se postave borderi a ovako je bez
//        row.setBackgroundColor(getContext().getResources().getColor(R.color.orange_end));

        tableNum = new TextView(getActivity());
        tableNum.setPadding(15, 15, 15, 15);
        tableNum.setTextColor(getResources().getColor(R.color.colorPrimary));
        tableNum.setTypeface(null, Typeface.BOLD);
        tableNum.setGravity(Gravity.RIGHT);

        tableName = new TextView(getActivity());
        tableName.setPadding(15, 15, 15, 15);
        tableName.setTextColor(getResources().getColor(R.color.colorPrimary));
        tableName.setTypeface(null, Typeface.BOLD);
        tableName.setGravity(Gravity.LEFT);

        tableTime = new TextView(getActivity());
        tableTime.setPadding(15, 15, 15, 15);
        tableTime.setTextColor(getResources().getColor(R.color.colorPrimary));
        tableTime.setTypeface(null, Typeface.BOLD);
        tableTime.setGravity(Gravity.CENTER);

        tableFG = new TextView(getActivity());
        tableFG.setPadding(15, 15, 15, 15);
        tableFG.setTextColor(getResources().getColor(R.color.colorPrimary));
        tableFG.setTypeface(null, Typeface.BOLD);
        tableFG.setGravity(Gravity.CENTER);

        table2pts = new TextView(getActivity());
        table2pts.setPadding(15, 15, 15, 15);
        table2pts.setTextColor(getResources().getColor(R.color.colorPrimary));
        table2pts.setTypeface(null, Typeface.BOLD);
        table2pts.setGravity(Gravity.CENTER);

        table3pts = new TextView(getActivity());
        table3pts.setPadding(15, 15, 15, 15);
        table3pts.setTextColor(getResources().getColor(R.color.colorPrimary));
        table3pts.setTypeface(null, Typeface.BOLD);
        table3pts.setGravity(Gravity.CENTER);

        table1pts = new TextView(getActivity());
        table1pts.setPadding(15, 15, 15, 15);
        table1pts.setTextColor(getResources().getColor(R.color.colorPrimary));
        table1pts.setTypeface(null, Typeface.BOLD);
        table1pts.setGravity(Gravity.CENTER);

        tableTotalReb = new TextView(getActivity());
        tableTotalReb.setPadding(15, 15, 15, 15);
        tableTotalReb.setTextColor(getResources().getColor(R.color.colorPrimary));
        tableTotalReb.setTypeface(null, Typeface.BOLD);
        tableTotalReb.setGravity(Gravity.CENTER);

        tableDefReb = new TextView(getActivity());
        tableDefReb.setPadding(15, 15, 15, 15);
        tableDefReb.setTextColor(getResources().getColor(R.color.colorPrimary));
        tableDefReb.setTypeface(null, Typeface.BOLD);
        tableDefReb.setGravity(Gravity.CENTER);

        tableOffReb = new TextView(getActivity());
        tableOffReb.setPadding(15, 15, 15, 15);
        tableOffReb.setTextColor(getResources().getColor(R.color.colorPrimary));
        tableOffReb.setTypeface(null, Typeface.BOLD);
        tableOffReb.setGravity(Gravity.CENTER);

        tableAssist = new TextView(getActivity());
        tableAssist.setPadding(15, 15, 15, 15);
        tableAssist.setTextColor(getResources().getColor(R.color.colorPrimary));
        tableAssist.setTypeface(null, Typeface.BOLD);
        tableAssist.setGravity(Gravity.CENTER);

        tableBlock = new TextView(getActivity());
        tableBlock.setPadding(15, 15, 15, 15);
        tableBlock.setTextColor(getResources().getColor(R.color.colorPrimary));
        tableBlock.setTypeface(null, Typeface.BOLD);
        tableBlock.setGravity(Gravity.CENTER);

        tableSteals = new TextView(getActivity());
        tableSteals.setPadding(15, 15, 15, 15);
        tableSteals.setTextColor(getResources().getColor(R.color.colorPrimary));
        tableSteals.setTypeface(null, Typeface.BOLD);
        tableSteals.setGravity(Gravity.CENTER);

        tableTurnov = new TextView(getActivity());
        tableTurnov.setPadding(15, 15, 15, 15);
        tableTurnov.setTextColor(getResources().getColor(R.color.colorPrimary));
        tableTurnov.setTypeface(null, Typeface.BOLD);
        tableTurnov.setGravity(Gravity.CENTER);

        tableFoul = new TextView(getActivity());
        tableFoul.setPadding(15, 15, 15, 15);
        tableFoul.setTextColor(getResources().getColor(R.color.colorPrimary));
        tableFoul.setTypeface(null, Typeface.BOLD);
        tableFoul.setGravity(Gravity.CENTER);

        tablePts = new TextView(getActivity());
        tablePts.setPadding(15, 15, 15, 15);
        tablePts.setTextColor(getResources().getColor(R.color.colorPrimary));
        tablePts.setTypeface(null, Typeface.BOLD);
        tablePts.setGravity(Gravity.CENTER);

        tableEff = new TextView(getActivity());
        tableEff.setPadding(15, 15, 15, 15);
        tableEff.setTextColor(getResources().getColor(R.color.colorPrimary));
        tableEff.setTypeface(null, Typeface.BOLD);
        tableEff.setGravity(Gravity.CENTER);

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

            row2.setBackgroundResource(R.drawable.game_item_upper);

            number = new TextView(getActivity());
            number.setPadding(15, 15, 15, 15);
            number.setGravity(Gravity.RIGHT);
            number.setTextColor(getResources().getColor(R.color.orange_end));
            number.setTypeface(null, Typeface.BOLD);

            name = new TextView(getActivity());
            name.setPadding(15, 15, 15, 15);
            name.setGravity(Gravity.LEFT);
            name.setTextColor(getResources().getColor(R.color.colorPrimary));
            name.setTypeface(null, Typeface.BOLD);

            time = new TextView(getActivity());
            time.setPadding(15, 15, 15, 15);
            time.setTextColor(getResources().getColor(R.color.colorPrimary));
            time.setGravity(Gravity.CENTER);

            fg = new TextView(getActivity());
            fg.setPadding(15, 15, 15, 15);
            fg.setTextColor(getResources().getColor(R.color.colorPrimary));
            fg.setGravity(Gravity.CENTER);

            pts2 = new TextView(getActivity());
            pts2.setPadding(15, 15, 15, 15);
            pts2.setTextColor(getResources().getColor(R.color.colorPrimary));
            pts2.setGravity(Gravity.CENTER);

            pts3 = new TextView(getActivity());
            pts3.setTextColor(getResources().getColor(R.color.colorPrimary));
            pts3.setPadding(15, 15, 15, 15);
            pts3.setGravity(Gravity.CENTER);

            pts1 = new TextView(getActivity());
            pts1.setTextColor(getResources().getColor(R.color.colorPrimary));
            pts1.setPadding(15, 15, 15, 15);
            pts1.setGravity(Gravity.CENTER);

            offReb = new TextView(getActivity());
            offReb.setTextColor(getResources().getColor(R.color.colorPrimary));
            offReb.setPadding(15, 15, 15, 15);
            offReb.setGravity(Gravity.CENTER);

            defReb = new TextView(getActivity());
            defReb.setTextColor(getResources().getColor(R.color.colorPrimary));
            defReb.setPadding(15, 15, 15, 15);
            defReb.setGravity(Gravity.CENTER);

            totalReb = new TextView(getActivity());
            totalReb.setTextColor(getResources().getColor(R.color.colorPrimary));
            totalReb.setPadding(15, 15, 15, 15);
            totalReb.setGravity(Gravity.CENTER);

            assist = new TextView(getActivity());
            assist.setTextColor(getResources().getColor(R.color.colorPrimary));
            assist.setPadding(15, 15, 15, 15);
            assist.setGravity(Gravity.CENTER);

            block = new TextView(getActivity());
            block.setTextColor(getResources().getColor(R.color.colorPrimary));
            block.setPadding(15, 15, 15, 15);
            block.setGravity(Gravity.CENTER);

            steal = new TextView(getActivity());
            steal.setTextColor(getResources().getColor(R.color.colorPrimary));
            steal.setPadding(15, 15, 15, 15);
            steal.setGravity(Gravity.CENTER);

            turnov = new TextView(getActivity());
            turnov.setTextColor(getResources().getColor(R.color.colorPrimary));
            turnov.setPadding(15, 15, 15, 15);
            turnov.setGravity(Gravity.CENTER);

            foul = new TextView(getActivity());
            foul.setTextColor(getResources().getColor(R.color.colorPrimary));
            foul.setPadding(15, 15, 15, 15);
            foul.setGravity(Gravity.CENTER);

            pts = new TextView(getActivity());
            pts.setTextColor(getResources().getColor(R.color.colorPrimary));
            pts.setPadding(15, 15, 15, 15);
            pts.setGravity(Gravity.CENTER);

            eff = new TextView(getActivity());
            eff.setTextColor(getResources().getColor(R.color.colorPrimary));
            eff.setPadding(15, 15, 15, 15);
            eff.setGravity(Gravity.CENTER);

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

            if (i % 2 == 0){
                row2.setBackgroundResource(R.drawable.game_item_upper);
            } else {
                row2.setBackgroundColor(getResources().getColor(R.color.white));
            }

            table.addView(row2, i + 1);
        }

        TableRow row3 = new TableRow(getActivity());
        row3.setLayoutParams(lp);

        /*teamTV = new TextView(getActivity());
        teamTV.setBackgroundResource(R.drawable.table_border);
        teamTV.setPadding(15, 15, 15, 15);
        teamTV.setGravity(Gravity.CENTER);
        teamTV.setTypeface(null, Typeface.BOLD);*/



        teamTitle = new TextView(getActivity());
        teamTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
        teamTitle.setPadding(15, 15, 15, 15);
        teamTitle.setGravity(Gravity.CENTER);
        teamTitle.setTypeface(null, Typeface.BOLD);
        TableRow.LayoutParams params = ( TableRow.LayoutParams ) row3.getLayoutParams();
        params.span = 2;
        teamTitle.setLayoutParams(params);

        teamTimeTV = new TextView(getActivity());
        teamTimeTV.setTextColor(getResources().getColor(R.color.colorPrimary));
        teamTimeTV.setPadding(15, 15, 15, 15);
        teamTimeTV.setGravity(Gravity.CENTER);
        teamTimeTV.setTypeface(null, Typeface.BOLD);

        teamFGtv = new TextView(getActivity());
        teamFGtv.setTextColor(getResources().getColor(R.color.colorPrimary));
        teamFGtv.setPadding(15, 15, 15, 15);
        teamFGtv.setGravity(Gravity.CENTER);
        teamFGtv.setTypeface(null, Typeface.BOLD);

        team2ptsTV = new TextView(getActivity());
        team2ptsTV.setTextColor(getResources().getColor(R.color.colorPrimary));
        team2ptsTV.setPadding(15, 15, 15, 15);
        team2ptsTV.setGravity(Gravity.CENTER);
        team2ptsTV.setTypeface(null, Typeface.BOLD);

        team3ptsTV = new TextView(getActivity());
        team3ptsTV.setTextColor(getResources().getColor(R.color.colorPrimary));
        team3ptsTV.setPadding(15, 15, 15, 15);
        team3ptsTV.setGravity(Gravity.CENTER);
        team3ptsTV.setTypeface(null, Typeface.BOLD);

        team1ptsTV = new TextView(getActivity());
        team1ptsTV.setTextColor(getResources().getColor(R.color.colorPrimary));
        team1ptsTV.setPadding(15, 15, 15, 15);
        team1ptsTV.setGravity(Gravity.CENTER);
        team1ptsTV.setTypeface(null, Typeface.BOLD);

        teamOffRebTV = new TextView(getActivity());
        teamOffRebTV.setTextColor(getResources().getColor(R.color.colorPrimary));
        teamOffRebTV.setPadding(15, 15, 15, 15);
        teamOffRebTV.setGravity(Gravity.CENTER);
        teamOffRebTV.setTypeface(null, Typeface.BOLD);

        teamDefRebTV = new TextView(getActivity());
        teamDefRebTV.setTextColor(getResources().getColor(R.color.colorPrimary));
        teamDefRebTV.setPadding(15, 15, 15, 15);
        teamDefRebTV.setGravity(Gravity.CENTER);
        teamDefRebTV.setTypeface(null, Typeface.BOLD);

        teamRebTV = new TextView(getActivity());
        teamRebTV.setTextColor(getResources().getColor(R.color.colorPrimary));
        teamRebTV.setPadding(15, 15, 15, 15);
        teamRebTV.setGravity(Gravity.CENTER);
        teamRebTV.setTypeface(null, Typeface.BOLD);

        teamAssistTV = new TextView(getActivity());
        teamAssistTV.setTextColor(getResources().getColor(R.color.colorPrimary));
        teamAssistTV.setPadding(15, 15, 15, 15);
        teamAssistTV.setGravity(Gravity.CENTER);
        teamAssistTV.setTypeface(null, Typeface.BOLD);

        teamBlocksTV = new TextView(getActivity());
        teamBlocksTV.setTextColor(getResources().getColor(R.color.colorPrimary));
        teamBlocksTV.setPadding(15, 15, 15, 15);
        teamBlocksTV.setGravity(Gravity.CENTER);
        teamBlocksTV.setTypeface(null, Typeface.BOLD);

        teamStealsTV = new TextView(getActivity());
        teamStealsTV.setTextColor(getResources().getColor(R.color.colorPrimary));
        teamStealsTV.setPadding(15, 15, 15, 15);
        teamStealsTV.setGravity(Gravity.CENTER);
        teamStealsTV.setTypeface(null, Typeface.BOLD);

        teamTurnTV = new TextView(getActivity());
        teamTurnTV.setTextColor(getResources().getColor(R.color.colorPrimary));
        teamTurnTV.setPadding(15, 15, 15, 15);
        teamTurnTV.setGravity(Gravity.CENTER);
        teamTurnTV.setTypeface(null, Typeface.BOLD);

        teamFoulsTV = new TextView(getActivity());
        teamFoulsTV.setTextColor(getResources().getColor(R.color.colorPrimary));
        teamFoulsTV.setPadding(15, 15, 15, 15);
        teamFoulsTV.setGravity(Gravity.CENTER);
        teamFoulsTV.setTypeface(null, Typeface.BOLD);

        teamPointsTV = new TextView(getActivity());
        teamPointsTV.setTextColor(getResources().getColor(R.color.colorPrimary));
        teamPointsTV.setPadding(15, 15, 15, 15);
        teamPointsTV.setGravity(Gravity.CENTER);
        teamPointsTV.setTypeface(null, Typeface.BOLD);

        teamEffTV = new TextView(getActivity());
        teamEffTV.setTextColor(getResources().getColor(R.color.colorPrimary));
        teamEffTV.setPadding(15, 15, 15, 15);
        teamEffTV.setGravity(Gravity.CENTER);
        teamEffTV.setTypeface(null, Typeface.BOLD);

//        teamTV.setText(R.string.taraba);
        teamTitle.setText(R.string.total);
        int minutes = teamTime / 60;
        int seconds = teamTime % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        teamTimeTV.setText(timeLeftFormatted);
        teamFGtv.setText(teamFGM + "/" + teamFGA);
        team2ptsTV.setText(team2PTSpm + "/" + team2PTSpa);
        team3ptsTV.setText(team3PTSpm + "/" + team3PTSpa);
        team1ptsTV.setText(team1PTSpm + "/" + team1PTSpa);
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

//        row3.addView(teamTV);
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
