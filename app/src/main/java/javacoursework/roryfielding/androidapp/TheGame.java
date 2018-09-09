package javacoursework.roryfielding.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class TheGame extends Activity {

    /**
     * Override onCreate function to set layout to menu on start up
     * Start music
     * Implemented buttons to take user to game, settings menu and highscores
     *
     * @param savedInstanceState Bundle
     */

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        //turn title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set initial view to the menu
        setContentView(R.layout.activity_the_game);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8889979893131718/1039543685");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                finish();
            }
        });

        Button startMeUp = (Button) findViewById(R.id.startMeUp);
        startMeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                launchSettings();
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
        finish();
    }

    /**
     * Function to show big ad
     */
    public void showInterstitial(){
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            finish();
        }
    }
    @Override
    public void onBackPressed(){
        showInterstitial();
    }
}
