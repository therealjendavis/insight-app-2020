package com.scoutingApp.FIRST2020;

import java.io.Serializable;

class CargoShip implements Serializable {
    //this class represents a cargo ship
    
    private int mainCSFH = 0;
    private int mainCSFC = 0;
    private int mainCSSH = 0;
    private int mainCSSC = 0;
    private int mainCSFHSS = 0;
    private int mainCSFCSS = 0;
    private int mainCSSHSS = 0;
    private int mainCSSCSS = 0;

    int getMainCSFH() {
        return mainCSFH;
    }
    void setMainCSFH(int mainCSFH) {
        this.mainCSFH = mainCSFH;
    }
    int getMainCSFC() {
        return mainCSFC;
    }
    void setMainCSFC(int mainCSFC) {
        this.mainCSFC = mainCSFC;
    }
    int getMainCSSH() {
            return mainCSSH;
        }
    void setMainCSSH(int mainCSSH) {
            this.mainCSSH = mainCSSH;
        }
    int getMainCSSC() {
            return mainCSSC;
        }
    void setMainCSSC(int mainCSSC) {
            this.mainCSSC = mainCSSC;
        }
    int getMainCSFHSS() {
            return mainCSFHSS;
        }
    void setMainCSFHSS(int mainCSFHSS) {
            this.mainCSFHSS = mainCSFHSS;
        }
    int getMainCSFCSS() {
            return mainCSFCSS;
        }
    void setMainCSFCSS(int mainCSFCSS) {
            this.mainCSFCSS = mainCSFCSS;
        }
    int getMainCSSHSS() {
            return mainCSSHSS;
        }
    void setMainCSSHSS(int mainCSSHSS) {
            this.mainCSSHSS = mainCSSHSS;
        }
    int getMainCSSCSS() {
            return mainCSSCSS;
        }
    void setMainCSSCSS(int mainCSSCSS) {
            this.mainCSSCSS = mainCSSCSS;
        }

    // Bradley's code
    void scoreGamePiece(char location, int type, boolean sandstorm) {
        // iterate over possible levels and game piece scores
        switch(location) {
            // Front
            case 'f':
                // TeleOp
                if (type == DeepSpace.CARGO && !sandstorm) mainCSFC++;
                else if (type == DeepSpace.HATCH && !sandstorm) mainCSFH++;
                // Sandstorm
                else if (type == DeepSpace.CARGO) mainCSFCSS++;
                else if (type == DeepSpace.HATCH) mainCSFHSS++;
                break;
            // Sides
            case 'c':
                // TeleOp
                if (type == DeepSpace.CARGO && !sandstorm) mainCSSC++;
                else if (type == DeepSpace.HATCH && !sandstorm) mainCSSH++;
                    // Sandstorm
                else if (type == DeepSpace.CARGO) mainCSSCSS++;
                else if (type == DeepSpace.HATCH) mainCSSHSS++;
                break;
        }
    }
}
