package com.hfad.lzr.model;

import java.io.Serializable;

public class Game implements Serializable {
    private String id;
    private String idTeamA;
    private String idTeamB;
    private String gameDate;
    private String gameTime;
    private int resA;
    private int resB;
    private String teamAnaziv;
    private String teamBnaziv;


    public Game() {
    }

    public Game(String id, String idTeamA, String idTeamB, String gameDate, String gameTime, String teamAnaziv, String teamBnaziv) {
        this.id = id;
        this.idTeamA = idTeamA;
        this.idTeamB = idTeamB;
        this.gameDate = gameDate;
        this.gameTime = gameTime;
        this.teamAnaziv = teamAnaziv;
        this.teamBnaziv = teamBnaziv;
    }

    public int getResA() {
        return resA;
    }

    public void setResA(int resA) {
        this.resA = resA;
    }

    public int getResB() {
        return resB;
    }

    public void setResB(int resB) {
        this.resB = resB;
    }

    public String getTeamAnaziv() {
        return teamAnaziv;
    }

    public void setTeamAnaziv(String teamAnaziv) {
        this.teamAnaziv = teamAnaziv;
    }

    public String getTeamBnaziv() {
        return teamBnaziv;
    }

    public void setTeamBnaziv(String teamBnaziv) {
        this.teamBnaziv = teamBnaziv;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdTeamA() {
        return idTeamA;
    }

    public void setIdTeamA(String idTeamA) {
        this.idTeamA = idTeamA;
    }

    public String getIdTeamB() {
        return idTeamB;
    }

    public void setIdTeamB(String idTeamB) {
        this.idTeamB = idTeamB;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public String getGameTime() {
        return gameTime;
    }

    public void setGameTime(String gameTime) {
        this.gameTime = gameTime;
    }
}
