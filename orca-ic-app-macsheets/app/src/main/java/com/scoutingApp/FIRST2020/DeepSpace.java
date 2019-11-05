package com.scoutingApp.FIRST2020;

import java.io.Serializable;

class DeepSpace implements Serializable {
    //this class represents a "game"

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
    private int mainEndgame = 0;
    private boolean extrasRedCard = false;
    private boolean extrasYellowCard = false;
    private boolean noShow = false;
    private boolean movement = true;
    private int extrasFinalScore = 0;
    private  String settingsDisplay = " ";
    private  int settingsDisplayNum = 0;
    private  String settingsHelpInfo = ("Press the 'cache' button to view all data since last offload" +
            System.lineSeparator() + "Press the 'local' button to view all unsent data." +
            System.lineSeparator() + "Press the 'next' button to view the next submission in that category." +
            System.lineSeparator() + "Press the 'clear' button to clear the screen." +
            System.lineSeparator() + "Press the 'submit' button to send in the local data on display.");
    static final int HATCH = 0;
    static final int CARGO = 1;

    //getters & setters

    void setMainMatch(String mainMatch) {
        this.info.match = mainMatch;
    }
    boolean isNoShow() {
        return noShow;
    }
    void setNoShow(boolean noShow) {
        this.noShow = noShow;
    }
    boolean isMovement() {
        return movement;
    }
    void setMovement(boolean movement) {
        this.movement = movement;
    }
    CargoShip getCargo() {
        return cargo;
    }
    RocketShip getRocket() {
        return rocket;
    }
    boolean isSandStorm() {
        return sandStorm;
    }
    void setSandStorm(boolean sandStorm) {
        this.sandStorm = sandStorm;
    }
    int getMainStartPosition() {
        return mainStartPosition;
    }
    void setMainStartPosition(int mainStartPosition) {
        this.mainStartPosition = mainStartPosition;
    }
    boolean isMainStart() {
        return mainStart;
    }
    void setMainStart(boolean mainStart) {
        this.mainStart = mainStart;
    }
    boolean isMainDefense() {
        return mainDefense;
    }
    void setMainDefense(boolean mainDefense) {
        this.mainDefense = mainDefense;
    }
    int getMainBlockedScores() {
        return mainBlockedScores;
    }
    void setMainBlockedScores(int mainBlockedScores) {
        this.mainBlockedScores = mainBlockedScores;
    }
    String getMainName() {
        return info.getName();
    }
    void setMainName(String mainName) {
        this.info.name = mainName;
    }
    int getMainTeam() {
        return info.getTeam();
    }
    void setMainTeam(int mainTeam) {
        this.info.team = mainTeam;
    }
    String getMainHelpInfo() {
        return "MEET THIS YEAR'S DEVELOPERS!" + System.lineSeparator() +
                "      PROGRAMMING:" + System.lineSeparator() +
                "            Cassidy Schiller" + System.lineSeparator() +
                "            Mac Fraser" + System.lineSeparator() +
                "            Henry Morris" + System.lineSeparator() +
                "      Layout and Design:" + System.lineSeparator() +
                "            Cassidy Schiller" + System.lineSeparator() +
                "            Sam Slopey" + System.lineSeparator() +
                "            Em Brown" + System.lineSeparator() +
                "            Khai Little" + System.lineSeparator() +
                "            Jackie Lawton" + System.lineSeparator() +
                "Want to see your name here? Contact your team's collective representative to find out how to get involved with app development!";
    }
    int getMainEndgame() {
        return mainEndgame;
    }
    void setMainEndgame(int mainEndgame) {
        this.mainEndgame = mainEndgame;
    }
    String getMainAlliance() {
        return info.getAlliance();
    }
    void setMainAlliance(String mainAlliance) {
        this.info.alliance = mainAlliance;
    }
    boolean isExtrasRedCard() {
        return extrasRedCard;
    }
    void setExtrasRedCard(boolean extrasRedCard) {
        this.extrasRedCard = extrasRedCard;
    }
    boolean isExtrasYellowCard() {
        return extrasYellowCard;
    }
    void setExtrasYellowCard(boolean extrasYellowCard) {
        this.extrasYellowCard = extrasYellowCard;
    }
    int getExtrasFinalScore() {
        return extrasFinalScore;
    }
    void setExtrasFinalScore(int extrasFinalScore) {
        this.extrasFinalScore = extrasFinalScore;
    }
    String getExtrasNotes() {
        return info.getNotes();
    }
    void setExtrasNotes(String extrasNotes) {
        this.info.notes = extrasNotes;
    }
    String getSettingsDisplay() {
        return settingsDisplay;
    }
    void setSettingsDisplay(String settingsDisplay) {
        this.settingsDisplay = settingsDisplay;
    }
    int getSettingsDisplayNum() {
        return settingsDisplayNum;
    }
    void setSettingsDisplayNum(int settingsDisplayNum) {
        this.settingsDisplayNum = settingsDisplayNum;
    }
    String getSettingsHelpInfo() {
        return settingsHelpInfo;
    }
    Info getInfo() {
        return info;
    }
}
