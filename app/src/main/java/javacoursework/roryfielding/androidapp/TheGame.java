package javacoursework.roryfielding.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class TheGame extends Activity {

    MediaPlayer mediaPlayer;

    /**
     * Override onCreate function to set layout to menu on start up
     * Start music
     * Implemented buttons to take user to game, settings menu and highscores
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //turn title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Start some music
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.eightbitafricatoto);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        //set initial view to the menu
        setContentView(R.layout.activity_the_game);

        Button startMeUp = (Button) findViewById(R.id.startMeUp);
        startMeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                launchSettings();
            }
        });

        Button stopMe = (Button) findViewById(R.id.stopMe);
        stopMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(1);
                onDestroy();
            }
        });

        Button highscore = (Button) findViewById(R.id.highscore);
        highscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                launchHighscores();
            }
        });

        Button settings = (Button) findViewById(R.id.setMeUp);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                launchModeChanger();
            }
        });

    }

    /**
     * Override function for menu
     *
     * @param item MenuItem
     * @return super
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * Override function for menu
     *
     * @param menu Menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    /**
     * Function to launch the game
     */
    public void launchSettings() {
        startActivity(new Intent(TheGame.this, SettingsActivity.class));
    }

    /**
     * Function to launch the highscore activity
     */
    public void launchHighscores() {
        startActivity(new Intent(TheGame.this, HighscoreActivity.class));
    }

    /**
     * Function to launch the settings to allow user to change difficulty
     */
    public void launchModeChanger() {
        startActivity(new Intent(TheGame.this, ModeChanger.class));
    }

    /**
     * Override onPause to stop media player
     */
    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.release();
        finish();
    }
}
