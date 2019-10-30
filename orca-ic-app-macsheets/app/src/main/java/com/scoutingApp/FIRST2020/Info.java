package com.scoutingApp.FIRST2020;

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
    Integer getTeam() {
        return team;
    }
    void setTeam(Integer team) {
        this.team = team;
    }
    String getMatch() {
        return match;
    }
    void setMatch(String match) {
        this.match = match;
    }
    String getAlliance() {
        return alliance;
    }
    void setAlliance(String alliance) {
        this.alliance = alliance;
    }
    String getNotes() {
        return notes;
    }
    void setNotes(String notes) {
        this.notes = notes;
    }




}
