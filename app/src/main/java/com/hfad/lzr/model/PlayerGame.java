package com.hfad.lzr.model;

import java.io.Serializable;
import java.util.Objects;

public class PlayerGame extends Player implements Serializable {

    private String gameId;
    /*private int pointsTeamA;
    private int pointsTeamB;*/
    private int pm2;
    private int pa2;
    private int pm3;
    private int pa3;
    private int pm1;
    private int pa1;
    private int offReb;
    private int defReb;
    private int asist;
    private int turnover;
    private int block;
    private int foul;
    private int steal;
    private int tehnicalFoul;
    private int minutes;
    private int whenGoingIn;

    public PlayerGame(String gameId ,Player player) {
        super(player);
        this.gameId = gameId;
       /* this.pointsTeamA = pointsTeamA;
        this.pointsTeamB = pointsTeamB;*/
        this.pm2 = 0;
        this.pa2 = 0;
        this.pm3 =0;
        this.pa3 = 0;
        this.pm1 = 0;
        this.pa1 = 0;
        this.offReb = 0;
        this.defReb = 0;
        this.asist = 0;
        this.turnover = 0;
        this.block = 0;
        this.foul = 0;
        this.steal = 0;
        this.minutes = 0;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

  /*  public int getPointsTeamA() {
        return pointsTeamA;
    }

    public void setPointsTeamA(int pointsTeamA) {
        this.pointsTeamA = pointsTeamA;
    }

    public int getPointsTeamB() {
        return pointsTeamB;
    }

    public void setPointsTeamB(int pointsTeamB) {
        this.pointsTeamB = pointsTeamB;
    }*/

  /*  @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerGame that = ( PlayerGame ) o;
        return gameId.equals(that.gameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId);
    }*/

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getWhenGoingIn() {
        return whenGoingIn;
    }

    public void setWhenGoingIn(int whenGoingIn) {
        this.whenGoingIn = whenGoingIn;
    }

    public int getSteal() {
        return steal;
    }

    public void setSteal(int steal) {
        this.steal = steal;
    }

    public int getTehnicalFoul() {
        return tehnicalFoul;
    }

    public void setTehnicalFoul(int tehnicalFoul) {
        this.tehnicalFoul = tehnicalFoul;
    }

    public int getPm2() {
        return pm2;
    }

    public void setPm2(int pm2) {
        this.pm2 = pm2;
    }

    public int getPa2() {
        return pa2;
    }

    public void setPa2(int pa2) {
        this.pa2 = pa2;
    }

    public int getPm3() {
        return pm3;
    }

    public void setPm3(int pm3) {
        this.pm3 = pm3;
    }

    public int getPa3() {
        return pa3;
    }

    public void setPa3(int pa3) {
        this.pa3 = pa3;
    }

    public int getPm1() {
        return pm1;
    }

    public void setPm1(int pm1) {
        this.pm1 = pm1;
    }

    public int getPa1() {
        return pa1;
    }

    public void setPa1(int pa1) {
        this.pa1 = pa1;
    }

    public int getOffReb() {
        return offReb;
    }

    public void setOffReb(int offReb) {
        this.offReb = offReb;
    }

    public int getDefReb() {
        return defReb;
    }

    public void setDefReb(int defReb) {
        this.defReb = defReb;
    }

    public int getAsist() {
        return asist;
    }

    public void setAsist(int asist) {
        this.asist = asist;
    }

    public int getTurnover() {
        return turnover;
    }

    public void setTurnover(int turnover) {
        this.turnover = turnover;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getFoul() {
        return foul;
    }

    public void setFoul(int foul) {
        this.foul = foul;
    }
}
