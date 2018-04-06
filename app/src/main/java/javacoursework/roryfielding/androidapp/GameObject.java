package javacoursework.roryfielding.androidapp;

import android.graphics.Rect;

/**
 * Created by RoryFielding on 30/03/2018.
 */

public abstract class GameObject {

    protected int x, y, width, height; //x pos, y post, width and height
    protected float dx, dy; //x vector, y vector,

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public Rect getRectangle(){ //get rectangle for collisions
        return new Rect(x, y, x+width, y+height);
    }

}
