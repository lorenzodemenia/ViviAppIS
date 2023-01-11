package com.example.viviappis.data.model;

import android.os.CountDownTimer;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class Timer {
    private static final long START_TIME_IN_MILLIS = 600000; //10 min
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;


    public void startTimer(String idEvento) {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText(idEvento);
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
            }
        }.start();
        mTimerRunning = true;

    }

    public void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
    }

    public void resetTimer(String idEvento) {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText(idEvento);
    }

    public String updateCountDownText(String idEvento) {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://vivi-a924b-default-rtdb.europe-west1.firebasedatabase.app");
        DatabaseReference dbRef = db.getReference();

        dbRef.child("timers").child(idEvento).setValue(timeLeftFormatted);
        return timeLeftFormatted;
    }

    public long getmTimeLeftInMillis() {
        return mTimeLeftInMillis;
    }

    public boolean ismTimerRunning() {
        return mTimerRunning;
    }

    public CountDownTimer getmCountDownTimer() {
        return mCountDownTimer;
    }

    public static long getStartTimeInMillis() {
        return START_TIME_IN_MILLIS;
    }
}
