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

        goBackBtn = (Button) findViewById(R.id.settingsBackButton);
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

}
