package com.example.rumbleapp2020;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SheetsAccess implements Serializable {


    public static String API_KEY = "AIzaSyCPbCdf6Qmt4VMXbGhPFhFJ6qmDIKQt2RY";

    private String sheetID = "default";
    public String getSheetID() {
        return sheetID;
    }
    public void setSheetID(String sheetID) {
        this.sheetID = sheetID;
    }
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
    private List<List<Object>> sheetValue(String range) throws ExecutionException, InterruptedException {
        List<List<Object>> val = (new fetchSheet().execute("1UMDg9Evrj74S3p0fz49Dq6qFVPcSfEqSbPCyL3hJohc", range)).get();
        return val;
    }
    private String rangeTotal = getSheetID() + "!A:C";
    private List<List<Object>> sheetPage;
    public void setSheetPage() throws ExecutionException, InterruptedException {
        if (!getSheetID().equals("default")) { this.sheetPage = sheetValue(rangeTotal);}
    }
    public List<List<Object>> getSheetPage() {
        return this.sheetPage;
    }

    String nameValue(int row) { return getSheetPage().get(row).get(0).toString();}
    String teamValue(int row) { return getSheetPage().get(row).get(1).toString();}
    String matchValue(int row) { return getSheetPage().get(row).get(2).toString();}
}
