package javacoursework.roryfielding.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by RoryFielding on 05/04/2018.
 */

public class ModeChanger extends Activity {

    Button easyBtn;
    Button medBtn;
    Button hardBtn;
    Button goBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //turn title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set initial view to the menu
        setContentView(R.layout.setting_layout);

        easyBtn = (Button) findViewById(R.id.easyButton);
        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameView.progressDenominator = 20;
            }
        });

        medBtn = (Button) findViewById(R.id.mediumButton);
        medBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameView.progressDenominator = 10;
            }
        });

        hardBtn = (Button) findViewById(R.id.hardButton);
        hardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameView.progressDenominator = 1;
            }
        });

        goBackBtn = (Button) findViewById(R.id.settingsBackButton);
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMainActivity();
            }
        });

    }

    public void launchMainActivity(){
        Intent mainIntent = new Intent(this, TheGame.class);
        startActivity(mainIntent);
    }
}
