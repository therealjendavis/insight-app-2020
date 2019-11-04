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
    public DeepSpace getSpace() {
        return (DeepSpace) getIntent().getSerializableExtra("Game2");
    }
    public PersistentData getData() {
        return (PersistentData) getIntent().getSerializableExtra("data2");
    }
    public SubmittedData sub = new SubmittedData();
    public SubmittedData getSub() { return this.sub; }
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
        getSub().setCSFH(getSpace().getCargo().getMainCSFH());
        getSub().setCSFC(getSpace().getCargo().getMainCSFC());
        getSub().setCSSH(getSpace().getCargo().getMainCSSH());
        getSub().setCSSC(getSpace().getCargo().getMainCSSC());
        getSub().setCSFHSS(getSpace().getCargo().getMainCSFHSS());
        getSub().setCSFCSS(getSpace().getCargo().getMainCSFCSS());
        getSub().setCSSHSS(getSpace().getCargo().getMainCSSHSS());
        getSub().setCSSCSS(getSpace().getCargo().getMainCSSCSS());
        getSub().setR1H(getSpace().getRocket().getMainR1H());
        getSub().setR1C(getSpace().getRocket().getMainR1C());
        getSub().setR2H(getSpace().getRocket().getMainR2H());
        getSub().setR2C(getSpace().getRocket().getMainR2C());
        getSub().setR3H(getSpace().getRocket().getMainR3H());
        getSub().setR3C(getSpace().getRocket().getMainR3C());
        getSub().setR1HSS(getSpace().getRocket().getMainR1HSS());
        getSub().setR1CSS(getSpace().getRocket().getMainR1CSS());
        getSub().setR2HSS(getSpace().getRocket().getMainR2HSS());
        getSub().setR2CSS(getSpace().getRocket().getMainR2CSS());
        getSub().setR3HSS(getSpace().getRocket().getMainR3HSS());
        getSub().setR3CSS(getSpace().getRocket().getMainR3CSS());
    }
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
