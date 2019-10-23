package com.example.rumbleapp2020;

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

    public String subRange = getSheet().getSheetID() + "!" + (rowNumber + 1) + ":" + (rowNumber + 1);

    public boolean sender() {
        try {
            new SheetsAccess.sendToSheet().execute("SpreadsheetID", subRange).get();
            return true;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
