package com.scoutingApp.FIRST2020;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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

    String handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            assert account != null;
            return getAuthCode(account);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getAuthCode(GoogleSignInAccount acct) {
        final String[] returnMe = new String[1];
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("grant_type", "authorization_code")
                .add("client_id", "782050499682-o0e2ebf3q5fdh34pti8o5a9t0a5llnvp.apps.googleusercontent.com ")
                .add("client_secret", "vlGO8-L2b8-of6b7wXkPkMWT")
                .add("redirect_uri","")
                .add("code", acct.getServerAuthCode())
                .add("access_type", "offline")
                .add("id_token", acct.getIdToken())
                .build();
        final Request request = new Request.Builder()
                .url("https://www.googleapis.com/oauth2/v4/token")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                returnMe[0] = null;
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    returnMe[0] = jsonObject.get("access_token").toString();
                } catch (JSONException e) {
                    returnMe[0] = null;
                    e.printStackTrace();
                }
            }
        });
        return returnMe[0];
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
