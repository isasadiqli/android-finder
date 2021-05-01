package com.example.animalfinder;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private int HighScore = 0, correctCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File file = new File(String.valueOf(getFileStreamPath("highScore.txt")));
        try {
            if (file.exists())
                HighScore = Tools.getHighScore(this);
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException);
        }

        TextView textView = (TextView) findViewById(R.id.HighScore);
        textView.setText("High score " + HighScore * 10);

        Button continueButton = findViewById(R.id.playButton);
        continueButton.setClickable(false);
        continueButton.setAlpha((float) 0.5);

        file = new File(String.valueOf(getFileStreamPath("currentScore.txt")));
        try {
            if (file.exists())
                if (Tools.readCurrentScore(this) != 0 && Tools.readCurrentScore(this) != 15) {
                    continueButton.setClickable(true);
                    continueButton.setAlpha((float) 0.9);
                }
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException);
        }

        Animation bounceAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        findViewById(R.id.welcomeLayout).startAnimation(bounceAnim);
    }

    public void PlayButtonClicked(View v) {

        correctCounter = Tools.getCorrectCounter();

        File file = new File(String.valueOf(getFileStreamPath("currentScore.txt")));
        try {
            if (file.exists())
                correctCounter = Tools.readCurrentScore(this);
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException);
        }

        Intent levelIntent = new Intent(this, MainActivity.class);
        if (correctCounter <= 2) {
            levelIntent = new Intent(this, Level1.class);
        } else if (correctCounter <= 5) {
            levelIntent = new Intent(this, Level2.class);
        } else if (correctCounter <= 8) {
            levelIntent = new Intent(this, Level3.class);
        } else if (correctCounter <= 11) {
            levelIntent = new Intent(this, Level4.class);
        } else if (correctCounter <= 14) {
            levelIntent = new Intent(this, Level5.class);
        }

        startActivity(levelIntent);

    }

    public void QuitButtonClicked(View v) {
        finish();
        System.exit(0);
    }

    public void BackButtonClicked(View v) {

        findViewById(R.id.youWonLayout).setVisibility(View.GONE);
        findViewById(R.id.youLostLayout).setVisibility(View.GONE);
    }

    public void NewGameButtonClicked(View v) {
        correctCounter = 0;
        Tools.setCorrectCounter(correctCounter);
        Tools.writeCurrentScore(this, correctCounter);
        PlayButtonClicked(v);
    }

    public void answerChecker(View v) {

        correctCounter = Tools.getCorrectCounter();
        Tools.answerChecker(v);
    }


}