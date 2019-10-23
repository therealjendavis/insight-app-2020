package com.example.rumbleapp2020;

import java.io.Serializable;

public class Info implements Serializable {
    String name;
    Integer team;
    String match;
    String alliance;
    String notes;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getTeam() {
        return team;
    }
    public void setTeam(Integer team) {
        this.team = team;
    }
    public String getMatch() {
        return match;
    }
    public void setMatch(String match) {
        this.match = match;
    }
    public String getAlliance() {
        return alliance;
    }
    public void setAlliance(String alliance) {
        this.alliance = alliance;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }




}
