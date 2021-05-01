package com.example.animalfinder;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Level4 extends AppCompatActivity {

    private MediaPlayer animalSound;
    private TextToSpeech question;

    private CountDownTimer countDownTimer;
    private String timeLeftText = "";
    private long timeLeft = 12000;
    private boolean timerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level4);

        Tools.setCorrectButtonClicked();
        Tools.setWrongButtonClicked();

        int correctCounter = Tools.getCorrectCounter();
        int level;
        String animalType = "";
        level = 4;
        Tools.setLevel(level);


        ImageButton firstButton = findViewById(R.id.lvl4_firstImageButton);
        ImageButton secondButton = findViewById(R.id.lvl4_secondImageButton);
        ImageButton thirdButton = findViewById(R.id.lvl4_thirdImageButton);
        ImageButton forthButton = findViewById(R.id.lvl4_forthImageButton);
        ImageButton fifthButton = findViewById(R.id.lvl4_fifthImageButton);
        ImageButton sixthButton = findViewById(R.id.lvl4_sixthImageButton);

        if (correctCounter == 9) {
            firstButton.setImageResource(R.drawable.camel_200x200);
            secondButton.setImageResource(R.drawable.pig_200x200);
            thirdButton.setImageResource(R.drawable.hen_200x200);
            forthButton.setImageResource(R.drawable.cat_200x200);
            fifthButton.setImageResource(R.drawable.giraph_200x200);
            sixthButton.setImageResource(R.drawable.fish_200x200);

            animalSound = MediaPlayer.create(Level4.this, R.raw.pig);
            animalType = "pig";

        } else if (correctCounter == 10) {
            firstButton.setImageResource(R.drawable.pig_200x200);
            secondButton.setImageResource(R.drawable.bird_200x200);
            thirdButton.setImageResource(R.drawable.bear_200x200);
            forthButton.setImageResource(R.drawable.crocadile_200x200);
            fifthButton.setImageResource(R.drawable.sheep_200x200);
            sixthButton.setImageResource(R.drawable.dovsan_200x200);

            animalSound = MediaPlayer.create(Level4.this, R.raw.sheep);
            animalType = "sheep";

        } else if (correctCounter == 11) {
            firstButton.setImageResource(R.drawable.elephant_200x200);
            secondButton.setImageResource(R.drawable.hen_200x200);
            thirdButton.setImageResource(R.drawable.cat_200x200);
            forthButton.setImageResource(R.drawable.snake_200x200);
            fifthButton.setImageResource(R.drawable.sincab_1_200x200);
            sixthButton.setImageResource(R.drawable.rat_200x200);

            animalSound = MediaPlayer.create(Level4.this, R.raw.snake);
            animalType = "snake";

        }

        switch (animalType) {
            case "pig":
                question = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if (i == TextToSpeech.SUCCESS) {
                            question.setLanguage(Locale.ENGLISH);
                            question.speak("Find the pig, pig makes sound:", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                });
                break;
            case "sheep":
                question = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if (i == TextToSpeech.SUCCESS) {
                            question.setLanguage(Locale.ENGLISH);
                            question.speak("Find the sheep, sheep makes sound:", TextToSpeech.QUEUE_FLUSH, null);

                        }
                    }
                });
                break;
            case "snake":
                question = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if (i == TextToSpeech.SUCCESS) {
                            question.setLanguage(Locale.ENGLISH);
                            question.speak("Find the snake, snake makes sound:", TextToSpeech.QUEUE_FLUSH, null);

                        }
                    }
                });
                break;
        }

        TimerTask task = Tools.getTask();
        //new Timer().schedule(task, 3000L);

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

        if(timerRunning){
            timerRunning = false;
            countDownTimer.cancel();
        }
    }

    public String startTimer(TextView textView, Context context){

        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                int seconds = (int) timeLeft/1000;
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

        if(timerRunning){
            timerRunning = false;
            countDownTimer.cancel();
        }

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent main = new Intent(this, MainActivity.class);
            finishAffinity();
            startActivity(main);
            return true;
        }

        if(animalSound.isPlaying()){
            animalSound.seekTo(0);
            animalSound.release();
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