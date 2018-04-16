package javacoursework.roryfielding.androidapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by RoryFielding on 30/03/2018.
 */

public class TopBorder extends GameObject {

    /**
     * Constructor for the top border
     *
     * @param resource bitmap
     * @param x        x position
     * @param y        y position
     * @param h        height
     */
    public TopBorder(Bitmap resource, int x, int y, int h) {

        height = h;
        width = 20;

        this.x = x;
        this.y = y;
        this.bitmap = resource;

        dx = GameView.moveSpeed;
        bitmap = Bitmap.createBitmap(resource, 0, 0, width, height);
    }

    /**
     * Function to update border by vector
     */
    public void update() {

        x += dx;
    }

    /**
     * Function to draw the top border
     *
     * @param canvas Canvas
     */
    public void draw(Canvas canvas) {

        try {
            canvas.drawBitmap(bitmap, x, y, null);
        } catch (Exception e) {
            //catch exceptions
        }
    }

}
