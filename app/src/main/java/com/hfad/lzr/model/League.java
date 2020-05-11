package com.hfad.lzr.model;

public class League {

    private String id;
    private String name;

    public League() {
    }

    public League(String name) {
        this.name = name;
    }

    public League(String id, String name) {
        this.id = id;
        this.name = name;
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
}
