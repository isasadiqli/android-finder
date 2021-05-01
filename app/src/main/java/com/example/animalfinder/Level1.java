package com.example.animalfinder;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Level1 extends AppCompatActivity {

    private MediaPlayer animalSound;
    private TextToSpeech question;

    private CountDownTimer countDownTimer;
    private String timeLeftText = "";
    private long timeLeft = 21000;
    private boolean timerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);

        Tools.setCorrectButtonClicked();
        Tools.setWrongButtonClicked();


        int correctCounter = Tools.getCorrectCounter();
        int level;
        String animalType = "";
        level = 1;
        Tools.setLevel(level);


        ImageButton firstButton = findViewById(R.id.lvl1_firstImageButton);
        ImageButton secondButton = findViewById(R.id.lvl1_secondImageButton);

        if (correctCounter == 0) {
            firstButton.setImageResource(R.drawable.lion);
            secondButton.setImageResource(R.drawable.gelincik);

            animalSound = MediaPlayer.create(Level1.this, R.raw.lion);
            animalType = "lion";

        } else if (correctCounter == 1) {
            firstButton.setImageResource(R.drawable.bird_200x200);
            secondButton.setImageResource(R.drawable.bear_200x200);

            animalSound = MediaPlayer.create(Level1.this, R.raw.bear);
            animalType = "bear";

        } else if (correctCounter == 2) {
            firstButton.setImageResource(R.drawable.cat_200x200);
            secondButton.setImageResource(R.drawable.camel_200x200);

            animalSound = MediaPlayer.create(Level1.this, R.raw.cat);
            animalType = "cat";

        }


        switch (animalType) {
            case "lion":
                question = new TextToSpeech(getApplicationContext(), i -> {
                    if (i == TextToSpeech.SUCCESS) {
                        question.setLanguage(Locale.ENGLISH);
                        question.speak("Find the lion, lion makes sound:", TextToSpeech.QUEUE_FLUSH, null);

                    }
                });

                break;
            case "bear":
                question = new TextToSpeech(getApplicationContext(), i -> {
                    if (i == TextToSpeech.SUCCESS) {
                        question.setLanguage(Locale.ENGLISH);
                        question.speak("Find the bear, bear makes sound:", TextToSpeech.QUEUE_FLUSH, null);

                    }
                });
                break;
            case "cat":
                question = new TextToSpeech(getApplicationContext(), i -> {
                    if (i == TextToSpeech.SUCCESS) {
                        question.setLanguage(Locale.ENGLISH);
                        question.speak("Find the cat, cat makes sound:", TextToSpeech.QUEUE_FLUSH, null);
                    }
                });
                break;
        }

        if (!animalSound.isPlaying()) {
            Tools.setAnimalSound(animalSound, true);
            Tools.animalSoundPlayer();

        }

        startTimer(findViewById(R.id.timerTextView), this);
    }

    public void answerChecker(View v) {

        Tools.setAnimalSound(MediaPlayer.create(this, R.raw.initialize), false);
        Tools.answerChecker(v);

        Intent winLose = new Intent(this, WinLose.class);
        finishAffinity();
        startActivity(winLose);

        if (animalSound.isPlaying()) {
            animalSound.seekTo(0);
            animalSound.release();
        }

        if (timerRunning) {
            timerRunning = false;
            countDownTimer.cancel();
        }
    }

    public String startTimer(TextView textView, Context context) {

        Tools.setOutOfTime(false);

        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                int seconds = (int) timeLeft / 1000;
                timeLeftText = seconds + "";
                textView.setText(timeLeftText);
            }

            @Override
            public void onFinish() {
                Intent winLose = new Intent(context, WinLose.class);
                finishAffinity();
                startActivity(winLose);
                Tools.setOutOfTime(true);
            }
        }.start();
        timerRunning = true;
        return timeLeftText;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (timerRunning) {
            timerRunning = false;
            countDownTimer.cancel();
        }
        if(animalSound.isPlaying()){
            animalSound.seekTo(0);
            animalSound.release();
        }

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent main = new Intent(this, MainActivity.class);
            finishAffinity();
            startActivity(main);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (question != null) {
            question.stop();
            question.shutdown();
        }
        super.onDestroy();
    }
}
