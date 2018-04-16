package javacoursework.roryfielding.androidapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by RoryFielding on 30/03/2018.
 */

public class Missile extends GameObject {

    private int score;
    private int speed;
    private Random rng = new Random();
    private Animation animation = new Animation();

    /**
     * Constructor for the missile class
     *
     * @param resource  bitmap
     * @param x         x position
     * @param y         y position
     * @param w         width
     * @param h         height
     * @param s         score
     * @param numFrames number of frames
     */
    public Missile(Bitmap resource, int x, int y, int w, int h, int s, int numFrames) {
        super.x = x;
        super.y = y;
        width = w;
        height = h;
        score = s;
        speed = 7 + (int) (rng.nextDouble() * score / 30); //speed of missile increases as game progresses

        //cap missile speed
        if (speed > 40) {
            speed = 40;
        }

        Bitmap[] image = new Bitmap[numFrames];

        this.bitmap = resource;

        for (int i = 0; i < image.length; i++) { //loop through image and assign array values to each separate image
            image[i] = Bitmap.createBitmap(bitmap, 0, i * height, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(100 - speed); //if missile faster, delay is less and spins faster
    }

    /**
     * Update function for the missiles
     * Increase speed in relation to the score
     */
    public void update() {
        x -= speed;
        animation.update();
    }

    /**
     * Draw function for the missiles
     *
     * @param canvas Cavnas
     */
    public void draw(Canvas canvas) {
        try {
            canvas.drawBitmap(animation.getImage(), x, y, null);
        } catch (Exception e) {

        }
    }

    /**
     * Get width offset of missiles
     *
     * @return width - 10
     */
    @Override
    public int getWidth() {
        //offset slightly for better collision detection
        return width - 10;
    }


}


