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

import com.bootsysoftware.golfscorecard.R;
import com.bootsysoftware.golfscorecard.adapters.HoleAdapter;
import com.bootsysoftware.golfscorecard.model.Hole;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends ListActivity {

    private static final String PREFS_FILE = "com.bootsysoftware.golfscorecard.preferences";
    private static final String KEY_STROKECOUNT = "KEY_STROKECOUNT";
    private static final String KEY_TOTALSTROKES = "KEY_TOTALSTROKES";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    HoleAdapter adapter;
    public int totalStrokes = 0;

    //hold the hole objects
    private static Hole[] mHoles = new Hole[18];
    //bind views
    public static TextView mNumberTotalStrokesLabel;
    @BindView(android.R.id.list) ListView mListView;
    @BindView(android.R.id.empty) TextView mEmptyTextView;
    //@BindView(R.id.numberTotalStrokesLabel) TextView mNumberTotalStrokesLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mNumberTotalStrokesLabel = (TextView) findViewById(R.id.numberTotalStrokesLabel);

        //initialize the array of Hole objects
        populateHoles();
        //Create and set the my ListView to use my custom adapter
        adapter = new HoleAdapter(this, mHoles);
        mListView.setAdapter(adapter);
        mListView.setEmptyView(mEmptyTextView);



        //mNumberTotalStrokesLabel.setText(mHoles[0].getTotalStrokes() + "");


    }

    @Override
    protected void onPause() {
        super.onPause();

        for(int i = 0; i < mHoles.length; i++){
            mEditor.putInt(KEY_STROKECOUNT + i, mHoles[i].getNumStrokes());
        }
        mEditor.putInt(KEY_TOTALSTROKES, HoleAdapter.getTotalStrokes());
        mEditor.apply();
    }

    private void populateHoles() {
        int strokes = 0;
        for(int i = 0; i < mHoles.length; i++){
            strokes = mSharedPreferences.getInt(KEY_STROKECOUNT + i, 0);
                mHoles[i] = new Hole(i+1, strokes);
        }
        totalStrokes = mSharedPreferences.getInt(KEY_TOTALSTROKES, 0);
        mNumberTotalStrokesLabel.setText(totalStrokes + "");
    }//end populateHoles

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear_strokes) {
            //clears all scores
            mEditor.clear();
            //applies the changes
            mEditor.apply();
            for(Hole hole : mHoles){
                hole.setNumStrokes(0);
            }
            totalStrokes = 0;
            mNumberTotalStrokesLabel.setText(totalStrokes + "");
            adapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static int getHolesLength(){
        return mHoles.length;
    }

}
