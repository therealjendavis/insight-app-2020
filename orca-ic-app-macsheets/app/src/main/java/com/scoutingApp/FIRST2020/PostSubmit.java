package com.scoutingApp.FIRST2020;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.google.api.services.sheets.v4.SheetsScopes;

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
        nametext.setHint(content);
        nametext.setText(content);
    }

    private void info() {
        updateTextView(getSpace().getInfo().getName(), R.id.name);
        updateTextView(getSpace().getInfo().getAlliance(), R.id.alliance);
        updateTextView(Integer.toString(getSpace().getInfo().getTeam()), R.id.team);
        updateTextView((getSpace().getInfo().getMatch()), R.id.match);
    }
    public String newString(int id) {
        TextView text = findViewById(id);
        return text.getText().toString();
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
        getSpace().setMainName(newString(R.id.name));
        getSpace().setMainTeam(Integer.valueOf(newString(R.id.team)));
        getSpace().setMainMatch(newString(R.id.match));
        getSpace().setMainAlliance(newString(R.id.alliance));
        toSubmission();
        getData().getSheet().setValues(getSub().setValues());
        Intent signInIntent = googleClient.getSignInIntent();
        startActivityForResult(signInIntent, 1);
        if (!getData().sender()) {
            getData().perSubData.add(getSub());
        }
        getData().perCacheData.add(getSpace().getInfo());
        getData().setRowNumber(getData().getRowNumber() + 1);
        Intent back = new Intent(this, MainActivity.class);
        back.putExtra("data4", getData());
        startActivity(back);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_submit);
        info();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(SheetsScopes.SPREADSHEETS_READONLY))
                .requestScopes(new Scope(SheetsScopes.SPREADSHEETS))
                .requestIdToken("782050499682-o0e2ebf3q5fdh34pti8o5a9t0a5llnvp.apps.googleusercontent.com")
                .requestServerAuthCode("782050499682-o0e2ebf3q5fdh34pti8o5a9t0a5llnvp.apps.googleusercontent.com")
                .requestEmail()
                .build();
        googleClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData().getSheet().setAccount(GoogleSignIn.getLastSignedInAccount(this));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            getData().getSheet().handleSignInResult(task);
        }
    }
}
