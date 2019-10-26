package com.scoutingApp.FIRST2020;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class Settings extends AppCompatActivity {
    GoogleSignInClient googleClient;
    public DeepSpace getSpace() {
        return (DeepSpace) getIntent().getSerializableExtra("Game");
    }
    public PersistentData getData() {
        return (PersistentData) getIntent().getSerializableExtra("data");
    }
    private boolean cache = false;
    private boolean local = false;
    private boolean help = false;
    public static String allianceColor;
    public static String dialogMessage;
    public static int tabletNumber = 0;
    public boolean isCache() {
        return cache;
    }
    public void setCache(boolean cache) {
        this.cache = cache;
    }
    public boolean isLocal() {
        return local;
    }
    public void setLocal(boolean local) {
        this.local = local;
    }
    public boolean isHelp() {
        return help;
    }
    public void setHelp(boolean help) {
        this.help = help;
    }

    public void clickedMe() {
        setCache(false);
        setLocal(false);
        setHelp(false);
    }
    public void updateTextView(String content, int id){
        TextView nametext = findViewById(id);
        nametext.setText(content);
    }
    private void displaySet(String string) {
        getSpace().setSettingsDisplay(string);
        updateTextView(getSpace().getSettingsDisplay(), R.id.settingsDisplay);
    }
    public static class Dialogs extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder name = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
            name.setMessage(dialogMessage)
                    .setNegativeButton(R.string.okiedokes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Objects.requireNonNull(Settings.Dialogs.this.getDialog()).dismiss();
                        }
                    });
            return name.create();
        }
    }
    public static class Dialogs2 extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.password_dialog2, null));
            builder.setMessage(dialogMessage)
                .setPositiveButton("yup!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Settings.tabletNumber = Integer.parseInt(((TextView) Objects.requireNonNull(Dialogs2.this.getDialog()).findViewById(R.id.tabnum)).getText().toString());
                        if (tabletNumber <= 3) {
                            allianceColor = "Red";
                        } else {
                            allianceColor = "Blue";
                        }
                        Objects.requireNonNull(Dialogs2.this.getDialog()).cancel();
                    }
                });
            return builder.create();
        }
    }
    public void makeADialog(final String message, final String tag) {
        dialogMessage = message;
        DialogFragment newFragment = new Settings.Dialogs();
        newFragment.show(getSupportFragmentManager(), tag);
    }
    public void makeADialog2(final String message, final String tag) {
        dialogMessage = message;
        DialogFragment newFragment = new Settings.Dialogs2();
        newFragment.show(getSupportFragmentManager(), tag);
    }
    String lineBreak = System.lineSeparator();
    public String stringMe(Info obj) {
        return obj.getName() + lineBreak +
        obj.getTeam() + lineBreak +
        obj.getMatch() + lineBreak +
        obj.getAlliance() + lineBreak +
        obj.getNotes();
    }

    public void allianceChoose(View view) {
        makeADialog2("Choose this tablet's number!", "tabNumber");
    }
    public void cacheButton(View view) {
        getSpace().setSettingsDisplayNum(0);
        if (!getData().perCacheData.isEmpty()) {
            displaySet(stringMe(getData().perCacheData.get(getSpace().getSettingsDisplayNum())));
        }
        clickedMe();
        setCache(true);
    }
    public void localButton(View view) {
        getSpace().setSettingsDisplayNum(0);
        if (!getData().perSubData.isEmpty()) {
            displaySet("Submit? Match number " + getData().perSubData.get(getSpace().getSettingsDisplayNum()).getMatchNumber());
        }
        clickedMe();
        setLocal(true);
    }
    public void helpButtonTwo(View view) {
        getSpace().setSettingsDisplayNum(0);
        getSpace().setSettingsDisplay(getSpace().getSettingsHelpInfo());
        updateTextView(getSpace().getSettingsDisplay(), R.id.settingsDisplay);
        clickedMe();
        setHelp(true);
    }
    public void nextButton(View view) {
        getSpace().setSettingsDisplayNum(getSpace().getSettingsDisplayNum() + 1);
        if (isCache()) {
            if (getSpace().getSettingsDisplayNum() < getData().perCacheData.size()) {
                displaySet(stringMe(getData().perCacheData.get(getSpace().getSettingsDisplayNum())));
            }
        }
        else if (isLocal()) {
            if (getSpace().getSettingsDisplayNum() < getData().perSubData.size()) {
                displaySet("Submit? Match number " + getData().perSubData.get(getSpace().getSettingsDisplayNum()).getMatchNumber());
            }
        }
        else if (isHelp()) {makeADialog("This function is not available for this attribute.", "notAvailable");}
        else {makeADialog("Please select an attribute.", "noSelect");}
    }
    public void clearButton(View view) {
        getSpace().setSettingsDisplay("");
        updateTextView(getSpace().getSettingsDisplay(), R.id.settingsDisplay);
    }
    public void submitButtonThree(View view) {
        if (!isLocal()) { makeADialog("Unable to submit data in this category.", "noSubmit"); }
        else if (!getData().perSubData.isEmpty()){
            getData().getSheet().setValues(getData().perSubData.get(getSpace().getSettingsDisplayNum()).setValues());
            getConnected();
            getSpace().setSettingsDisplay("");
            updateTextView(getSpace().getSettingsDisplay(), R.id.settingsDisplay);
        }
    }
    public void backHome2(View view){
        Intent back2 = new Intent(this, MainActivity.class);
        back2.putExtra("game6", getSpace());
        back2.putExtra("data6", getData());
        if ((tabletNumber != 0) && (getData().getSheet().getSheetPage() == null)){
            getData().setPerAlliance(allianceColor);
            getData().getSheet().setSheetID("tab" + tabletNumber);
            try {
                getData().getSheet().setSheetPage();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        startActivity(back2);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }

    private void getConnected()
    {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(SheetsScopes.SPREADSHEETS_READONLY))
                .requestScopes(new Scope(SheetsScopes.SPREADSHEETS))
                .requestIdToken("782050499682-o0e2ebf3q5fdh34pti8o5a9t0a5llnvp.apps.googleusercontent.com")
                .requestServerAuthCode("782050499682-o0e2ebf3q5fdh34pti8o5a9t0a5llnvp.apps.googleusercontent.com")
                .requestEmail()
                .build();
        googleClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = googleClient.getSignInIntent();
        startActivityForResult(signInIntent, 1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
            //  getData().setTokenKeyThingy(getData().getSheet().handleSignInResult(task));
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            //         getData().getSheet().setGoogleUser(account);

            // Signed in successfully, show authenticated UI.
            Log.i("SETTINGS", account.toString());
            getAuthCode(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("SETTINGS", "signInResult:failed code=" + e.getStatusCode());
            makeADialog("no connection!", "noConnect");

        }
    }

    private void getAuthCode(GoogleSignInAccount acct)
    {

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("grant_type", "authorization_code")
                .add("client_id", "782050499682-o0e2ebf3q5fdh34pti8o5a9t0a5llnvp.apps.googleusercontent.com")
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
                Log.e("SETTINGS", e.toString());
                makeADialog("no connection!", "noConnect");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    final String token = jsonObject.get("access_token").toString();

                    Log.i("SETTINGS", token);
                    if(getData().sender(token)) {
                        getData().perSubData.remove(getSpace().getSettingsDisplayNum());
                    }
                    else
                    {
                        makeADialog("no connection!", "noConnect");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    makeADialog("no connection!", "noConnect");
                }
            }
        });
    }
}
