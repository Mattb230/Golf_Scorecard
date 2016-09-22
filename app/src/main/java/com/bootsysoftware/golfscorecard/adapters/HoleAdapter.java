package com.bootsysoftware.golfscorecard.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bootsysoftware.golfscorecard.R;
import com.bootsysoftware.golfscorecard.controller.MainActivity;
import com.bootsysoftware.golfscorecard.model.Hole;

/**
 * Created by Matthew Boydston on 9/19/2016.
 */
public class HoleAdapter extends BaseAdapter{
    private Context mContext;
    private Hole[] mHoles;
    //private static int mTotalStrokes;


    public HoleAdapter(Context context, Hole[] holes) {
        mContext = context;
        mHoles = holes;
    }

    @Override
    public int getCount() {
        return mHoles.length;
    }

    @Override
    public Object getItem(int position) {
        return mHoles[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        //Check to see if this view is brand new. If it is, create everything and inflate
        if(convertView == null){
            //inflate view using the layout I created "holes_list_item.xml"
            convertView = LayoutInflater.from(mContext).inflate(R.layout.holes_list_item, null);
            holder = new ViewHolder();
            holder.holeNumberLabel = (TextView) convertView.findViewById(R.id.holeNumberLabel);
            holder.numberOfstrokesLabel = (TextView) convertView.findViewById(R.id.numberOfStrokesLabel);
            holder.minusButton = (Button) convertView.findViewById(R.id.minusButton);
            holder.plusButton = (Button) convertView.findViewById(R.id.plusButton);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.holeNumberLabel.setText("Hole " + mHoles[position].getHoleNumber());
        holder.numberOfstrokesLabel.setText(mHoles[position].getNumStrokes() + "");
        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int updatedStrokeCount = mHoles[position].getNumStrokes() - 1;
                //if the current strokes are 0, don't subtract from total strokes if the user keeps
                //pressing the "-" button
                if(mHoles[position].getNumStrokes() > 0){
                    Hole.removeStroke();
                }

                //if the updated count is less than 0, keep it as 0
                if (updatedStrokeCount < 0) updatedStrokeCount = 0;
                if (Hole.totalStrokes < 0) Hole.totalStrokes = 0;
                //update model
                mHoles[position].setNumStrokes(updatedStrokeCount);
                // Update views
                holder.numberOfstrokesLabel.setText(mHoles[position].getNumStrokes() + "");
                MainActivity.mNumberTotalStrokesLabel.setText(Hole.totalStrokes + "");
            }
        });
        holder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int updatedStrokeCount = mHoles[position].getNumStrokes() + 1;
                Hole.addStroke();
                //update model

                mHoles[position].setNumStrokes(updatedStrokeCount);
                // Update views
                holder.numberOfstrokesLabel.setText(mHoles[position].getNumStrokes() + "");
                MainActivity.mNumberTotalStrokesLabel.setText(Hole.totalStrokes + "");
            }
        });

        return convertView;
    }

    private static class ViewHolder{
        TextView holeNumberLabel;
        TextView numberOfstrokesLabel;
        Button minusButton;
        Button plusButton;
    }

}
