package javacoursework.roryfielding.androidapp;

import android.graphics.Bitmap;

/**
 * Created by RoryFielding on 30/03/2018.
 */

public class Animation {

    private Bitmap[] frames;
    private int currentFrame;
    private long startTime;
    private long delay;
    private boolean playedOnce;


    Animation(){

    }

    public void setFrames(Bitmap [] frames){

        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();

    }

    public void setDelay(long d){
        delay = d;
    }

    public void setFrame(int f){
        currentFrame = f;
    }

    public void update(){

        long elapsed = (System.nanoTime() - startTime)/1000000;

        if (elapsed > delay){ //determine which image in array to return
            currentFrame++;
            startTime = System.nanoTime();
        }

        if(currentFrame == frames.length){
            currentFrame = 0;
            playedOnce = true;
        }
    }

    public Bitmap getImage(){
        //what player class draws is determined by this method
        return frames[currentFrame];
    }

    public int getFrame(){
        return currentFrame;
    }

    public boolean playedOnce(){
        return playedOnce;
    }

}
