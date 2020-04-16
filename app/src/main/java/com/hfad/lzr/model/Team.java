package com.hfad.lzr.model;

import java.io.Serializable;
import java.util.List;

public class Team implements Serializable {

    private String id;
    private String name;
    private String league;
    private int played;
    private int win;
    private int lost;
    private int pointsScored;
    private int pointsReceived;
    private int points;


    public Team() {
    }

    public Team(String id, String name, String league, int played, int win, int lost, int pointsScored, int pointsReceived, int points) {
        this.id = id;
        this.name = name;
        this.league = league;
        this.played = played;
        this.win = win;
        this.lost = lost;
        this.pointsScored = pointsScored;
        this.pointsReceived = pointsReceived;
        this.points = points;
    }

    public Team(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Team(String name, int pointsScored, int pointsReceived) {
        this.name = name;
        this.pointsScored = pointsScored;
        this.pointsReceived = pointsReceived;
    }

    public Team(String id, String name, String league) {
        this.id = id;
        this.name = name;
        this.league = league;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPointsScored() {
        return pointsScored;
    }

    public void setPointsScored(int pointsScored) {
        this.pointsScored = pointsScored;
    }

    public int getPointsReceived() {
        return pointsReceived;
    }

    public void setPointsReceived(int pointsReceived) {
        this.pointsReceived = pointsReceived;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }
}
