package javacoursework.roryfielding.androidapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by RoryFielding on 31/03/2018.
 */

public class Explosion {

    private int x, y, width, height, row;
    private Animation animation = new Animation();
    private Bitmap spritesheet;

    public Explosion(Bitmap resource, int x, int y, int w, int h, int numFrames){
        this.x = x;
        this.y= y;
        this.width = w;
        this.height = h;

        Bitmap [] image = new Bitmap[numFrames];

        spritesheet = resource;

        for(int i = 0; i < image.length; i++){ //assign bitmap array to frames of explosion
            if(i%5 == 0 & i > 0){
                row ++;
                image[i] = Bitmap.createBitmap(spritesheet, ( i - (5 * row)) * width, row * height, width, height);
            }

        }
        animation.setFrames(image);
        animation.setDelay(10);

    }

    public void draw(Canvas canvas){
        if(!animation.playedOnce()){ //only draw through once
         canvas.drawBitmap(animation.getImage(), x, y, null);
        }

    }

    public void update(){
        if(!animation.playedOnce()){ //only play the explosion one time
            animation.update();
        }
    }

    public int getHeight(){
        return height;
    }

}
