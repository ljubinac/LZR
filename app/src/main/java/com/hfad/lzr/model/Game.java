package com.hfad.lzr.model;

public class Game {
    private String date;
    private String teamAid;
    private String teamBid;
    private String id;
    private String time;

    public Game() {
    }

    public Game(String date, String teamAid, String teamBid, String id, String time) {
        this.date = date;
        this.teamAid = teamAid;
        this.teamBid = teamBid;
        this.id = id;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTeamAid() {
        return teamAid;
    }

    public void setTeamAid(String teamAid) {
        this.teamAid = teamAid;
    }

    public String getTeamBid() {
        return teamBid;
    }

    public void setTeamBid(String teamBid) {
        this.teamBid = teamBid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
