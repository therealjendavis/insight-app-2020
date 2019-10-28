package com.scoutingApp.FIRST2020;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //class-specific variables

    private int rocketLevel = 0;
    private PersistentData data = null;
    private DeepSpace space = null;
    private static String dialogMessage = "";
    private char cargoLoc = ' ';
    private int timerPause = 0;
    static Intent settings;

    // getters and setters

    public PersistentData getData() { return data; }
    public void setData(PersistentData data) { this.data = data; }
    public int getTimerPause() {
        return timerPause;
    }
    public void setTimerPause(int timerPause) {
        this.timerPause = timerPause;
    }
    public DeepSpace getSpace() {
        return space;
    }
    public void setSpace(DeepSpace space) {
        this.space = space;
    }
    public int getRocketLevel() {
        return rocketLevel;
    }
    public void setRocketLevel(int rocketLevel) {
        this.rocketLevel = rocketLevel;
    }
    public char getCargoLoc() {
        return cargoLoc;
    }
    public void setCargoLoc(char cargoLoc) {
        this.cargoLoc = cargoLoc;
    }
    public Intent getSettings() {
        Intent settings = new Intent(this, Settings.class);
        settings.putExtra("Game", getSpace());
        settings.putExtra("data", getData());
        return settings;
    }
    public void setFinSettingsIntent() {MainActivity.settings = getSettings();}

    // methods to set or change variables, set ss timer, etc

    public static class Dialogs extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder name = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
            name.setMessage(dialogMessage)
                    .setNegativeButton(R.string.okiedokes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Objects.requireNonNull(Dialogs.this.getDialog()).dismiss();
                        }
                    });
            return name.create();
        }
    }
    public static class Dialogs3 extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.password_dialog, null))
                    .setPositiveButton("yup!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            boolean passwordCorrect = ((TextView) Objects.requireNonNull(getDialog()).findViewById(R.id.password)).getText().toString().equalsIgnoreCase("rocknroll");
                            if (passwordCorrect) {
                                startActivity(settings);
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
    public void makeADialog(final String message, final String tag) {
        dialogMessage = message;
        DialogFragment newFragment = new Dialogs();
        newFragment.show(getSupportFragmentManager(), tag);
    }
    public void stormDelay(int seconds) {
        Timer timer = new Timer();
        timer.schedule(new RemindTask(), seconds * 1000);
        if (getTimerPause() == 0) {
            Timer pauser = new Timer();
            pauser.scheduleAtFixedRate(new RemindTask2(), 1000, 1000);
        }
    }
    class RemindTask extends TimerTask {
        public void run() {
            getSpace().setSandStorm(false);
        }
    }
    class RemindTask2 extends TimerTask {
        public void run() {
            if (getSpace().isMainStart()) {
                setTimerPause(timerPause + 1);
                if (getTimerPause() == 155) {
                    getSpace().setMainStart(false);
                    findViewById(R.id.start).setBackgroundColor(getResources().getColor(R.color.coolGreen));
                    ((Button) findViewById(R.id.start)).setText(R.string.start);
                }
            }
        }
    }
    public void rocketSet(DeepSpace game, int level, int type) {
        if (!game.isMainStart()) {makeADialog("you need to press start!", "rocketfalse");}
        else if (level == 1) {
            if (type == DeepSpace.HATCH) {
                if (game.isSandStorm()) {
                    game.getRocket().setMainR1HSS(game.getRocket().getMainR1HSS() + 1);
                }
                else game.getRocket().setMainR1H(game.getRocket().getMainR1H() + 1);
            }
            else {
                if (game.isSandStorm()) {
                    game.getRocket().setMainR1CSS(game.getRocket().getMainR1CSS() + 1);
                }
                else {
                    game.getRocket().setMainR1C(game.getRocket().getMainR1C() + 1);
                }
            }
        }
        else if (level == 2) {
            if (type == DeepSpace.HATCH) {
                if (game.isSandStorm()) {
                    game.getRocket().setMainR2HSS(game.getRocket().getMainR2HSS() + 1);
                }
                else game.getRocket().setMainR2H(game.getRocket().getMainR2H() + 1);
            }
            else {
                if (game.isSandStorm()) {
                    game.getRocket().setMainR2CSS(game.getRocket().getMainR2CSS() + 1);
                }
                else {
                    game.getRocket().setMainR2C(game.getRocket().getMainR2C() + 1);
                }
            }
        }
        else if (level == 3) {
            if (type == DeepSpace.HATCH) {
                if (game.isSandStorm()) {
                    game.getRocket().setMainR3HSS(game.getRocket().getMainR3HSS() + 1);
                }
                else game.getRocket().setMainR3H(game.getRocket().getMainR3H() + 1);
            }
            else {
                if (game.isSandStorm()) {
                    game.getRocket().setMainR3CSS(game.getRocket().getMainR3CSS() + 1);
                }
                else {
                    game.getRocket().setMainR3C(game.getRocket().getMainR3C() + 1);
                }
            }
        }
        else makeADialog("You need to pick a level!", "level");

        setRocketLevel(0);
    }
    public void cargoShipSet(DeepSpace game, char location, int type) {
        if (!game.isMainStart()) {makeADialog("you need to press start!", "rocketfalse");}
        else if (location == 'f') {
            if (type == DeepSpace.HATCH) {
                if (game.isSandStorm()) {
                    game.getCargo().setMainCSFHSS(game.getCargo().getMainCSFHSS() + 1);
                }
                else game.getCargo().setMainCSFH(game.getCargo().getMainCSFH() + 1);
            }
            else {
                if (game.isSandStorm()) {
                    game.getCargo().setMainCSFCSS(game.getCargo().getMainCSFCSS() + 1);
                }
                else game.getCargo().setMainCSFC(game.getCargo().getMainCSFC() + 1);
            }
        }
        else if (location == 'c') {
            if (type == DeepSpace.HATCH) {
                if (game.isSandStorm()) {
                    game.getCargo().setMainCSSHSS(game.getCargo().getMainCSSHSS() + 1);
                }
                else game.getCargo().setMainCSSH(game.getCargo().getMainCSSH() + 1);
            }
            else {
                if (game.isSandStorm()) {
                    game.getCargo().setMainCSSCSS(game.getCargo().getMainCSSCSS() + 1);
                }
                else game.getCargo().setMainCSSC(game.getCargo().getMainCSSC() + 1);
            }
        }
        else makeADialog("You need to pick front or side!", "cargoship");
    }
    public void updateTextView(String content, int id){
        TextView nametext = findViewById(id);
        nametext.setText(content);
    }

    // method to set the info on top of page (called on create)

    public void infoTop() {
        updateTextView(getSpace().getInfo().getName(), R.id.infoName);
        updateTextView(getSpace().getInfo().getAlliance(), R.id.infoAlliance);
        updateTextView(getSpace().getInfo().getTeam().toString(), R.id.infoTeam);
        updateTextView(getSpace().getInfo().getMatch(), R.id.infoMatch);
    }

    // button methods

    public void settingsButton(View view){
        setFinSettingsIntent();
        DialogFragment newFragment = new Dialogs3();
        newFragment.show(getSupportFragmentManager(), "settingsPassword");
    }
    public void addInfoButton(View view){
        Intent addInfo = new Intent(this, AdditionalInfo.class);
        addInfo.putExtra("Game4", getSpace());
        addInfo.putExtra("data4", getData());
        startActivity(addInfo);
    }
    public void helpButton(View view) {makeADialog(getSpace().getMainHelpInfo(), "help"); }
    public void startButton(View view) {
        if(!getSpace().isMainStart()) {
            getSpace().setMainStart(true);
            findViewById(R.id.start).setBackgroundColor(getResources().getColor(R.color.coolRed));
            ((Button) findViewById(R.id.start)).setText(R.string.stop);
            if (getTimerPause() == 0) { stormDelay(15);}
            else if (getTimerPause() <= 14) {
                getSpace().setSandStorm(true);
                stormDelay(15 - getTimerPause());}
        }
        else {
            getSpace().setMainStart(false);
            findViewById(R.id.start).setBackgroundColor(getResources().getColor(R.color.coolGreen));
            ((Button) findViewById(R.id.start)).setText(R.string.start);
            getSpace().setSandStorm(false);
        }
    }
    public void submitButton(View view) {
        Intent psPage = new Intent(this, PostSubmit.class);
        psPage.putExtra("Game2", getSpace());
        psPage.putExtra("data2", getData());
        startActivity(psPage);
    }
    public void rc(View view) {
        rocketSet(getSpace(), getRocketLevel(), DeepSpace.CARGO);
    }
    public void rh(View view) {
        rocketSet(getSpace(), getRocketLevel(), DeepSpace.HATCH);
    }
    public void csc(View view) {
        cargoShipSet(getSpace(), getCargoLoc(), DeepSpace.CARGO);
    }
    public void csh(View view) {
        cargoShipSet(getSpace(), getCargoLoc(), DeepSpace.HATCH);
    }
    public void csf(View view) {
        setCargoLoc('f');
    }
    public void css(View view) {
        setCargoLoc('c');
    }
    public void rl1(View view) {setRocketLevel(1); }
    public void rl2(View view) {
        setRocketLevel(2);
    }
    public void rl3(View view) {
        setRocketLevel(3);
    }
    public void defense(View view) {
        if (!getSpace().isMainStart()) {makeADialog("you need to press start!", "rocketfalse");}
        else getSpace().setMainDefense(true);
    }
    public void start1(View view) {
        getSpace().setMainStartPosition(1);
    }
    public void start2(View view) {getSpace().setMainStartPosition(2); }
    public void end1(View view) {
        if (getSpace().isMainStart()) {
            getSpace().setMainEndgame(1);
        }
        else makeADialog("You need to press start!", "setscore");
    }
    public void end2(View view) {
        if (getSpace().isMainStart()) {
            getSpace().setMainEndgame(2);
        }
        else makeADialog("You need to press start!", "setscore");
    }
    public void end3(View view) {
        if (getSpace().isMainStart()) { getSpace().setMainEndgame(3); }
        else { makeADialog("You need to press start!", "setscore"); }
    }
    public void blockedScore(View view){
        if (!getSpace().isMainStart()) {makeADialog("you need to press start!", "rocketfalse");}
        else getSpace().setMainBlockedScores(getSpace().getMainBlockedScores() + 1);
    }

    // method for setting object value (called on create)

    public void checkSpace() {
        if (getIntent().hasExtra("game5")) {
            setSpace((DeepSpace) getIntent().getSerializableExtra("game5"));
        }
        else if (getIntent().hasExtra("game6")) {
            setSpace((DeepSpace) getIntent().getSerializableExtra("game6"));
        }
        else {
            setSpace(new DeepSpace());
        }
    }
    public void checkData() {
        if (getIntent().hasExtra("data5")) {
            setData((PersistentData) getIntent().getSerializableExtra("data5"));
        }
        else if (getIntent().hasExtra("data4")) {
            setData((PersistentData) getIntent().getSerializableExtra("data4"));
        }
        else if (getIntent().hasExtra("data6")) {
            setData((PersistentData) getIntent().getSerializableExtra("data6"));
        }
        else {
            setData(new PersistentData());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkData();
        checkSpace();
        if (!getData().getSheet().getSheetID().equals("default")) {
            getSpace().infoSet(
                getData().getSheet().matchValue(getData().getRowNumber()),
                Integer.parseInt(getData().getSheet().teamValue(getData().getRowNumber())),
                getData().getSheet().nameValue(getData().getRowNumber()),
                getData().getPerAlliance()
            );
        }
        else {
            getSpace().infoSet("Set", 0, "Please", "Tablet Number");
        }
        infoTop();
    }
}
