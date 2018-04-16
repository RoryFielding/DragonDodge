package javacoursework.roryfielding.androidapp;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by RoryFielding on 09/04/2018.
 */

public class SecondaryThread extends Thread {

    private int FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    public static Canvas canvas;

    /**
     * Constructor for the secondary thread
     *
     * @param surfaceHolder SurfaceHolder
     * @param gameView      GameView
     */
    SecondaryThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    /**
     * Override run function for the thread
     * Set FPS and draw/update game
     */
    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCt = 0;
        long targetTime = 1000 / FPS; //each time we run through the game loop
        //this should take 1000/30 milliseconds

        while (running) {
            startTime = System.nanoTime();
            canvas = null;

            //try to lock the canvas for pixel editing

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    //each time we go through game loop
                    this.gameView.update(); //update game once
                    this.gameView.draw(canvas); //draw the game once
                    //this should be called 30 times a second
                }
            } catch (Exception e) {
                //catch exceptions
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                        {

                        }
                    }
                }
                timeMillis = System.nanoTime() - startTime / 1000000; //how many millisecs to update and draw game
                waitTime = targetTime - timeMillis; //target to get update/draw 1/30th of a second

                try {
                    this.sleep(waitTime);
                } catch (Exception e) {
                    //catch expections
                }

                totalTime += System.nanoTime() - startTime;
                frameCt++;

                if (frameCt == FPS) {
                    averageFPS = 1000 / ((totalTime / frameCt) / 1000000); //calculate average fps
                    frameCt = 0;
                    totalTime = 0; //reset
                }

                if (averageFPS > 30) {
                    waitTime++;
                }

            }
        }
    }

    /**
     * Set game running
     *
     * @param b boolean running
     */
    public void setRunning(boolean b) {
        running = b;
    }
}

