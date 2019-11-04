package com.scoutingApp.FIRST2020;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

class PersistentData implements Serializable {
    ArrayList<SubmittedData> perSubData = new ArrayList<>();
    ArrayList<Info> perCacheData = new ArrayList<>();
    //load data sheet here?
    private SheetsAccess sheet = new SheetsAccess();
    SheetsAccess getSheet() {
        return sheet;
    }
    private String perAlliance;
    private int rowNumber = 0;
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
    static void threadify(Class<? extends Runnable> c) {
        Runnable thread = null;
        try {
            thread = c.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        Thread threadStart = new Thread(thread);
        threadStart.start();
    }

    private String setSubRange() { return "dataFromApp!" + (rowNumber + 2) + ":" + (rowNumber + 2);}

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
