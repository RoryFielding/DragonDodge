package javacoursework.roryfielding.androidapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by RoryFielding on 30/03/2018.
 */

public class Background {

    private Bitmap image; //image of background
    private int x, y, dx; //x pos, y pos, vector

    public Background(Bitmap resource){
        image = resource;
        dx = GameView.moveSpeed;
        //set image to resource file
    }

    public void update(){
        x += dx; //make the background scroll
        if(x < -GameView.WIDTH){ //if image off the screen
            x=0; //reset
        }

    }

    public  void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null); //draw the background
        if(x<0){ //if part of image off screen, draw second image after
            canvas.drawBitmap(image, x + GameView.WIDTH, y, null);
        }
    }

}
