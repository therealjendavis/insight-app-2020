package com.scoutingApp.FIRST2020;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class Settings extends AppCompatActivity {
    //variables and objects

    public DeepSpace getSpace() {
        return (DeepSpace) getIntent().getSerializableExtra("Game");
    }
    public PersistentData getData() {
        return (PersistentData) getIntent().getSerializableExtra("data");
    }
    public static String allianceColor;
    public static int tabletNumber = 0;
    String lineBreak = System.lineSeparator();

    // non-button methods

    public void updateTextView(String content, int id){
        TextView nametext = findViewById(id);
        nametext.setText(content);
    }
    private void displaySet(String string) {
        getSpace().setSettingsDisplay(string);
        updateTextView(getSpace().getSettingsDisplay(), R.id.settingsDisplay);
    }
    public static class Dialogs2 extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
            LayoutInflater inflater = requireActivity().getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.password_dialog2, null));
                builder.setPositiveButton("yup!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {Settings.tabletNumber = (((RadioGroup) Objects.requireNonNull(Dialogs2.this.getDialog()).findViewById(R.id.RadioGroup)).indexOfChild(
                                Objects.requireNonNull(Dialogs2.this.getDialog()).findViewById((
                                        (RadioGroup) Objects.requireNonNull(Dialogs2.this.getDialog()).findViewById(R.id.RadioGroup)).getCheckedRadioButtonId()
                                )
                            ));
                        }
                        catch (Exception e) {
                            try {Settings.tabletNumber = (((RadioGroup) Objects.requireNonNull(Dialogs2.this.getDialog()).findViewById(R.id.RadioGroup2)).indexOfChild(
                                        Objects.requireNonNull(Dialogs2.this.getDialog()).findViewById((
                                                (RadioGroup) Objects.requireNonNull(Dialogs2.this.getDialog()).findViewById(R.id.RadioGroup2)).getCheckedRadioButtonId()
                                        )
                                ));
                            }
                            catch (Exception e2) {Settings.tabletNumber = 0;}
                        }
                        PersistentData.setTabNum(Settings.tabletNumber);
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

    public static class Dialogs3 extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.password_dialog, null))
                    .setMessage("What's the password?")
                    .setPositiveButton("yup!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            boolean passwordCorrect = ((TextView) Objects.requireNonNull(getDialog()).findViewById(R.id.password)).getText().toString().equalsIgnoreCase("rocknroll");
                            if (passwordCorrect) {
                                DialogFragment newFragment = new Settings.Dialogs2();
                                newFragment.show(Objects.requireNonNull(getFragmentManager()), "tabnumber");
                            }
                            Objects.requireNonNull(Dialogs3.this.getDialog()).cancel();
                        }
                    })
                    .setNegativeButton("nope!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Objects.requireNonNull(Dialogs3.this.getDialog()).cancel();
                        }
                    });
            return builder.create();
        }
    }

    public void makeADialog3() {
        DialogFragment newFragment = new Settings.Dialogs3();
        newFragment.show(getSupportFragmentManager(), "password");
    }

    public String stringMe(Info obj) {
        return obj.getName() + " " +
        obj.getTeam() + " " +
        obj.getMatch() + " " +
        obj.getAlliance();
    }

    // threads

    class HomeThread implements Runnable {
        @Override
        public void run() {
            Intent back2 = new Intent(getApplicationContext(), MainActivity.class);
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
    }

    // button methods

    public void allianceChoose(View view) {
        makeADialog3();
    }
    public void cacheButton(View view) {
        String cache = "Cached Data:";
        if (!getData().perCacheData.isEmpty()) {
            for (int i = 0; i < getData().perCacheData.size(); i++) {
                cache = cache.concat(lineBreak + stringMe(getData().perCacheData.get(i)));
            }
        }
        displaySet(cache);
    }
    public void helpButtonTwo(View view) {
        displaySet(getSpace().getSettingsHelpInfo());
    }
    public void clearButton(View view) {
        getSpace().setSettingsDisplay("");
        updateTextView(getSpace().getSettingsDisplay(), R.id.settingsDisplay);
    }
    public void backHome2(View view){
        HomeThread thread = new HomeThread();
        Thread threadStart = new Thread(thread);
        threadStart.start();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }
}
