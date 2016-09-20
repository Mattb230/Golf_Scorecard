package com.bootsysoftware.golfscorecard.controller;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bootsysoftware.golfscorecard.R;
import com.bootsysoftware.golfscorecard.adapters.HoleAdapter;
import com.bootsysoftware.golfscorecard.model.Hole;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends ListActivity {

    private static final String PREFS_FILE = "com.bootsysoftware.golfscorecard.preferences";
    private static final String KEY_STROKECOUNT = "KEY_STROKECOUNT";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    //hold the hole objects
    private Hole[] mHoles = new Hole[18];
    //bind views
    @BindView(android.R.id.list) ListView mListView;
    @BindView(android.R.id.empty) TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        //initialize the array of Hole objects
        populateHoles();
        //Create and set the my ListView to use my custom adapter
        HoleAdapter adapter = new HoleAdapter(this, mHoles);
        mListView.setAdapter(adapter);
        mListView.setEmptyView(mEmptyTextView);


    }

    @Override
    protected void onPause() {
        super.onPause();

        for(int i = 0; i < mHoles.length; i++){
            mEditor.putInt(KEY_STROKECOUNT + i, mHoles[i].getNumStrokes());
        }
        mEditor.apply();
    }

    private void populateHoles() {
        int strokes = 0;
       // mHoles = new Hole[17];
        for(int i = 0; i < mHoles.length; i++){
            strokes = mSharedPreferences.getInt(KEY_STROKECOUNT + i, 0);
            mHoles[i] = new Hole(i+1, strokes);
        }

    }

}
