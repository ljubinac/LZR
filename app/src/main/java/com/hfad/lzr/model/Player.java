package com.hfad.lzr.model;

import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Comparator;

public class Player implements Serializable, Comparable<Player> {

    private String id;
    private String nameAndLastname;
    private String number;
    private String teamId;
    private String league;
    private int totalPoints;
    private int totalRebs;
    private int totalAssists;
    private int totalSteals;
    private int totalBlocks;
    private int totalTurnovers;
    private int totalFouls;
    private int totalTehnical;
    private int totalMinutes;
    private int totalEff;

    public Player() {
    }

    public Player(String id, String nameAndLastname, String number, String teamId, String league) {
        this.id = id;
        this.nameAndLastname = nameAndLastname;
        this.number = number;
        this.teamId = teamId;
        this.league = league;
        this.totalPoints = 0;
        this.totalRebs = 0;
        this.totalAssists = 0;
        this.totalSteals = 0;
        this.totalBlocks = 0;
        this.totalFouls = 0;
        this.totalTurnovers = 0;
        this.totalTehnical = 0;
        this.totalEff = 0;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Player(Player player) {
        this.id = player.id;
        this.nameAndLastname = player.nameAndLastname;
        this.number = player.number;
        this.teamId = player.teamId;
        this.totalPoints = player.totalPoints;
        this.totalAssists = player.totalAssists;
        this.totalRebs = player.totalRebs;
        this.totalSteals = player.totalSteals;
        this.totalBlocks = player.totalBlocks;
        this.totalFouls = player.totalFouls;
        this.totalTurnovers = player.totalTurnovers;
        this.totalTehnical = player.totalTehnical;
        this.totalEff = player.totalEff;
    }

    public Player(String id, String nameAndLastname, String number) {
        this.id = id;
        this.nameAndLastname = nameAndLastname;
        this.number = number;
    }

    public Player(String name, String number) {
        this.nameAndLastname = name;
        this.number = number;
    }

    public void changeText1(String text, String text2){
        number = text;
        nameAndLastname = text2;
    }

    public int getTotalTurnovers() {
        return totalTurnovers;
    }

    public void setTotalTurnovers(int totalTurnovers) {
        this.totalTurnovers = totalTurnovers;
    }

    public int getTotalFouls() {
        return totalFouls;
    }

    public void setTotalFouls(int totalFouls) {
        this.totalFouls = totalFouls;
    }

    public int getTotalTehnical() {
        return totalTehnical;
    }

    public void setTotalTehnical(int totalTehnical) {
        this.totalTehnical = totalTehnical;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(int totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public int getTotalRebs() {
        return totalRebs;
    }

    public void setTotalRebs(int totalRebs) {
        this.totalRebs = totalRebs;
    }

    public int getTotalAssists() {
        return totalAssists;
    }

    public void setTotalAssists(int totalAssists) {
        this.totalAssists = totalAssists;
    }

    public int getTotalSteals() {
        return totalSteals;
    }

    public void setTotalSteals(int totalSteals) {
        this.totalSteals = totalSteals;
    }

    public int getTotalBlocks() {
        return totalBlocks;
    }

    public void setTotalBlocks(int totalBlocks) {
        this.totalBlocks = totalBlocks;
    }

    public int getTotalEff() {
        return totalEff;
    }

    public void setTotalEff(int totalEff) {
        this.totalEff = totalEff;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameAndLastname() {
        return nameAndLastname;
    }

    public void setNameAndLastname(String nameAndLastname) {
        this.nameAndLastname = nameAndLastname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public int compareTo(Player p) {
        if(totalPoints > p.totalPoints) {
            return  -1;
        } else if (totalPoints < p.totalPoints){
            return 1;
        } else {
            return 0;
        }
    }
}
