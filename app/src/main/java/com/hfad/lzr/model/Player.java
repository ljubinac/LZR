package com.hfad.lzr.model;

import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

public class Player implements Serializable {

    private String id;
    private String nameAndLastname;
    private String number;
    private String teamId;
    private String league;
    private int totalPoints;

    public Player() {
    }

    public Player(String id, String nameAndLastname, String number, String teamId, String league) {
        this.id = id;
        this.nameAndLastname = nameAndLastname;
        this.number = number;
        this.teamId = teamId;
        this.league = league;
        this.totalPoints = 0;
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
}
