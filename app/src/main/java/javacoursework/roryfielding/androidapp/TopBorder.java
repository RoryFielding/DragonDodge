package javacoursework.roryfielding.androidapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by RoryFielding on 30/03/2018.
 */

public class TopBorder extends GameObject {

    private Bitmap image;

    public TopBorder(Bitmap resource, int x, int y, int h){

        height = h;
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

        try{canvas.drawBitmap(image, x, y, null);}catch (Exception e){
            //catch exceptions
        }
    }

}
