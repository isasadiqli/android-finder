package com.example.animalfinder;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Timer;
import java.util.TimerTask;

public class Tools {
    private static int correctCounter = 0;
    private static int level = 0;
    private static MediaPlayer animalSound;

    private static boolean correctButtonClicked = false;
    private static boolean wrongButtonClicked = false;
    private static boolean flag = true;
    private static TimerTask task;
    private static int highScore = 0;

    private static CountDownTimer countDownTimer;
    private static String timeLeftText = "";
    private static boolean outOfTime = false;

    public static void setCorrectCounter(int correctCounter) {
        Tools.correctCounter = correctCounter;
    }

    public static void setLevel(int level) {
        Tools.level = level;
    }

    public static int getCorrectCounter() {
        return correctCounter;
    }

    public static boolean isCorrectButtonClicked() {
        return correctButtonClicked;
    }

    public static boolean isWrongButtonClicked() {
        return wrongButtonClicked;
    }

    public static int getLevel() {
        return level;
    }

    public static void setCorrectButtonClicked() {
        correctButtonClicked = false;
    }

    public static void setWrongButtonClicked() {
        wrongButtonClicked = false;
    }

    public static void setOutOfTime(boolean outOfTime) {
        Tools.outOfTime = outOfTime;
    }

    public static MediaPlayer getAnimalSound() {
        return animalSound;
    }

    public static int getHighScore(Context context) {
        highScore = Integer.parseInt(readFromFile(context, "highScore.txt"));
        return highScore;
    }

    public static void setHighScore(Context context, int highScore) {
        writeToFile(String.valueOf(highScore), context, "highScore.txt");
        Tools.highScore = highScore;
    }

    public static int readCurrentScore(Context context) {
        correctCounter = Integer.parseInt(readFromFile(context, "currentScore.txt"));
        return correctCounter;
    }

    public static void writeCurrentScore(Context context, int score) {
        writeToFile(String.valueOf(score), context, "currentScore.txt");
        correctCounter = score;
    }

    public static void setAnimalSound(MediaPlayer animalSound, boolean flag) {
        Tools.animalSound = animalSound;
        Tools.flag = flag;
    }

    public static TimerTask getTask() {
        return task;
    }

    public static boolean isOutOfTime() {
        return outOfTime;
    }

    public static void animalSoundPlayer() {

        task = new TimerTask() {
            @Override
            public void run() {

                if (flag)
                    animalSound.start();
                else {
                    animalSound.seekTo(0);
                    animalSound.release();
                }
            }
        };
        new Timer().schedule(task, 3000L);

    }

    public static void answerChecker(View v) {

        correctCounter = Tools.getCorrectCounter();
        if (level == 1) {
            if ((correctCounter == 0 || correctCounter == 2) && v.getId() == R.id.lvl1_firstImageButton) {
                correct();
            } else if (correctCounter == 1 && v.getId() == R.id.lvl1_secondImageButton) {
                correct();
            } else
                wrong();
        }
        if (level == 2) {
            if (correctCounter == 3 && v.getId() == R.id.lvl2_firstImageButton) {
                correct();

            } else if (correctCounter == 5 && v.getId() == R.id.lvl2_secondImageButton) {
                correct();

            } else if (correctCounter == 4 && v.getId() == R.id.lvl2_thirdImageButton) {
                correct();
            } else
                wrong();
        }
        if (level == 3) {
            if (correctCounter == 6 && v.getId() == R.id.lvl3_secondImageButton) {
                correct();
            } else if (correctCounter == 7 && v.getId() == R.id.lvl3_thirdImageButton) {
                correct();
            } else if (correctCounter == 8 && v.getId() == R.id.lvl3_firstImageButton) {
                correct();
            } else
                wrong();
        }
        if (level == 4) {
            if (correctCounter == 9 && v.getId() == R.id.lvl4_secondImageButton) {
                correct();
            } else if (correctCounter == 10 && v.getId() == R.id.lvl4_fifthImageButton) {
                correct();
            } else if (correctCounter == 11 && v.getId() == R.id.lvl4_forthImageButton) {
                correct();
            } else
                wrong();
        }
        if (level == 5) {
            if (correctCounter == 12 && v.getId() == R.id.lvl5_sixthImageButton) {
                correct();
            } else if (correctCounter == 13 && v.getId() == R.id.lvl5_eighthImageButton) {
                correct();
            } else if (correctCounter == 14 && v.getId() == R.id.lvl5_seventhImageButton) {
                correct();
            } else
                wrong();
        }
    }

    public static void correct() {
        correctButtonClicked = true;
        wrongButtonClicked = false;
        correctCounter++;
        if (correctCounter == 15)
            level = 6;
    }

    private static void wrong() {
        wrongButtonClicked = true;
        correctButtonClicked = false;
    }

    public static String readFromFile(Context context, String fileName) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(fileName);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append("").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public static void writeToFile(String data, Context context, String fileName) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static void stopTimer() {
        countDownTimer.cancel();
    }

    public static void setCountDownTimer(CountDownTimer countDownTimer) {
        Tools.countDownTimer = countDownTimer;
    }
}
