package com.scoutingApp.FIRST2020;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class RocketShip implements Serializable {
    //this class represents a rocket ship

    /*
        Explanation of HashMap constructor -
            Synchronized map needed because asynchronous access could mean non-synchronized data, we don't want that
            HashMap stores location as key (String) and score as value (Integer)
            initial capacity = total game pieces possible = 6 hatches + 6 cargo
     */
    private Map<String, Integer> dataMap = Collections.synchronizedMap(new HashMap<String, Integer>(12));

    void scoreGamePiece(int level, int type, boolean sandstorm) {
        String piece = type == DeepSpace.CARGO ? "c" : "h";
        String ss = sandstorm ? "ss" : "";
        String key = "" + level + piece + ss;
        int currentValue;
        if (dataMap.get(key) != null) currentValue = dataMap.get(key);
        else currentValue = 0;
        dataMap.put(key, currentValue + 1);
    }

    int getScore(int level, int type, boolean sandstorm) {
        String piece = type == DeepSpace.CARGO ? "c" : "h";
        String ss = sandstorm ? "ss" : "";
        String key = "" + level + piece + ss;
        if (dataMap.get(key) != null) return dataMap.get(key);
        return 0;
    }

    int getGamePieceScore(int type) {
        int totalScore = 0;
        String piece = type == DeepSpace.CARGO ? "c" : "h";
        for (Map.Entry<String, Integer> score : dataMap.entrySet()) {
            if (score.getKey().substring(1, 2).equals(piece)) totalScore += score.getValue();
        }
        return totalScore;
    }
}
