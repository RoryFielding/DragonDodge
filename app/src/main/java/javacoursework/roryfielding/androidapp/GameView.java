package javacoursework.roryfielding.androidapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by RoryFielding on 30/03/2018.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread mainThread;
    private Background background;
    private Player player;
    private Explosion explosion;
    private ArrayList<Missile> missiles;
    private ArrayList<TopBorder> topBorders;
    private ArrayList<BottomBorder> bottomBorders;
    private long missileStartTime;
    private long startReset;
    private Random rng = new Random();
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    public static final int moveSpeed = -5;
    public boolean loseGame;
    private int maxBorderHeight;
    private int minBorderHeight;
    public static int progressDenominator; //increase to slow down difficult progression, decrease to speed up
    private boolean topDown = true;
    private boolean bottomDown = true;
    private boolean newGameCreated;
    private boolean reset;
    private boolean disappear;
    private boolean started;
    private MediaPlayer explosionsound;

    public GameView(Context context) {


        super(context);

        //add callback to surfaceholder to intercept events
        getHolder().addCallback(this);

        //set GameView focusable so it can handle events
        setFocusable(true);

        this.loseGame = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;
        int ct = 0;

        while (retry && ct < 1000) { //prevent infinite loop
            try {
                mainThread.setRunning(false); //try to stop thread and set running to false
                mainThread.join();
                retry = false; //if succeed goes to retry = false stopping thread.
                mainThread = null; //set thread to null so garbage collector gets object
            } catch (InterruptedException e) {
                e.printStackTrace();
            } //if this false, catch and retry again

        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {



        //set up background and player
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background)); //set the background to resource
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.flappydragonresized), 122, 88, 6);   //get image, width of frame, height of frame, num of frames
       //set up missile timer and arrays
        missiles = new ArrayList<Missile>();
        topBorders = new ArrayList<TopBorder>();
        bottomBorders = new ArrayList<BottomBorder>();
        missileStartTime = System.nanoTime();

        //instantiate thread
        mainThread = new MainThread(getHolder(), this);

        //when surface created, start game loop
        mainThread.setRunning(true);
        mainThread.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) { //if down press
            if (!player.getPlaying() && newGameCreated && reset) { //if first time pressing down
                player.setPlaying(true); //starting playing
                player.setUp(true);
            }
            if(player.getPlaying()){
                if(!started) started = true;
                reset = false;
                player.setUp(true);
            }

            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            player.setUp(false); //if release finger, player starts going down.
            return true;
        }

        return super.onTouchEvent(event);
    }

    public void update() {

        if (player.getPlaying()) { //only update if playing

            if(bottomBorders.isEmpty()){
                player.setPlaying(false);
                return;
            }
            if(topBorders.isEmpty()){
                player.setPlaying(false);
                return;
            }

            background.update();
            player.update();

            //calculate the threshold of height the border can have based on the score
            //max and min border height are updated, and the border switches direction when either max or min is met
            maxBorderHeight = 30 + player.getScore() / progressDenominator;
            //cap max border height so that borders can only take up a total of half screen otherwise it's impossible
            if (maxBorderHeight > HEIGHT / 4) {
                maxBorderHeight = HEIGHT / 4;
            }

            minBorderHeight = 5 + player.getScore() / progressDenominator;

            //check top border collision
            for(int i = 0; i<topBorders.size(); i++){
                if(collision(topBorders.get(i), player))
                    player.setPlaying(false);
            }

            //check bottom border collision
            for(int i = 0; i <bottomBorders.size(); i++){
                if(collision(bottomBorders.get(i), player))
                    player.setPlaying(false);
            }

            //update top border
            this.updateTopBorder();

            //create bottom border
            this.updateBottomBorder();


            //once the player reaches level 2 (score of 200) start firing missiles
            if (player.getPlaying() && (player.getScore() > 200)) {
                //add missiles on a timer
                long missileElapsed = (System.nanoTime() - missileStartTime) / 1000000;
                if (missileElapsed > 2000 - player.getScore() / 4) { //the higher the player score, less delay time

                    //first missile always goes down center of screen, starting offset by 10 on x axis so starts off screen
                    if (missiles.size() == 0) {
                        missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.flysprite),
                                WIDTH + 10, HEIGHT / 2, 57, 46, player.getScore(), 2));
                    } else { //other missiles are randomly placed on the screen however do no intersect with the borders created
                        missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.flysprite),
                                WIDTH + 10, (int) (rng.nextDouble() * (HEIGHT - (maxBorderHeight * 2)) + maxBorderHeight + 15), 57, 46, player.getScore(), 2));
                    }
                    if(player.getScore() > 300){ //once players hits level 3, start firing even more missiles
                        missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.flysprite),
                                WIDTH + 10, (int) (rng.nextDouble() * (HEIGHT - (maxBorderHeight * 2)) + maxBorderHeight + 15), 57, 46, player.getScore(), 2));
                        missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.flysprite),
                                WIDTH + 10, (int) (rng.nextDouble() * (HEIGHT - (maxBorderHeight * 2)) + maxBorderHeight + 15), 57, 46, player.getScore(), 2));

                    }
                    missileStartTime = System.nanoTime();
                }


                //loop through every missile
                for (int i = 0; i < missiles.size(); i++) {
                    //update missile
                    missiles.get(i).update();
                    if (collision(missiles.get(i), player)) { //, check missiles and player to see if they collide
                        missiles.remove(i);
                        player.setPlaying(false);
                        break;
                    }
                    if (missiles.get(i).getX() < -100) { //if player dodges missile and it is off screen
                        missiles.remove(i); //remove the missile in order to save memory
                    }
                }
            }
        }else //when the player dies
            {
                player.resetDY();
                if(!reset){
                    newGameCreated = false;
                    startReset = System.nanoTime();
                    reset = true;
                    disappear = true;
                    explosion = new Explosion(BitmapFactory.decodeResource(getResources(), R.drawable.explosion),
                            player.getX(), player.getY() - 30, 100, 100, 25); //draw explosion slightly above player
                    explosionsound = MediaPlayer.create(getContext(), R.raw.explosion);
                    explosionsound.start();
                }
                explosion.update();;
                long resetElapsed = (System.nanoTime() - startReset)/1000000;

                if(resetElapsed > 1500 && !newGameCreated){
                   newGame();
                }

                if(resetElapsed > 1 && loseGame){
                    gameLost();
                }
        }
    }

    //function to tell whether two game objects are colliding
    public boolean collision(GameObject a, GameObject b) {
        if (Rect.intersects(a.getRectangle(), b.getRectangle())) {
            loseGame = true;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void draw(Canvas canvas) {

        final float scaleFactorX = getWidth() / (WIDTH * 1.f); //get width of fullscreen
        final float scaleFactorY = getHeight() / (HEIGHT * 1.f); //get height of fullscreen

        if (canvas != null) {
            final int savedState = canvas.save(); //before scaling, save state
            canvas.scale(scaleFactorX, scaleFactorY); //scale
            background.draw(canvas); //draw background

            if(!disappear) {
                player.draw(canvas);
            }

            for (Missile m : missiles) { //for each missile in the array
                m.draw(canvas); //draw missiles
            }

            //draw topborder
            for(TopBorder tb : topBorders){
                tb.draw(canvas);
            }

            //draw bottomborder
            for(BottomBorder bb: bottomBorders){
                bb.draw(canvas);
            }

            //draw explosion
            if(started){
                explosion.draw(canvas);
            }

            drawText(canvas);

            Paint paint2 = new Paint();
            paint2.setColor(Color.WHITE);
            paint2.setTextSize(50);
            paint2.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

            for (int i = 1; i < 100; i++){
                if(player.getPlaying() && (player.getScore() > (i * 100)) && (player.getScore() < ((i * 100) + 20))){
                    canvas.drawText("Level " + i, WIDTH / 2 - 50, HEIGHT / 2, paint2);
                }
            }

            canvas.restoreToCount(savedState); //after drawing restore to saved unscaled state
        }
    }

    public void updateTopBorder() {
        //borders will go up until they reach max height, then go down till min, then reiterate
        //every 50 points, insert randomly placed top blocks that break that pattern
        if (player.getScore() % 50 == 0) {
            topBorders.add(new TopBorder(BitmapFactory.decodeResource(getResources(), R.drawable.stonewall
            ), topBorders.get(topBorders.size() - 1).getX() + 20, 0, (int) ((rng.nextDouble() * (maxBorderHeight
            )) + 1)));
            //height will be random number from 1 to the max border height
        }

        for (int i = 0; i < topBorders.size(); i++){
            topBorders.get(i).update();
            if(topBorders.get(i).getX() < - 20){ //if border moves off the map by -20
                topBorders.remove(i); //remove this object from the arraylist to save memory
                if(topBorders.get(topBorders.size()-1).getHeight() >= maxBorderHeight){ //calculate topdown which determines which
                    topDown = false; //direction border is moving (up or down)
                }
                if(topBorders.get(topBorders.size()-1).getHeight() <= minBorderHeight){ //if last element smaller than minheight
                    topDown = true; //switch direction
                }
                //new border added will have more height
                if(topDown){
                    topBorders.add(new TopBorder(BitmapFactory.decodeResource(getResources(),
                            R.drawable.stonewall), topBorders.get(topBorders.size()-1).getX() + 20,
                            0, topBorders.get(topBorders.size()-1).getHeight() + 1));
                }else //new border added will have less height
                    {
                        topBorders.add(new TopBorder(BitmapFactory.decodeResource(getResources(),
                                R.drawable.stonewall), topBorders.get(topBorders.size()-1).getX() + 20,
                                0, topBorders.get(topBorders.size()-1).getHeight() - 1));

                }
            }
        }
    }

    public void updateBottomBorder() {

        //every 60 points, insert randomly placed bottom blocks that break pattern
        if (player.getScore() % 60 == 0) {
            bottomBorders.add(new BottomBorder(BitmapFactory.decodeResource((getResources()), R.drawable.stonewall),
                    bottomBorders.get(bottomBorders.size() - 1).getX() + 20, (int) ((rng.nextDouble() * maxBorderHeight
                    + (HEIGHT - maxBorderHeight)))));
        }
            //update bottom border
            for (int i = 0; i < bottomBorders.size(); i++){
                bottomBorders.get(i).update();

                //if border is moving off screen, remove it and add a corresponding new one of the other size

                if(bottomBorders.get(i).getX() < - 20) {
                    bottomBorders.remove(i);

                    //determine if border going up or down
                    if (bottomBorders.get(bottomBorders.size() - 1).getY() <= HEIGHT - maxBorderHeight) { //calculate topdown which determines which
                        bottomDown = true; //direction border is moving (up or down)
                    }
                    if (bottomBorders.get(bottomBorders.size() - 1).getY() >= HEIGHT - minBorderHeight) { //if last element smaller than minheight
                        bottomDown = false; //switch direction
                    }

                    if (bottomDown) {
                        bottomBorders.add(new BottomBorder(BitmapFactory.decodeResource(getResources(), R.drawable.stonewall)
                                , bottomBorders.get(bottomBorders.size() - 1).getX() + 20, bottomBorders.get(bottomBorders.size() - 1
                        ).getY() + 1)); //move down
                    } else {
                        bottomBorders.add(new BottomBorder(BitmapFactory.decodeResource(getResources(), R.drawable.stonewall)
                                , bottomBorders.get(bottomBorders.size() - 1).getX() + 20, bottomBorders.get(bottomBorders.size() - 1
                        ).getY() - 1)); //move up
                    }
                }
            }
        }


    public void newGame(){

        //new game gets called 1500ms after player dies

        disappear = false; //appear player again
        bottomBorders.clear(); //clear arrays
        topBorders.clear();
        missiles.clear();
        minBorderHeight = 5; //reset variables
        maxBorderHeight = 30;
        player.setY(HEIGHT/2);
        player.resetDY();

        //if top score, bump other scores down and set top to first
        if((player.getScore() > (getRecord())) && (player.getScore() > getRecord2()) && (player.getScore() > getRecord3())){
            setRecord3(getRecord2());
            setRecord2(getRecord());
            setRecord(player.getScore());
            player.resetScore();
        }

        //if second score, greater than three and two but less than one bump second score to third and set second
        if((player.getScore() > getRecord2()) && (player.getScore() > getRecord3()) && (player.getScore() < getRecord())){
            setRecord3(getRecord2());
            setRecord2(player.getScore());
            player.resetScore();
        }

        //if third score but not greater than the second score, set third score
        if((player.getScore() > getRecord3()) && player.getScore() < getRecord2()){
            setRecord3(player.getScore());
            player.resetScore();
        }

        player.resetScore();

        //create initial borders until they are width +40 off the screen
        for(int i = 0; i * 20 < WIDTH + 40; i++){
            if(i == 0){ //first one created
                topBorders.add(new TopBorder(BitmapFactory.decodeResource(getResources(), R.drawable.stonewall
                ), i * 20, 0, 10));
            }else
                {
                    topBorders.add(new TopBorder(BitmapFactory.decodeResource(getResources(), R.drawable.stonewall
                    ), i * 20, 0, topBorders.get(i-1).getHeight()+1)); //add to last element in array
                }
        }

        //initialise bottom borders
        for(int i = 0; i * 20 < WIDTH + 40; i++){
            //first one created
            if(i == 0){
                bottomBorders.add(new BottomBorder(BitmapFactory.decodeResource(getResources(), R.drawable.stonewall
                ), i*20, HEIGHT - minBorderHeight ));
            } else { //adding on top of this until full screen
                bottomBorders.add(new BottomBorder(BitmapFactory.decodeResource(getResources(), R.drawable.stonewall)
                , i*20, bottomBorders.get(i-1).getY()-1));
            }

            newGameCreated = true;

        }
    }

    public void drawText(Canvas canvas){

        Paint backcolour = new Paint();
        backcolour.setColor(Color.BLACK);
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setTextSize(30);
        paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        canvas.drawRect(0, HEIGHT - 40, WIDTH, WIDTH + 20, backcolour);
        canvas.drawText("SCORE: " + (player.getScore()), 10, HEIGHT - 10, paint);
        canvas.drawText("HIGHSCORE: " + getRecord(), WIDTH - 285, HEIGHT - 10, paint);

        if(!player.getPlaying() && newGameCreated && reset){
            Paint paint1 = new Paint();
            paint1.setColor(Color.WHITE);
            paint1.setTextSize(80);
            paint1.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
            canvas.drawText("PRESS TO START", WIDTH/2-50, HEIGHT/2, paint1);

            paint1.setTextSize(60);
            canvas.drawText("TOUCH TO GO UP", WIDTH/2-50, HEIGHT/2 + 60, paint1);
            canvas.drawText("RELEASE TO FALL", WIDTH/2-50, HEIGHT/2 + 120, paint1);
        }
    }

    public int getRecord(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        return prefs.getInt("record", 0);
    }

    public int getRecord2(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        return prefs.getInt("second", 0);
    }

    public int getRecord3(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        return prefs.getInt("third", 0);
    }

    public void setRecord(int value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("record", value);
        editor.commit();
    }

    public void setRecord2(int value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("second", value);
        editor.commit();
    }

    public void setRecord3(int value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("third", value);
        editor.commit();
    }

    public void gameLost(){

            //if top score, bump other scores down and set top to first
            if((player.getScore() > (getRecord())) && (player.getScore() > getRecord2()) && (player.getScore() > getRecord3())){
                setRecord3(getRecord2());
                setRecord2(getRecord());
                setRecord(player.getScore());
                player.resetScore();
            }

            //if second score, greater than three and two but less than one bump second score to third and set second
            if((player.getScore() > getRecord2()) && (player.getScore() > getRecord3()) && (player.getScore() < getRecord())){
                setRecord3(getRecord2());
                setRecord2(player.getScore());
                player.resetScore();
            }

            //if third score but not greater than the second score, set third score
            if((player.getScore() > getRecord3()) && player.getScore() < getRecord2()){
                setRecord3(player.getScore());
                player.resetScore();
            }

            player.resetScore();

           // ((Activity) getContext()).finish();

            Intent intent = new Intent().setClass(getContext(), HighscoreActivity.class);
            ((Activity) getContext()).startActivity(intent);
        }

}
