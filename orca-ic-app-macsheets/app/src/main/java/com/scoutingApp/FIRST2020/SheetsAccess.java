package com.scoutingApp.FIRST2020;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SheetsAccess implements Serializable {


    public static String API_KEY = "AIzaSyAAwkxoRODxyIyMpJv-ss7fPOdmyv8KfQI";
    private String sheetID = "default";
    private List<List<Object>> sheetPage = null;
    private static List<List<Object>> values;

    public String getSheetID() {
        return sheetID;
    }
    public void setSheetID(String sheetID) {
        this.sheetID = sheetID;
    }
    public void setSheetPage() throws ExecutionException, InterruptedException {
        if (!getSheetID().equals("default")) {
            String rangeTotal = getSheetID() + "!A:C";
            this.sheetPage = sheetValue(rangeTotal);}
    }
    public List<List<Object>> getSheetPage() {
        return this.sheetPage;
    }
    void setValues(List<List<Object>> values) {
        SheetsAccess.values = values;
    }

    private List<List<Object>> sheetValue(String range) throws ExecutionException, InterruptedException {
        return (new fetchSheet().execute("1UMDg9Evrj74S3p0fz49Dq6qFVPcSfEqSbPCyL3hJohc", range)).get();
    }

    String nameValue(int row) { return getSheetPage().get(row).get(0).toString();}
    String teamValue(int row) { return getSheetPage().get(row).get(1).toString();}
    String matchValue(int row) { return getSheetPage().get(row).get(2).toString();}

    protected static class fetchSheet extends AsyncTask<String, Void, List<List<Object>>> {
        @Override
        protected List<List<Object>> doInBackground(String... strings) {

            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory factory = JacksonFactory.getDefaultInstance();

            Sheets service = new Sheets.Builder(transport, factory, null)
                    .setApplicationName("scouting-app-2020")
                    .build();

            ValueRange response = null;
            try {
                response = service.spreadsheets().values()
                        .get(strings[0], strings[1])
                        .setKey(API_KEY)
                        .execute();
            } catch (IOException e) {
                e.printStackTrace();
            }


            if (response != null) {
                return response.getValues();
            } else return null;
        }
    }
    protected static class sendToSheet extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String returner;
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory factory = JacksonFactory.getDefaultInstance();

            Sheets service = new Sheets.Builder(transport, factory, null)
                    .setApplicationName("scouting-app-2020")
                    .build();

            ValueRange body = new ValueRange()
                    .setValues(values)
                    .setMajorDimension("COLUMNS");
            try {
                        //sheetID, range, body
                        service.spreadsheets().values().update(strings[0], strings[1], body)
                                .setValueInputOption("RAW")
                                .setKey(API_KEY)
                                .execute();
                        returner = "yup";
            } catch (IOException e) {
                e.printStackTrace();
                returner = "nope";
            }
            return returner;
        }
    }
}
