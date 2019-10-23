package com.example.rumbleapp2020;

import java.io.Serializable;

public class SubmittedData implements Serializable {
    
    private int mainStartPosition;
    private boolean mainDefense;
    private int mainBlockedScores;
    private int mainEndgame;

    private boolean extrasRedCard;
    private boolean extrasYellowCard;
    private boolean noShow;
    private boolean movement;
    private int extrasFinalScore;

    private int team;
    private String match;
    private String name;
    private String alliance;
    private String notes;

    private int CSFH;
    private int CSFC;
    private int CSSH;
    private int CSSC;
    private int CSFHSS;
    private int CSFCSS;
    private int CSSHSS;
    private int CSSCSS;

    private int R1H;
    private int R1C;
    private int R2H;
    private int R2C;
    private int R3H;
    private int R3C;
    private int R1HSS;
    private int R1CSS;
    private int R2HSS;
    private int R2CSS;
    private int R3HSS;
    private int R3CSS;
    
    private int totalSS = CSFHSS + CSFCSS + CSSCSS + CSSHSS + R1HSS + R1CSS + R2HSS + R2CSS + R3CSS + R3HSS;
    private int cargoShipTotal = CSFH + CSFC + CSSH + CSSC + CSFHSS + CSFCSS + CSSHSS + CSSCSS;
    private int rocketShipTotal = R1H + R1C + R2H + R2C + R3H + R3C + R1HSS + R1CSS + R2HSS + R2CSS + R3HSS + R3CSS;
    private int totalCargo = CSFC + CSSC + CSFCSS + CSSCSS + R1C + R2C + R3C + R1CSS + R2CSS + R3CSS;
    private int totalHatch = CSFH + CSSH + CSFHSS + CSSHSS + R1H + R2H + R3H + R1HSS + R2HSS + R3HSS;

    public void setNotes(String notes) {this.notes = notes;}
    public void setMainStartPosition(int mainStartPosition) {this.mainStartPosition = mainStartPosition;}
    public void setMainDefense(boolean mainDefense) {this.mainDefense = mainDefense;}
    public void setMainBlockedScores(int mainBlockedScores) {this.mainBlockedScores = mainBlockedScores;}
    public void setMainEndgame(int mainEndgame) {this.mainEndgame = mainEndgame;}
    public void setExtrasRedCard(boolean extrasRedCard) {this.extrasRedCard = extrasRedCard;}
    public void setExtrasYellowCard(boolean extrasYellowCard) {this.extrasYellowCard = extrasYellowCard;}
    public void setNoShow(boolean noShow) {this.noShow = noShow;}
    public void setMovement(boolean movement) {this.movement = movement;}
    public void setExtrasFinalScore(int extrasFinalScore) {this.extrasFinalScore = extrasFinalScore;}
    public void setTeam(int team) {this.team = team;}
    public void setMatch(String match) {this.match = match;}
    public void setName(String name) {this.name = name;}
    public void setAlliance(String alliance) {this.alliance = alliance;}
    public void setCSFH(int CSFH) {this.CSFH = CSFH;}
    public void setCSFC(int CSFC) {this.CSFC = CSFC;}
    public void setCSSH(int CSSH) {this.CSSH = CSSH;}
    public void setCSSC(int CSSC) {this.CSSC = CSSC;}
    public void setCSFHSS(int CSFHSS) {this.CSFHSS = CSFHSS;}
    public void setCSFCSS(int CSFCSS) {this.CSFCSS = CSFCSS;}
    public void setCSSHSS(int CSSHSS) {this.CSSHSS = CSSHSS;}
    public void setCSSCSS(int CSSCSS) {this.CSSCSS = CSSCSS;}
    public void setR1H(int r1H) {R1H = r1H;}
    public void setR1C(int r1C) {R1C = r1C;}
    public void setR2H(int r2H) {R2H = r2H;}
    public void setR2C(int r2C) {R2C = r2C;}
    public void setR3H(int r3H) {R3H = r3H;}
    public void setR3C(int r3C) {R3C = r3C;}
    public void setR1HSS(int r1HSS) {R1HSS = r1HSS;}
    public void setR1CSS(int r1CSS) {R1CSS = r1CSS;}
    public void setR2HSS(int r2HSS) {R2HSS = r2HSS;}
    public void setR2CSS(int r2CSS) {R2CSS = r2CSS;}
    public void setR3HSS(int r3HSS) {R3HSS = r3HSS;}
    public void setR3CSS(int r3CSS) {R3CSS = r3CSS;}

    public int getMainStartPosition() {return mainStartPosition;}
    public int getTotalSS() {return totalSS;}
    public int getCargoShipTotal() {return cargoShipTotal;}
    public int getRocketShipTotal() {return rocketShipTotal;}
    public int getTotalCargo() {return totalCargo;}
    public int getTotalHatch() {return totalHatch;}
}
