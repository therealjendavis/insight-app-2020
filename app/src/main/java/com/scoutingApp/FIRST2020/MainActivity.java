package com.scoutingApp.FIRST2020;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

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

    public PersistentData getData() {
        return data;
    }

    public void setData(PersistentData data) {
        this.data = data;
    }

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

    public void setFinSettingsIntent() {
        MainActivity.settings = getSettings();
    }

    // methods to set or change variables, set ss timer, etc

    //    public void updateScoreText(int id, int score, int array) {
//        TextView text = findViewById(id);
//        String[] res = getResources().getStringArray(array);
//        text.setText(res[score]);
//    }
    public void updateScoreText(int id, int score, int type) {
        TextView text = findViewById(id);
        String label = (type == DeepSpace.CARGO ? "Cargo (" : "Hatch (") + score + ")";
        text.setText(label);
    }

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
                TimerCheckThread thread = new TimerCheckThread();
                Thread threadStart = new Thread(thread);
                runOnUiThread(threadStart);
                if (getTimerPause() == 155) {
                    getSpace().setMainStart(false);
                }
            }
        }
    }

    public void rocketSet(DeepSpace game, int level, int type) {
        // If game started
        if (!game.isMainStart()) makeADialog("you need to press start!", "rocketfalse");
            // If level is valid
        else if (level > 0 && level <= 3) {
            // If score is at maximum
            if (game.getRocket().getTotalScore(type) >= 12) {
                makeADialog("Can't score more than 12 hatches/cargo on the rocket!", "scoreSizeRocket");
            } else {
                // Actually add one to the score
                game.getRocket().scoreGamePiece(level, type, game.isSandStorm());
                setRocketLevel(0);
                int id = type == DeepSpace.HATCH ? R.id.RH : R.id.RC;
                updateScoreText(id, game.getRocket().getTotalScore(type), type);
            }
        }
        // If level isn't valid
        else makeADialog("You need to pick a level!", "level");
    }

    public void cargoShipSet(DeepSpace game, char location, int type) {
        // If game started
        if (!game.isMainStart()) makeADialog("you need to press start!", "rocketfalse");
            // If location is valid
        else if (location == 'f' || location == 'c') {
            // If score is at maximum
            if (game.getCargo().getTotalScore(type) >= 16) {
                makeADialog("Can't score more than 16 hatches/cargo on the rocket!", "scoreSizeCargo");
            } else {
                // Actually add one to the score
                game.getCargo().scoreGamePiece(location, type, game.isSandStorm());
                setCargoLoc(' ');
                int id = type == DeepSpace.HATCH ? R.id.Hatchcsf : R.id.button11;
                updateScoreText(id, game.getCargo().getTotalScore(type), type);
            }
        }
        // If location invalid
        else makeADialog("You need to pick front or side!", "cargoship");
    }

    public void updateTextView(String content, int id) {
        TextView nametext = findViewById(id);
        nametext.setText(content);
    }

    // methods called on create

    public void updateDisplayInfo() {
        updateTextView(getSpace().getInfo().getName(), R.id.infoName);
        updateTextView(getSpace().getInfo().getAlliance(), R.id.infoAlliance);
        updateTextView(getSpace().getInfo().getTeam().toString(), R.id.infoTeam);
        updateTextView(getSpace().getInfo().getMatch(), R.id.infoMatch);
    }

    public void dialogCheck() {
        DialogCheckThread thread = new DialogCheckThread();
        Thread threadStart = new Thread(thread);
        threadStart.start();
    }

    public void colorSet(int id, int color) {
        findViewById(id).setBackgroundColor(getResources().getColor(color));
    }

    public void checkDataSpace() {
        if (getIntent().hasExtra("game5")) {
            setSpace((DeepSpace) getIntent().getSerializableExtra("game5"));
        } else if (getIntent().hasExtra("game6")) {
            setSpace((DeepSpace) getIntent().getSerializableExtra("game6"));
        } else {
            setSpace(new DeepSpace());
        }
        if (getIntent().hasExtra("data5")) {
            setData((PersistentData) getIntent().getSerializableExtra("data5"));
        } else if (getIntent().hasExtra("data4")) {
            setData((PersistentData) getIntent().getSerializableExtra("data4"));
        } else if (getIntent().hasExtra("data6")) {
            setData((PersistentData) getIntent().getSerializableExtra("data6"));
        } else {
            setData(new PersistentData());
        }
    }

    // threads

    class DialogCheckThread implements Runnable {
        @Override
        public void run() {
            if (getData().perSubData != null && getData().getSheet().getSheetPage() != null) {
                int x = Integer.parseInt(getData().getSheet().getSheetPage().get(getData().getRowNumber()).get(2).toString()); //current match number
                int y = Integer.parseInt(getData().getSheet().getSheetPage().get(getData().getRowNumber() - 1).get(2).toString()); //last match number
                if (!(getData().getSheet().getSheetPage().get(getData().getRowNumber()).get(0).equals(getData().getSheet().getSheetPage().get(getData().getRowNumber() - 1).get(0))) && (x - 1 == y)) {
                    makeADialog("Please go find the next scouter, " + getData().getSheet().getSheetPage().get(getData().getRowNumber()).get(0), "handoff");
                }
            }
        }
    }

    class TimerCheckThread implements Runnable {
        @Override
        public void run() {
            TextView timerText = findViewById(R.id.timer);
            timerText.setText(String.valueOf(getTimerPause()));
        }
    }

    class SettingsButtonThread implements Runnable {
        @Override
        public void run() {
            setFinSettingsIntent();
            DialogFragment newFragment = new Dialogs3();
            newFragment.show(getSupportFragmentManager(), "settingsPassword");
        }
    }

    class SubmitButtonThread implements Runnable {
        @Override
        public void run() {
            Intent psPage = new Intent(getApplicationContext(), PostSubmit.class);
            psPage.putExtra("Game2", getSpace());
            psPage.putExtra("data2", getData());
            startActivity(psPage);
        }
    }

    class AddInfoButtonThread implements Runnable {
        @Override
        public void run() {
            Intent addInfo = new Intent(getApplicationContext(), AdditionalInfo.class);
            addInfo.putExtra("Game4", getSpace());
            addInfo.putExtra("data4", getData());
            startActivity(addInfo);
        }
    }

    class BlockedScoreThread implements Runnable {
        @Override
        public void run() {
            if (!getSpace().isMainStart()) {
                makeADialog("you need to press start!", "rocketfalse");
            } else getSpace().setMainBlockedScores(getSpace().getMainBlockedScores() + 1);
        }
    }

    // button methods

    public void settingsButton(View view) {
        SettingsButtonThread thread = new SettingsButtonThread();
        Thread threadStart = new Thread(thread);
        threadStart.start();
    }

    public void addInfoButton(View view) {
        AddInfoButtonThread thread = new AddInfoButtonThread();
        Thread threadStart = new Thread(thread);
        threadStart.start();
    }

    public void helpButton(View view) {
        makeADialog(getSpace().getMainHelpInfo(), "help");
    }

    public void startButton(View view) {
        if (getSpace().getMainStartPosition() != 0) {
            if (!getSpace().isMainStart()) {
                getSpace().setMainStart(true);
                findViewById(R.id.start3).setBackgroundColor(getResources().getColor(R.color.coolRed));
                ((Button) findViewById(R.id.start3)).setText(R.string.stop);
                if (getTimerPause() == 0) {
                    stormDelay(15);
                } else if (getTimerPause() <= 14) {
                    getSpace().setSandStorm(true);
                    stormDelay(15 - getTimerPause());
                }
            } else {
                getSpace().setMainStart(false);
                findViewById(R.id.start3).setBackgroundColor(getResources().getColor(R.color.coolGreen));
                ((Button) findViewById(R.id.start3)).setText(R.string.start);
                getSpace().setSandStorm(false);
            }
        } else {
            makeADialog("You need to press choose a start location!", "startLoc");
        }
    }

    public void submitButton(View view) {
        SubmitButtonThread thread = new SubmitButtonThread();
        Thread threadStart = new Thread(thread);
        threadStart.start();
    }

    public void rc(View view) {
        rocketSet(getSpace(), getRocketLevel(), DeepSpace.CARGO);
        colorSet(R.id.rl1, DeepSpace.INACTIVE_LEVEL_COLOR);
        colorSet(R.id.rl2, DeepSpace.INACTIVE_LEVEL_COLOR);
        colorSet(R.id.rl3, DeepSpace.INACTIVE_LEVEL_COLOR);
    }

    public void rh(View view) {
        rocketSet(getSpace(), getRocketLevel(), DeepSpace.HATCH);
        colorSet(R.id.rl1, DeepSpace.INACTIVE_LEVEL_COLOR);
        colorSet(R.id.rl2, DeepSpace.INACTIVE_LEVEL_COLOR);
        colorSet(R.id.rl3, DeepSpace.INACTIVE_LEVEL_COLOR);
    }

    public void csc(View view) {
        cargoShipSet(getSpace(), getCargoLoc(), DeepSpace.CARGO);
        colorSet(R.id.cargoshipfront, DeepSpace.INACTIVE_LEVEL_COLOR);
        colorSet(R.id.cargoshipfront3, DeepSpace.INACTIVE_LEVEL_COLOR);
    }

    public void csh(View view) {
        cargoShipSet(getSpace(), getCargoLoc(), DeepSpace.HATCH);
        colorSet(R.id.cargoshipfront, DeepSpace.INACTIVE_LEVEL_COLOR);
        colorSet(R.id.cargoshipfront3, DeepSpace.INACTIVE_LEVEL_COLOR);
    }

    public void csf(View view) {
        if (getSpace().isMainStart()) {
            setCargoLoc('f');
            colorSet(R.id.cargoshipfront, DeepSpace.ACTIVE_LEVEL_COLOR);
            colorSet(R.id.cargoshipfront3, DeepSpace.INACTIVE_LEVEL_COLOR);
        } else makeADialog("You need to press start!", "setscore");
    }

    public void css(View view) {
        if (getSpace().isMainStart()) {
            setCargoLoc('c');
            colorSet(R.id.cargoshipfront3, DeepSpace.ACTIVE_LEVEL_COLOR);
            colorSet(R.id.cargoshipfront, DeepSpace.INACTIVE_LEVEL_COLOR);
        } else makeADialog("You need to press start!", "setscore");
    }

    public void rl1(View view) {
        if (getSpace().isMainStart()) {
            setRocketLevel(1);
            colorSet(R.id.rl1, DeepSpace.INACTIVE_LEVEL_COLOR);
            colorSet(R.id.rl2, DeepSpace.INACTIVE_LEVEL_COLOR);
            colorSet(R.id.rl3, DeepSpace.INACTIVE_LEVEL_COLOR);
            colorSet(R.id.rl1, DeepSpace.ACTIVE_LEVEL_COLOR);
        } else makeADialog("You need to press start!", "setscore");
    }

    public void rl2(View view) {
        if (getSpace().isMainStart()) {
            setRocketLevel(2);
            colorSet(R.id.rl1, DeepSpace.INACTIVE_LEVEL_COLOR);
            colorSet(R.id.rl2, DeepSpace.INACTIVE_LEVEL_COLOR);
            colorSet(R.id.rl3, DeepSpace.INACTIVE_LEVEL_COLOR);
            colorSet(R.id.rl2, DeepSpace.ACTIVE_LEVEL_COLOR);
        } else makeADialog("You need to press start!", "setscore");
    }

    public void rl3(View view) {
        if (getSpace().isMainStart()) {
            colorSet(R.id.rl1, DeepSpace.INACTIVE_LEVEL_COLOR);
            colorSet(R.id.rl2, DeepSpace.INACTIVE_LEVEL_COLOR);
            colorSet(R.id.rl3, DeepSpace.INACTIVE_LEVEL_COLOR);
            colorSet(R.id.rl3, DeepSpace.ACTIVE_LEVEL_COLOR);
            setRocketLevel(3);
        } else makeADialog("You need to press start!", "setscore");
    }

    public void defense(View view) {
        if (!getSpace().isMainStart()) {
            makeADialog("you need to press start!", "rocketfalse");
            ((Switch) view).setChecked(getSpace().isMainDefense());
        } else getSpace().setMainDefense(!getSpace().isMainDefense());
    }

    public void start1(View view) {
        getSpace().setMainStartPosition(1);
        colorSet(R.id.start2, DeepSpace.ACTIVE_START_COLOR);
        colorSet(R.id.start1, DeepSpace.INACTIVE_START_COLOR);
    }

    public void start2(View view) {
        getSpace().setMainStartPosition(2);
        colorSet(R.id.start1, DeepSpace.ACTIVE_START_COLOR);
        colorSet(R.id.start2, DeepSpace.INACTIVE_START_COLOR);
    }

    public void end1(View view) {
        if (getSpace().isMainStart()) {
            getSpace().setMainEndgame(1);
            colorSet(R.id.hab1, DeepSpace.INACTIVE_START_COLOR);
            colorSet(R.id.hab2, DeepSpace.INACTIVE_START_COLOR);
            colorSet(R.id.hab3, DeepSpace.INACTIVE_START_COLOR);
            colorSet(R.id.hab1, DeepSpace.ACTIVE_START_COLOR);
        } else makeADialog("You need to press start!", "setscore");
    }

    public void end2(View view) {
        if (getSpace().isMainStart()) {
            getSpace().setMainEndgame(2);
            colorSet(R.id.hab1, DeepSpace.INACTIVE_START_COLOR);
            colorSet(R.id.hab2, DeepSpace.INACTIVE_START_COLOR);
            colorSet(R.id.hab3, DeepSpace.INACTIVE_START_COLOR);
            colorSet(R.id.hab2, DeepSpace.ACTIVE_START_COLOR);
        } else makeADialog("You need to press start!", "setscore");
    }

    public void end3(View view) {
        if (getSpace().isMainStart()) {
            getSpace().setMainEndgame(3);
            colorSet(R.id.hab1, DeepSpace.INACTIVE_START_COLOR);
            colorSet(R.id.hab2, DeepSpace.INACTIVE_START_COLOR);
            colorSet(R.id.hab3, DeepSpace.INACTIVE_START_COLOR);
            colorSet(R.id.hab3, DeepSpace.ACTIVE_START_COLOR);
        } else makeADialog("You need to press start!", "setscore");
    }

    public void blockedScore(View view) {
        BlockedScoreThread thread = new BlockedScoreThread();
        Thread threadStart = new Thread(thread);
        threadStart.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkDataSpace();
        if (!getData().getSheet().getSheetID().equals("default")) {
            int rowNum = getData().getRowNumber();
            getSpace().infoSet(
                    getData().getSheet().matchValue(rowNum),
                    Integer.parseInt(getData().getSheet().teamValue(rowNum)),
                    getData().getSheet().nameValue(rowNum),
                    getData().getPerAlliance()
            );
        } else {
            getSpace().infoSet("0", 0, "0", "0");
        }
        updateDisplayInfo();
        dialogCheck();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putSerializable("DATA", getData());
        savedInstanceState.putSerializable("SPACE", getSpace());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setData((PersistentData) savedInstanceState.getSerializable("DATA"));
        setSpace((DeepSpace) savedInstanceState.getSerializable("SPACE"));
    }
}
