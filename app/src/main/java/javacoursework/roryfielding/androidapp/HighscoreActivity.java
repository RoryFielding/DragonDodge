package javacoursework.roryfielding.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;

/**
 * Created by RoryFielding on 05/04/2018.
 */

public class HighscoreActivity extends Activity {

    public Button goBackBtn;
    public TextView tv1;
    public TextView tv2;
    public TextView tv3;
    public int highscore1;
    public int highscore2;
    public int highscore3;
    private InterstitialAd mInterstitialAd;

    /**
     * Override onCreate to set the layout to the highscore layout
     * Get highscores from shared preferences and display to user
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

        //set initial view to the menu
        setContentView(R.layout.highscore_layout);

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

        goBackBtn = (Button) findViewById(R.id.settingsBackButton);
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showInterstitial();
                    launchMainActivity();
            }
        });

        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        highscore1 = preferences.getInt("record", -1);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv1.setText("High Score : " + highscore1);

        highscore2 = preferences.getInt("second", -1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv2.setText("2nd Place : " + highscore2);

        highscore3 = preferences.getInt("third", -1);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv3.setText("3rd Place: " + highscore3);

    }

    /**
     * Function to send player back to main activity
     */
    public void launchMainActivity() {
        Intent mainIntent = new Intent(this, TheGame.class);
        startActivity(mainIntent);
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
