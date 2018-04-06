package javacoursework.roryfielding.androidapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by RoryFielding on 30/03/2018.
 */

public class BottomBorder extends GameObject{

    private Bitmap image;

    public BottomBorder(Bitmap resource, int x, int y){
        height = 200;
        width = 20;

        this.x = x;
        this.y = y;
        dx = GameView.moveSpeed;

        image = Bitmap.createBitmap(resource, 0, 0, width, height);
    }

    public void update(){
        x += dx;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
    }
}
