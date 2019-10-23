package com.example.rumbleapp2020;

import java.io.Serializable;
import java.util.ArrayList;

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
}
