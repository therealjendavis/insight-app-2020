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

class SheetsAccess implements Serializable {
    //this class handles all sheet access and such

    private String sheetID = "default";
    private List<List<Object>> sheetPage = null;
    private static List<List<Object>> values;

    String getSheetID() {
        return sheetID;
    }
    void setSheetID(String sheetID) {
        this.sheetID = sheetID;
    }
    void setSheetPage() throws ExecutionException, InterruptedException {
        if (!getSheetID().equals("default")) {
            String rangeTotal = getSheetID() + "!A:C";
            this.sheetPage = sheetValue(rangeTotal);}
    }
    List<List<Object>> getSheetPage() {
        return this.sheetPage;
    }
    void setValues(List<List<Object>> values) {
        SheetsAccess.values = values;
    }

    private List<List<Object>> sheetValue(String range) throws ExecutionException, InterruptedException {
        if ((new fetchSheet().execute("1gz3ZkNAZ0rtq6hBiLIRO6hiUAkN3Dhu_UQ-Nc11aW0E", range)).get() != null) {
            return (new fetchSheet().execute("1gz3ZkNAZ0rtq6hBiLIRO6hiUAkN3Dhu_UQ-Nc11aW0E", range)).get();
        }
        else {
            setSheetID("default");
            return null;
        }
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
                String API_KEY = "AIzaSyAAwkxoRODxyIyMpJv-ss7fPOdmyv8KfQI";
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
                    .setMajorDimension("ROWS");
            try {
                        //sheetID, range, body
                        service.spreadsheets().values()
                                .append(strings[0], strings[1], body)
                                .setValueInputOption("RAW")
                                .setAccessToken(strings[2])
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
