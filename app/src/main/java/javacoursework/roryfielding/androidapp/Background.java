package javacoursework.roryfielding.androidapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by RoryFielding on 30/03/2018.
 */

public class Background {

    private Bitmap image; //image of background
    private int x, y, dx; //x pos, y pos, vector

    /**
     * Constructor for the background class
     *
     * @param resource Bitmap image
     */
    public Background(Bitmap resource) {
        image = resource;
        dx = GameView.moveSpeed;
        //set image to resource file
    }

    /**
     * Update function for the background
     * Move the background left creating a scrolling effect
     * If image off screen, reset
     */
    public void update() {
        x += dx; //make the background scroll
        if (x < -GameView.WIDTH) { //if image off the screen
            x = 0; //reset
        }

    }

    /**
     * Draw function for the background
     * If image moves off screen, draw another image afterwards
     *
     * @param canvas Canvas
     */
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null); //draw the background
        if (x < 0) { //if part of image off screen, draw second image after
            canvas.drawBitmap(image, x + GameView.WIDTH, y, null);
        }
    }

}
