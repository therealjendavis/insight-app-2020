package com.example.rumbleapp2020;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class Settings extends AppCompatActivity {

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
        else if (isLocal()) {makeADialog("You still need to code this!! (local)", "noMoreData");}
        else if (isHelp()) {makeADialog("This function is not available for this attribute.", "notAvailable");}
        else {makeADialog("Please select an attribute.", "noSelect");}
    }
    public void clearButton(View view) {
        getSpace().setSettingsDisplay("");
        updateTextView(getSpace().getSettingsDisplay(), R.id.settingsDisplay);
    }
    public void submitButtonThree(View view) {
        if (!isLocal()) { makeADialog("Unable to submit data in this category.", "noSubmit"); }
        else {
            getData().getSheet().setValues(getData().perSubData.get(getSpace().getSettingsDisplayNum()).setValues());
            if (!getData().sender()) {
                makeADialog("no connection!", "noConnect");
            }
            else {getData().perSubData.remove(getSpace().getSettingsDisplayNum());}
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
}
