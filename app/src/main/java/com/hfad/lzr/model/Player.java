package com.hfad.lzr.model;

public class Player {

    private String id;
    private String nameAndLastname;
    private String number;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    private String teamId;

    public Player() {
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

    public void changeText1(String text){
        nameAndLastname = text;
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
