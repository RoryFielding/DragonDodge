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

    /**
     * Constructor for the Animation class
     */
    Animation() {

    }

    /**
     * Set the frames for the animation to run
     *
     * @param frames frame to set
     */
    public void setFrames(Bitmap[] frames) {

        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();

    }

    /**
     * Set the delay for the animation to run
     *
     * @param d delay time
     */
    public void setDelay(long d) {
        delay = d;
    }

    /**
     * Set the frame to the frame given in the parameter
     *
     * @param f frame number
     */
    public void setFrame(int f) {
        currentFrame = f;
    }

    /**
     * Update the animation
     */
    public void update() {

        long elapsed = (System.nanoTime() - startTime) / 1000000;

        if (elapsed > delay) { //determine which image in array to return
            currentFrame++;
            startTime = System.nanoTime();
        }

        if (currentFrame == frames.length) {
            currentFrame = 0;
            playedOnce = true;
        }
    }

    /**
     * Function to determine what the player class draws
     *
     * @return frame array with current frame
     */
    public Bitmap getImage() {
        //what player class draws is determined by this method
        return frames[currentFrame];
    }

    /**
     * Getter for current frame
     *
     * @return currentFrame
     */
    public int getFrame() {
        return currentFrame;
    }

    /**
     * Function to determine whether the animation has played once
     *
     * @return playedOnce if animation has played
     */
    public boolean playedOnce() {
        return playedOnce;
    }

}
