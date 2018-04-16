package javacoursework.roryfielding.androidapp;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Created by RoryFielding on 30/03/2018.
 */

public abstract class GameObject {

    protected int x, y, width, height; //x pos, y post, width and height
    protected float dx, dy; //x vector, y vector,
    protected Bitmap bitmap;

    /**
     * Set x position
     *
     * @param x x position
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set y position
     *
     * @param y y position
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Get x position
     *
     * @return x psotion
     */
    public int getX() {
        return x;
    }

    /**
     * Get y position
     *
     * @return y position
     */
    public int getY() {
        return y;
    }

    /**
     * Get height
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get width
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * This function returns the rectangle of the game object, in order to use for collsion
     */

    public Rect getRectangle() { //get rectangle for collisions
        return new Rect(x, y, x + width, y + height);
    }

}
