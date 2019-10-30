package com.scoutingApp.FIRST2020;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

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

    private int getTotalSS() { return CSFHSS + CSFCSS + CSSCSS + CSSHSS + R1HSS + R1CSS + R2HSS + R2CSS + R3CSS + R3HSS; }
    private int getCargoShipTotal() { return CSFH + CSFC + CSSH + CSSC + CSFHSS + CSFCSS + CSSHSS + CSSCSS; }
    private int getRocketShipTotal() { return R1H + R1C + R2H + R2C + R3H + R3C + R1HSS + R1CSS + R2HSS + R2CSS + R3HSS + R3CSS; }
    private int getTotalCargo() { return CSFC + CSSC + CSFCSS + CSSCSS + R1C + R2C + R3C + R1CSS + R2CSS + R3CSS; }
    private int getTotalHatch() { return CSFH + CSSH + CSFHSS + CSSHSS + R1H + R2H + R3H + R1HSS + R2HSS + R3HSS; }

    List<List<Object>> setValues() {
        return Arrays.asList(
                Arrays.asList(
                        ((Object) mainStartPosition),
                        mainDefense,
                        mainBlockedScores,
                        mainEndgame,
                        extrasRedCard,
                        extrasYellowCard,
                        noShow,
                        movement,
                        extrasFinalScore,
                        team,
                        match,
                        name,
                        alliance,
                        notes,
                        CSFH,
                        CSFC,
                        CSSH,
                        CSSC,
                        CSFHSS,
                        CSFCSS,
                        CSSHSS,
                        CSSCSS,
                        R1H,
                        R1C,
                        R2H,
                        R2C,
                        R3H,
                        R3C,
                        R1HSS,
                        R1CSS,
                        R2HSS,
                        R2CSS,
                        R3HSS,
                        R3CSS,
                        getTotalSS(),
                        getCargoShipTotal(),
                        getRocketShipTotal(),
                        getTotalCargo(),
                        getTotalHatch()
                )
        );
    }

    void setNotes(String notes) {this.notes = notes;}
    void setMainStartPosition(int mainStartPosition) {this.mainStartPosition = mainStartPosition;}
    void setMainDefense(boolean mainDefense) {this.mainDefense = mainDefense;}
    void setMainBlockedScores(int mainBlockedScores) {this.mainBlockedScores = mainBlockedScores;}
    void setMainEndgame(int mainEndgame) {this.mainEndgame = mainEndgame;}
    void setExtrasRedCard(boolean extrasRedCard) {this.extrasRedCard = extrasRedCard;}
    void setExtrasYellowCard(boolean extrasYellowCard) {this.extrasYellowCard = extrasYellowCard;}
    void setNoShow(boolean noShow) {this.noShow = noShow;}
    void setMovement(boolean movement) {this.movement = movement;}
    void setExtrasFinalScore(int extrasFinalScore) {this.extrasFinalScore = extrasFinalScore;}
    void setTeam(int team) {this.team = team;}
    void setMatch(String match) {this.match = match;}
    public void setName(String name) {this.name = name;}
    void setAlliance(String alliance) {this.alliance = alliance;}
    void setCSFH(int CSFH) {this.CSFH = CSFH;}
    void setCSFC(int CSFC) {this.CSFC = CSFC;}
    void setCSSH(int CSSH) {this.CSSH = CSSH;}
    void setCSSC(int CSSC) {this.CSSC = CSSC;}
    void setCSFHSS(int CSFHSS) {this.CSFHSS = CSFHSS;}
    void setCSFCSS(int CSFCSS) {this.CSFCSS = CSFCSS;}
    void setCSSHSS(int CSSHSS) {this.CSSHSS = CSSHSS;}
    void setCSSCSS(int CSSCSS) {this.CSSCSS = CSSCSS;}
    void setR1H(int r1H) {R1H = r1H;}
    void setR1C(int r1C) {R1C = r1C;}
    void setR2H(int r2H) {R2H = r2H;}
    void setR2C(int r2C) {R2C = r2C;}
    void setR3H(int r3H) {R3H = r3H;}
    void setR3C(int r3C) {R3C = r3C;}
    void setR1HSS(int r1HSS) {R1HSS = r1HSS;}
    void setR1CSS(int r1CSS) {R1CSS = r1CSS;}
    void setR2HSS(int r2HSS) {R2HSS = r2HSS;}
    void setR2CSS(int r2CSS) {R2CSS = r2CSS;}
    void setR3HSS(int r3HSS) {R3HSS = r3HSS;}
    void setR3CSS(int r3CSS) {R3CSS = r3CSS;}

    String getMatchNumber() {return match;}
}
