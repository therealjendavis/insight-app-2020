package com.scoutingApp.FIRST2020;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class RocketShip implements Serializable {
    //this class represents a rocket ship

    private Map<String, Integer> dataMap = Collections.synchronizedMap(new HashMap<String, Integer>(12));

    RocketShip() {
        dataMap.put("r1h", 0);
        dataMap.put("r1c", 0);
        dataMap.put("r2h", 0);
        dataMap.put("r2c", 0);
        dataMap.put("r3h", 0);
        dataMap.put("r3c", 0);
        dataMap.put("r1hss", 0);
        dataMap.put("r1css", 0);
        dataMap.put("r2hss", 0);
        dataMap.put("r2css", 0);
        dataMap.put("r3hss", 0);
        dataMap.put("r3css", 0);
    }

    // Bradley's code
    void scoreGamePiece(int level, int type, boolean sandstorm) {
        String piece = type == DeepSpace.CARGO ? "c" : "h";
        String ss = sandstorm ? "ss" : "";
        String key = "r" + level + piece + ss;
        int currentValue;
        if (dataMap.get(key) != null) currentValue = dataMap.get(key);
        else currentValue = 0;
        dataMap.put(key, currentValue + 1);
    }

    int getScore(int level, int type, boolean sandstorm) {
        String piece = type == DeepSpace.CARGO ? "c" : "h";
        String ss = sandstorm ? "ss" : "";
        String key = "r" + level + piece + ss;
        if (dataMap.get(key) != null) return dataMap.get(key);
        return 0;
    }

    int getTotalScore(int type) {
        int totalScore = 0;
        String piece = type == DeepSpace.CARGO ? "c" : "h";
        for (Map.Entry<String, Integer> score : dataMap.entrySet()) {
            System.out.println(score.getKey().substring(2,3));
            if (score.getKey().substring(2,3).equals(piece)) totalScore += score.getValue();
        }
        return totalScore;
    }
}
