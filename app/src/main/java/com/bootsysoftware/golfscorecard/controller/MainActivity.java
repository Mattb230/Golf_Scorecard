package com.bootsysoftware.golfscorecard.controller;

import android.app.ListActivity;
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
        //initialize the array of Hole objects
        populateHoles();
        //Create and set the my ListView to use my custom adapter
        HoleAdapter adapter = new HoleAdapter(this, mHoles);
        mListView.setAdapter(adapter);
        mListView.setEmptyView(mEmptyTextView);


    }

    private void populateHoles() {
       // mHoles = new Hole[17];
        for(int i = 0; i < mHoles.length; i++){
            mHoles[i] = new Hole(i+1, 0);
        }

    }

}
