package com.hfad.lzr.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Game {
    private String id;
    private String idTeamA;
    private String idTeamB;
    private String gameDate;
    private String gameTime;

    public Game() {
    }

    public Game(String id, String idTeamA, String idTeamB, String gameDate, String gameTime) {
        this.id = id;
        this.idTeamA = idTeamA;
        this.idTeamB = idTeamB;
        this.gameDate = gameDate;
        this.gameTime = gameTime;
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
