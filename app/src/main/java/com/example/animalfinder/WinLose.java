package com.example.animalfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.util.Locale;
import java.util.Timer;

public class WinLose extends AppCompatActivity {

    private int HighScore;
    MediaPlayer victory;
    private boolean victoryIsPlaying = false;
    private int score = Tools.getCorrectCounter();
    TextToSpeech greeting = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_lose);

        File file = new File(String.valueOf(getFileStreamPath("highScore.txt")));
        TextView textView = (TextView) findViewById(R.id.yourScoreTextView);

        try {
            if (file.exists())
                HighScore = Tools.getHighScore(this);
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException);
        }

        if (score > HighScore)
            Tools.setHighScore(this, score);

        if (Tools.isCorrectButtonClicked()) {
            findViewById(R.id.youWonLayout).setVisibility(View.VISIBLE);
            findViewById(R.id.youLostLayout).setVisibility(View.GONE);

            switch (Tools.getLevel()) {
                case 1:
                    greeting = new TextToSpeech(getApplicationContext(), i -> {
                        if (i == TextToSpeech.SUCCESS) {
                            greeting.setLanguage(Locale.ENGLISH);
                            greeting.speak("Nice", TextToSpeech.QUEUE_FLUSH, null);


                        }
                    });
                    break;
                case 2:
                    greeting = new TextToSpeech(getApplicationContext(), i -> {
                        if (i == TextToSpeech.SUCCESS) {
                            greeting.setLanguage(Locale.ENGLISH);
                            greeting.speak("Amazing", TextToSpeech.QUEUE_FLUSH, null);

                        }
                    });
                    break;
                case 3:
                    greeting = new TextToSpeech(getApplicationContext(), i -> {
                        if (i == TextToSpeech.SUCCESS) {
                            greeting.setLanguage(Locale.ENGLISH);
                            greeting.speak("Perfect", TextToSpeech.QUEUE_FLUSH, null);

                        }
                    });
                    break;
                case 4:
                    greeting = new TextToSpeech(getApplicationContext(), i -> {
                        if (i == TextToSpeech.SUCCESS) {
                            greeting.setLanguage(Locale.ENGLISH);
                            greeting.speak("Talented", TextToSpeech.QUEUE_FLUSH, null);

                        }
                    });
                    break;
                case 5:
                    greeting = new TextToSpeech(getApplicationContext(), i -> {
                        if (i == TextToSpeech.SUCCESS) {
                            greeting.setLanguage(Locale.ENGLISH);
                            greeting.speak("Legendary", TextToSpeech.QUEUE_FLUSH, null);

                        }
                    });
                    break;
                default:
                    greeting = new TextToSpeech(getApplicationContext(), i -> {
                        if (i == TextToSpeech.SUCCESS) {
                            greeting.setLanguage(Locale.ENGLISH);
                            greeting.speak("You are real ANIMAL FINDER", TextToSpeech.QUEUE_FLUSH, null);

                        }
                    });

                    findViewById(R.id.youWonLayout).setVisibility(View.GONE);
                    findViewById(R.id.nextButton).setVisibility(View.GONE);

                    findViewById(R.id.congrats_layout).setVisibility(View.VISIBLE);

                    textView.setVisibility(View.GONE);

                    victory = MediaPlayer.create(WinLose.this, R.raw.victory);
                    victory.start();

                    victoryIsPlaying = true;
                    break;
            }
            greeting.setPitch(1.1f);
            greeting.setSpeechRate(0.7f);

            Tools.writeCurrentScore(this, score);

        } else {
            findViewById(R.id.youWonLayout).setVisibility(View.GONE);
            findViewById(R.id.youLostLayout).setVisibility(View.VISIBLE);
            findViewById(R.id.winlose).setBackgroundColor(Color.BLACK);

            Tools.setCorrectCounter(0);
            Tools.writeCurrentScore(this, 0);

            if (Tools.isOutOfTime())
                greeting = new TextToSpeech(getApplicationContext(), i -> {
                    if (i == TextToSpeech.SUCCESS) {
                        greeting.setLanguage(Locale.ENGLISH);
                        greeting.speak("Come on FASTER!!!", TextToSpeech.QUEUE_FLUSH, null);

                    }
                });
            else {
                greeting = new TextToSpeech(getApplicationContext(), i -> {
                    if (i == TextToSpeech.SUCCESS) {
                        greeting.setLanguage(Locale.ENGLISH);
                        greeting.speak("Nice try!!! Keep it up you can do it", TextToSpeech.QUEUE_FLUSH, null);

                    }
                });
                greeting.setSpeechRate(1.1f);
            }

            Tools.writeCurrentScore(this, 0);


        }


        if (score > HighScore)
            textView.setText("\uD835\uDD50\uD835\uDD46\uD835\uDD4C ℍ\uD835\uDD38\uD835\uDD4D\uD835\uDD3C ℕ\uD835\uDD3C\uD835\uDD4E ℝ\uD835\uDD3Cℂ\uD835\uDD46ℝ\uD835\uDD3B:" + score * 10);
        else
            textView.setText("\uD835\uDD4Aℂ\uD835\uDD46ℝ\uD835\uDD3C:\t" + score * 10);




    }

    public void nextButtonClicked(View v) {

        Tools.setAnimalSound(MediaPlayer.create(this, R.raw.initialize), false);

        finishAffinity();
        Intent levelIntent = new Intent(this, MainActivity.class);
        if (score <= 2) {
            levelIntent = new Intent(this, Level1.class);
        } else if (score <= 5) {
            levelIntent = new Intent(this, Level2.class);
        } else if (score <= 8) {
            levelIntent = new Intent(this, Level3.class);
        } else if (score <= 11) {
            levelIntent = new Intent(this, Level4.class);
        } else if (score <= 14) {
            levelIntent = new Intent(this, Level5.class);
        }

        startActivity(levelIntent);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent main = new Intent(this, MainActivity.class);
            finishAffinity();
            startActivity(main);
            if(victoryIsPlaying) {
                if (victory.isPlaying()) {
                    victory.seekTo(0);
                    victory.release();
                }
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (greeting != null) {
            greeting.stop();
            greeting.shutdown();
        }
        super.onDestroy();
    }
}