package com.scoutingApp.FIRST2020;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AdditionalInfo extends AppCompatActivity {
    // inherited class objects

    public DeepSpace getSpace() {
        return (DeepSpace) getIntent().getSerializableExtra("Game4");
    }
    public PersistentData getData() {
        return (PersistentData) getIntent().getSerializableExtra("data4");
    }

    // button and switch methods

    public void redSwitch(View view) {
                if (!getSpace().isExtrasRedCard()) {
                getSpace().setExtrasRedCard(true);}
                else {getSpace().setExtrasRedCard(false);}
    }
    public void yellowSwitch(View view) {
                if (!getSpace().isExtrasYellowCard()) {
                    getSpace().setExtrasYellowCard(true);}
                else {getSpace().setExtrasYellowCard(false);}
    }
    public void noShowSwitch(View view) {
                if (!getSpace().isNoShow()) {
                    getSpace().setNoShow(true);}
                else {getSpace().setNoShow(false);}
    }
    public void moveSwitch(View view) {
        if (getSpace().isMovement()) { getSpace().setMovement(false); }
        else {getSpace().setMovement(true);}
    }
    public void backButton(View view) {
        EditText notes = findViewById(R.id.notes);
        getSpace().setExtrasNotes(notes.getText().toString());
        Intent backHome = new Intent(this, MainActivity.class);
        backHome.putExtra("game5", getSpace());
        backHome.putExtra("data5", getData());
        startActivity(backHome);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addinfo);
    }
}
