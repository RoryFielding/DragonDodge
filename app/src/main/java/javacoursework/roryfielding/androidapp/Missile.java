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
    private Bitmap spritesheet;

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

        spritesheet = resource;

        for (int i = 0; i < image.length; i++) { //loop through image and assign array values to each separate image
            image[i] = Bitmap.createBitmap(spritesheet, 0, i * height, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(100 - speed); //if missile faster, delay is less and spins faster
    }
        public void update(){
            x -= speed;
            animation.update();
    }

    public void draw(Canvas canvas){
            try{
                canvas.drawBitmap(animation.getImage(), x, y, null);
            }catch (Exception e){

            }
    }

    @Override
    public int getWidth(){
        //offset slightly for better collision detection
        return  width - 10;
    }


}


