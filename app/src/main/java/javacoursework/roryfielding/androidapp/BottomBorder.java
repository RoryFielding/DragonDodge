package javacoursework.roryfielding.androidapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by RoryFielding on 30/03/2018.
 */

public class BottomBorder extends GameObject {

    /**
     * Constructor for the bottom border
     *
     * @param resource bitmap
     * @param x        x position
     * @param y        y position
     */
    public BottomBorder(Bitmap resource, int x, int y) {
        height = 200;
        width = 20;

        this.x = x;
        this.y = y;
        dx = GameView.moveSpeed;
        this.bitmap = resource;
        Bitmap.createBitmap(bitmap, 0, 0, width, height);
    }

    /**
     * Update function for the border
     * Increment x by vector
     */
    public void update() {
        x += dx;
    }

    /**
     * Draw function for the border
     *
     * @param canvas Canvas
     */
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
    }
}
