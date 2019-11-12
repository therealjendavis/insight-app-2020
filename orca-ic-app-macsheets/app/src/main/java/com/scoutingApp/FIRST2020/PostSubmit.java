package com.scoutingApp.FIRST2020;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class PostSubmit extends AppCompatActivity {
    // inherited class objects

    public DeepSpace getSpace() {
        return (DeepSpace) getIntent().getSerializableExtra("Game2");
    }
    public PersistentData getData() {
        return (PersistentData) getIntent().getSerializableExtra("data2");
    }
    public SubmittedData sub = new SubmittedData();
    public SubmittedData getSub() { return this.sub; }

    // methods to update the content of the UI

    private void updateTextView(String content, int id){
        TextView nametext = findViewById(id);
        nametext.setText(content);
    }
    private void info() {
        updateTextView(getSpace().getInfo().getName(), R.id.name);
        updateTextView(getSpace().getInfo().getAlliance(), R.id.alliance);
        updateTextView(Integer.toString(getSpace().getInfo().getTeam()), R.id.team);
        updateTextView((getSpace().getInfo().getMatch()), R.id.match);
    }

    // various methods called on submit

    public static class Dialogs4 extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder name = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
            name.setMessage("Please add the final alliance score or a \"0\" placeholder!")
                    .setNegativeButton(R.string.okiedokes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Objects.requireNonNull(PostSubmit.Dialogs4.this.getDialog()).dismiss();
                        }
                    });
            return name.create();
        }
    }
    public String newString(int id) {
        TextView text = findViewById(id);
        return text.getText().toString();
    }
    public void goHome() {
        Intent main = new Intent(this, MainActivity.class);
        main.putExtra("data4", getData());
        startActivity(main);
    }
    public void toSubmission() {
        getSub().setMainStartPosition(getSpace().getMainStartPosition());
        getSub().setMainDefense(getSpace().isMainDefense());
        getSub().setMainBlockedScores(getSpace().getMainBlockedScores());
        getSub().setMainEndgame(getSpace().getMainEndgame());
        getSub().setExtrasRedCard(getSpace().isExtrasRedCard());
        getSub().setExtrasYellowCard(getSpace().isExtrasYellowCard());
        getSub().setNoShow(getSpace().isNoShow());
        getSub().setMovement(getSpace().isMovement());
        getSub().setExtrasFinalScore(getSpace().getExtrasFinalScore());
        getSub().setTeam(getSpace().getMainTeam());
        getSub().setMatch(getSpace().getInfo().getMatch());
        getSub().setName(getSpace().getMainName());
        getSub().setNotes(getSpace().getExtrasNotes());
        getSub().setAlliance(getSpace().getMainAlliance());
        getSub().setCSFH(getSpace().getCargo().getScore('f', DeepSpace.HATCH, false));
        getSub().setCSFC(getSpace().getCargo().getScore('f', DeepSpace.CARGO, false));
        getSub().setCSSH(getSpace().getCargo().getScore('c', DeepSpace.HATCH, false));
        getSub().setCSSC(getSpace().getCargo().getScore('c', DeepSpace.CARGO, false));
        getSub().setCSFHSS(getSpace().getCargo().getScore('f', DeepSpace.HATCH, true));
        getSub().setCSFCSS(getSpace().getCargo().getScore('f', DeepSpace.CARGO, true));
        getSub().setCSSHSS(getSpace().getCargo().getScore('c', DeepSpace.HATCH, true));
        getSub().setCSSCSS(getSpace().getCargo().getScore('c', DeepSpace.CARGO, true));
        getSub().setR1H(getSpace().getRocket().getScore(1, DeepSpace.HATCH, false));
        getSub().setR1C(getSpace().getRocket().getScore(1, DeepSpace.CARGO, false));
        getSub().setR2H(getSpace().getRocket().getScore(2, DeepSpace.HATCH, false));
        getSub().setR2C(getSpace().getRocket().getScore(2, DeepSpace.CARGO, false));
        getSub().setR3H(getSpace().getRocket().getScore(3, DeepSpace.HATCH, false));
        getSub().setR3C(getSpace().getRocket().getScore(3, DeepSpace.CARGO, false));
        getSub().setR1HSS(getSpace().getRocket().getScore(1, DeepSpace.HATCH, true));
        getSub().setR1CSS(getSpace().getRocket().getScore(1, DeepSpace.CARGO, true));
        getSub().setR2HSS(getSpace().getRocket().getScore(2, DeepSpace.HATCH, true));
        getSub().setR2CSS(getSpace().getRocket().getScore(2, DeepSpace.CARGO, true));
        getSub().setR3HSS(getSpace().getRocket().getScore(3, DeepSpace.HATCH, true));
        getSub().setR3CSS(getSpace().getRocket().getScore(3, DeepSpace.CARGO, true));
    }

    //button method

    public void submitButtonPageTwo(View view) {
        if (!newString(R.id.typescorehere).equals("")) {
            getSpace().setMainName(newString(R.id.name));
            getSpace().setMainTeam(Integer.valueOf(newString(R.id.team)));
            getSpace().setMainMatch(newString(R.id.match));
            getSpace().setMainAlliance(newString(R.id.alliance));
            getSpace().setExtrasFinalScore(Integer.valueOf(newString(R.id.typescorehere)));
            toSubmission();
            getData().perSubData.add(getSub());
            getData().perCacheData.add(getSpace().getInfo());
            getData().setRowNumber(getData().getRowNumber() + 1);
            goHome();
        }
        else {DialogFragment newFragment = new Dialogs4();
            newFragment.show(getSupportFragmentManager(), "STOP");}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_submit);
        info();
    }
}
