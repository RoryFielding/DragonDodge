package javacoursework.roryfielding.androidapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by RoryFielding on 30/03/2018.
 */

public class Player extends GameObject {

    private int score;
    private boolean up;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;

    /**
     * Constructor for the player class
     *
     * @param resource  bitmap
     * @param w         width
     * @param h         height
     * @param numFrames number of frames
     */
    public Player(Bitmap resource, int w, int h, int numFrames) {
        x = 100;
        y = GameView.HEIGHT / 2;
        dy = 0;
        score = 0;
        height = h;
        width = w;

        Bitmap[] image = new Bitmap[numFrames]; //bitmap array to store sprites for player
        this.bitmap = resource;

        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(bitmap, i * (int) width, 0, (int) width, height);
        }

        animation.setFrames(image); //pass aray to animations class
        animation.setDelay(10);
        startTime = System.nanoTime();
    }

    /**
     * Function to tell whether playing is moving up or not
     *
     * @param b boolean value for up
     */
    public void setUp(boolean b) { //called by motiion event
        up = b;
    }

    /**
     * Update function for the game
     * Depending on the difficulty selected, update score at variable rates
     * Update animation and cap speed
     */
    public void update() {
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if (elapsed > 100) {
            if (GameView.progressDenominator == 20) {
                score++; //every 100 millisecs increment score
                startTime = System.nanoTime();
            }
            if (GameView.progressDenominator == 10) {
                score++;  //every 100 millisecs increment score
                score++;
                startTime = System.nanoTime();
            }
            if (GameView.progressDenominator == 1) {
                score++;  //every 100 millisecs increment score
                score++;
                startTime = System.nanoTime();
            }
        }
        animation.update();
        if (up) {
            dy -= 0.3; //if pressing down on screen, accelerate
        } else {
            dy += 0.3; //if not pressing down, decelerate
        }

        if (dy > 10) dy = 10; //cap speed
        if (dy <= -10) dy = -10; //cap speed

        y += dy * 2;
    }

    /**
     * Draw function for the player
     *
     * @param canvas Canvas
     */
    public void draw(Canvas canvas) {
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }

    /**
     * Get score
     *
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Get playing
     *
     * @return playing
     */
    public boolean getPlaying() {
        return playing;
    }

    /**
     * Set playing
     *
     * @param b playing
     */
    public void setPlaying(boolean b) {
        playing = b;
    }

    /**
     * Reset the player's vector
     */
    public void resetDY() {
        dy = 0;
    }

    /**
     * Reset the player's score
     */
    public void resetScore() {
        score = 0;
    }
}
