package javacoursework.roryfielding.androidapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by RoryFielding on 30/03/2018.
 */

public class Player extends GameObject  {

    private Bitmap spritesheet;
    private int score;
    private boolean up;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;

    public Player(Bitmap resource, int w, int h, int numFrames){
        x = 100;
        y = GameView.HEIGHT/2;
        dy = 0;
        score = 0;
        height = h;
        width = w;

        Bitmap[] image = new Bitmap[numFrames]; //bitmap array to store sprites for player
        spritesheet = resource;

        for(int i = 0; i < image.length; i++){
           image[i] = Bitmap.createBitmap(spritesheet, i * (int)width, 0, (int)width , height);
        }

        animation.setFrames(image); //pass aray to animations class
        animation.setDelay(10);
        startTime = System.nanoTime();
    }

    public void setUp(boolean b){ //called by motiion event
        up = b;
    }

    public void update(){
        long elapsed = (System.nanoTime() - startTime)/1000000;
        if (elapsed > 100)
        {
            if(GameView.progressDenominator == 20){
            score ++; //every 100 millisecs increment score
            startTime = System.nanoTime();
            }
            if(GameView.progressDenominator == 10){
                score++;  //every 100 millisecs increment score
                score++;
                startTime = System.nanoTime();
            }
            if(GameView.progressDenominator == 1){
                score++;  //every 100 millisecs increment score
                score++;
                startTime = System.nanoTime();
            }
        }
        animation.update();
        if (up){
            dy -= 0.3; //if pressing down on screen, accelerate
        }
        else {
            dy += 0.3; //if not pressing down, decelerate
        }

        if (dy > 10) dy = 10; //cap speed
        if (dy <= -10) dy = -10; //cap speed

        y += dy*2;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }

    public int getScore(){
        return score;
    }

    public boolean getPlaying(){
        return playing;
    }

    public void setPlaying(boolean b){
        playing = b;
    }

    public void resetDY(){
        dy = 0 ;
    }

    public void resetScore(){
        score = 0;
    }
}
