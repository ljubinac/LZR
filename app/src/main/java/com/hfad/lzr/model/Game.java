package com.hfad.lzr.model;

import java.io.Serializable;

public class Game implements Serializable {
    private String id;
    private String idTeamA;
    private String idTeamB;
    private String gameDate;
    private String gameTime;
    private String gameArenaId;
    private String gameArenaName;
    private int resA;
    private int resB;
    private String teamAnaziv;
    private String teamBnaziv;
    private boolean isFinished;
    private boolean isExhibition;
    private int foulsTeamA;
    private int foulsTeamB;
    private int quarter;
    private String league;


    public Game() {
    }

    public Game(String id, String idTeamA, String idTeamB, String gameDate, String gameTime, String teamAnaziv, String teamBnaziv, boolean isFinished, boolean isExhibition, String gameArenaId, String gameArenaName, String league) {
        this.id = id;
        this.idTeamA = idTeamA;
        this.idTeamB = idTeamB;
        this.gameDate = gameDate;
        this.gameTime = gameTime;
        this.teamAnaziv = teamAnaziv;
        this.teamBnaziv = teamBnaziv;
        this.isFinished = isFinished;
        this.isExhibition = isExhibition;
        this.gameArenaId = gameArenaId;
        this.gameArenaName = gameArenaName;
        this.league = league;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getGameArenaId() {
        return gameArenaId;
    }

    public void setGameArenaId(String gameArenaId) {
        this.gameArenaId = gameArenaId;
    }

    public String getGameArenaName() {
        return gameArenaName;
    }

    public void setGameArenaName(String gameArenaName) {
        this.gameArenaName = gameArenaName;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public int getFoulsTeamA() {
        return foulsTeamA;
    }

    public void setFoulsTeamA(int foulsTeamA) {
        this.foulsTeamA = foulsTeamA;
    }

    public int getFoulsTeamB() {
        return foulsTeamB;
    }

    public void setFoulsTeamB(int foulsTeamB) {
        this.foulsTeamB = foulsTeamB;
    }

    public boolean isExhibition() {
        return isExhibition;
    }

    public void setExhibition(boolean exhibition) {
        isExhibition = exhibition;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
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
