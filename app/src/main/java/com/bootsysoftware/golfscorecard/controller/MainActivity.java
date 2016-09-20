package com.bootsysoftware.golfscorecard.controller;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    HoleAdapter adapter;

    //hold the hole objects
    private static Hole[] mHoles = new Hole[18];
    //bind views
    public static TextView mNumberTotalStrokesLabel;
    @BindView(android.R.id.list) ListView mListView;
    @BindView(android.R.id.empty) TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mNumberTotalStrokesLabel = (TextView) findViewById(R.id.numberTotalStrokesLabel);

        mSharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        //initialize the array of Hole objects
        populateHoles();
        //Create and set the my ListView to use my custom adapter
        adapter = new HoleAdapter(this, mHoles);
        mListView.setAdapter(adapter);
        mListView.setEmptyView(mEmptyTextView);
    }

    @Override
    protected void onPause() {
        super.onPause();

        for(int i = 0; i < mHoles.length; i++){
            mEditor.putInt(KEY_STROKECOUNT + i, mHoles[i].getNumStrokes());
        }
        mEditor.commit();
    }

    // populate Hole objects and set the label for the TotalStrokes
    private void populateHoles() {
        int strokes = 0;
        for(int i = 0; i < mHoles.length; i++){
            strokes = mSharedPreferences.getInt(KEY_STROKECOUNT + i, 0);
            mHoles[i] = new Hole(i+1, strokes);
        }
        mNumberTotalStrokesLabel.setText(Hole.totalStrokes + "");
    }//end populateHoles

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_clear_strokes) {
            //clear out the editor, and reset values for numStrokes and totalStrokes
            mEditor.clear().apply();

            for(Hole hole : mHoles){
                hole.setNumStrokes(0);
            }
            Hole.resetStrokes();
            mNumberTotalStrokesLabel.setText(Hole.totalStrokes + "");
            adapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }//end onOptionsItemSelected
}//end class
