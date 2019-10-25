package com.scoutingApp.FIRST2020;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PersistentData implements Serializable {
    ArrayList<SubmittedData> perSubData = new ArrayList<>();
    ArrayList<Info> perCacheData = new ArrayList<>();
    //load data sheet here?
    private SheetsAccess sheet = new SheetsAccess();
    public SheetsAccess getSheet() {
        return sheet;
    }
    private String perAlliance;
    private int rowNumber = 0;
    String tokenKeyThingy;

    public String getTokenKeyThingy() {
        return tokenKeyThingy;
    }
    public void setTokenKeyThingy(String tokenKeyThingy) {
        this.tokenKeyThingy = tokenKeyThingy;
    }
    public String getPerAlliance() {
        return perAlliance;
    }
    public void setPerAlliance(String perAlliance) {
        this.perAlliance = perAlliance;
    }
    public int getRowNumber() {
        return rowNumber;
    }
    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String subRange = getSheet().getSheetID() + "!" + (rowNumber + 2) + ":" + (rowNumber + 2);

    public boolean sender(String key) {
        try {
            String x = new SheetsAccess.sendToSheet().execute("1IrT8fskl1MCdMyxous8OO5YRCJo3Y4AoXHS_zusYrYc", subRange, key).get();
            return x.equals("yup");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
