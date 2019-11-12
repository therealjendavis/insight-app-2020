package com.scoutingApp.FIRST2020;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

class PersistentData implements Serializable {
    //local/cached data arrays

    ArrayList<SubmittedData> perSubData = new ArrayList<>();
    ArrayList<Info> perCacheData = new ArrayList<>();

    //variables that remain consistent

    private String perAlliance;
    private int rowNumber = 0;
    private SheetsAccess sheet = new SheetsAccess();

    //getters and setters

    SheetsAccess getSheet() {
        return sheet;
    }

    String getPerAlliance() {
        return perAlliance;
    }

    void setPerAlliance(String perAlliance) {
        this.perAlliance = perAlliance;
    }

    int getRowNumber() {
        return rowNumber;
    }

    void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    private String setSubRange() {
        return "dataFromApp!" + (rowNumber + 2) + ":" + (rowNumber + 2);
    }

    // method to send data to the sheet (requires auth code)

    boolean sender(String key) {
        try {
            String x = new SheetsAccess.sendToSheet().execute("1IrT8fskl1MCdMyxous8OO5YRCJo3Y4AoXHS_zusYrYc", setSubRange(), key).get();
            return x.equals("yup");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
