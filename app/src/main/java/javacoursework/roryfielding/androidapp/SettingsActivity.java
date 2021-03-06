package javacoursework.roryfielding.androidapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by RoryFielding on 05/04/2018.
 */

public class SettingsActivity extends Activity {

    /**
     * Override onCreate function to create instance of activity to run the game
     * Set content view to game
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

        GameView.progressDenominator = 20;

        setContentView(new GameView(this));
            }
}