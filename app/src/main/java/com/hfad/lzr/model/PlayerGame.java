package com.hfad.lzr.model;

import java.io.Serializable;

public class PlayerGame extends Player implements Serializable {
    private int m2pM;
    private int m2pA;
    private String gameId;

    public PlayerGame(Player player, String gameId) {
        super(player);
        this.m2pM = 0;
        this.m2pA = 0;
        this.gameId = gameId;
    }

    public int getM2pM() {
        return m2pM;
    }

    public void setM2pM(int m2pM) {
        this.m2pM = m2pM;
    }

    public int getM2pA() {
        return m2pA;
    }

    public void setM2pA(int m2pA) {
        this.m2pA = m2pA;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
