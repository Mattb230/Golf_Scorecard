package com.bootsysoftware.golfscorecard.model;

/**
 * Created by Matthew Boydston on 9/19/2016.
 */
public class Hole {
    private int mHoleNumber;
    private int mNumStrokes;

    public static int totalStrokes = 0;

    public Hole () {}

    public Hole(int holeNumber, int numStrokes) {
        mHoleNumber = holeNumber;
        mNumStrokes = numStrokes;
    }

    public int getHoleNumber() {
        return mHoleNumber;
    }

    public void setHoleNumber(int holeNumber) {
        mHoleNumber = holeNumber;
    }

    public int getNumStrokes() {
        return mNumStrokes;
    }

    public void setNumStrokes(int numStrokes) {
        mNumStrokes = numStrokes;
    }

    public static void addStroke(){
        totalStrokes++;
    }
    public static void removeStroke(){
        totalStrokes--;
    }
    public static void resetStrokes(){
        totalStrokes = 0;
    }

}
