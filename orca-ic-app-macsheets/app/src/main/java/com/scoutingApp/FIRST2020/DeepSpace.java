package com.scoutingApp.FIRST2020;

import java.io.Serializable;

public class DeepSpace implements Serializable {
    private CargoShip cargo = new CargoShip();
    private RocketShip rocket = new RocketShip();
    private Info info = new Info();
    void infoSet(String match, int team, String name, String alliance){
        info.setAlliance(alliance);
        info.setMatch(match);
        info.setTeam(team);
        info.setName(name);
    }
    private boolean sandStorm = true;
    private int mainStartPosition = 0;
    private boolean mainStart = false;
    private boolean mainDefense = false;
    private int mainBlockedScores = 0;
    private String mainHelpInfo = "Help Text";
    private int mainEndgame = 0;

    private boolean extrasRedCard = false;
    private boolean extrasYellowCard = false;
    private boolean noShow = false;
    private boolean movement = true;
    private int extrasFinalScore = 0;

    private  String settingsDisplay = " ";
    private  int settingsDisplayNum = 0;
    private  String settingsAlliance = "Unknown!";
    private  String settingsHelpInfo = "Press the 'cache' button to view all data since last offload" +
            System.lineSeparator() + "Press the 'local' button to view all unsent data." +
            System.lineSeparator() + "Press the 'next' button to view the next submission in that category." +
            System.lineSeparator() + "Press the 'clear' button to clear the screen." +
            System.lineSeparator() + "Press the 'submit' button to send in the local data on display.";
    static final int HATCH = 0;
    static final int CARGO = 1;

    //getters & setters


    public String getMainMatch() {
        return info.match;
    }

    public void setMainMatch(String mainMatch) {
        this.info.match = mainMatch;
    }

    public String getSettingsAlliance() {
        return settingsAlliance;
    }
    public boolean isNoShow() {
        return noShow;
    }
    public void setNoShow(boolean noShow) {
        this.noShow = noShow;
    }
    public boolean isMovement() {
        return movement;
    }
    public void setMovement(boolean movement) {
        this.movement = movement;
    }
    public void setSettingsAlliance(String settingsAlliance) {
        this.settingsAlliance = settingsAlliance;
    }
    public CargoShip getCargo() {
        return cargo;
    }
    public RocketShip getRocket() {
        return rocket;
    }
    public boolean isSandStorm() {
        return sandStorm;
    }
    public void setSandStorm(boolean sandStorm) {
        this.sandStorm = sandStorm;
    }
    public int getMainStartPosition() {
        return mainStartPosition;
    }
    public void setMainStartPosition(int mainStartPosition) {
        this.mainStartPosition = mainStartPosition;
    }
    public boolean isMainStart() {
        return mainStart;
    }
    public void setMainStart(boolean mainStart) {
        this.mainStart = mainStart;
    }
    public boolean isMainDefense() {
        return mainDefense;
    }
    public void setMainDefense(boolean mainDefense) {
        this.mainDefense = mainDefense;
    }
    public int getMainBlockedScores() {
        return mainBlockedScores;
    }
    public void setMainBlockedScores(int mainBlockedScores) {
        this.mainBlockedScores = mainBlockedScores;
    }
    public String getMainName() {
        return info.getName();
    }
    public void setMainName(String mainName) {
        this.info.name = mainName;
    }
    public int getMainTeam() {
        return info.getTeam();
    }
    public void setMainTeam(int mainTeam) {
        this.info.team = mainTeam;
    }
    public String getMainHelpInfo() {
        return mainHelpInfo;
    }
    public void setMainHelpInfo(String mainHelpInfo) {
        this.mainHelpInfo = mainHelpInfo;
    }
    public int getMainEndgame() {
        return mainEndgame;
    }
    public void setMainEndgame(int mainEndgame) {
        this.mainEndgame = mainEndgame;
    }
    public String getMainAlliance() {
        return info.getAlliance();
    }
    public void setMainAlliance(String mainAlliance) {
        this.info.alliance = mainAlliance;
    }
    public boolean isExtrasRedCard() {
        return extrasRedCard;
    }
    public void setExtrasRedCard(boolean extrasRedCard) {
        this.extrasRedCard = extrasRedCard;
    }
    public boolean isExtrasYellowCard() {
        return extrasYellowCard;
    }
    public void setExtrasYellowCard(boolean extrasYellowCard) {
        this.extrasYellowCard = extrasYellowCard;
    }
    public int getExtrasFinalScore() {
        return extrasFinalScore;
    }
    public void setExtrasFinalScore(int extrasFinalScore) {
        this.extrasFinalScore = extrasFinalScore;
    }
    public String getExtrasNotes() {
        return info.getNotes();
    }
    public void setExtrasNotes(String extrasNotes) {
        this.info.notes = extrasNotes;
    }
    public String getSettingsDisplay() {
        return settingsDisplay;
    }
    public void setSettingsDisplay(String settingsDisplay) {
        this.settingsDisplay = settingsDisplay;
    }
    public int getSettingsDisplayNum() {
        return settingsDisplayNum;
    }
    public void setSettingsDisplayNum(int settingsDisplayNum) {
        this.settingsDisplayNum = settingsDisplayNum;
    }
    public String getSettingsHelpInfo() {
        return settingsHelpInfo;
    }

    public Info getInfo() {
        return info;
    }
    public void setInfo(Info info) {
        this.info = info;
    }
}
