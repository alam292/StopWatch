package com.alam292.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public boolean isRunning = false;
    public long pauseOffset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Chronometer chronometer = findViewById(R.id.custom_chronometer);

        Button button_star = findViewById(R.id.button_start);
        Button button_pause = findViewById(R.id.button_pause);
        Button button_reset = findViewById(R.id.button_reset);

        chronometer.setFormat("Timer: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 10_000) {
                    // chronometer.setBase(SystemClock.elapsedRealtime());
                    Toast.makeText(MainActivity.this, "10 second completed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_star.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                if(!isRunning){
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chronometer.start();
                    isRunning = true;
                }
            }
        });

        button_pause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(isRunning){
                    chronometer.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    isRunning = false;
                }
            }
        });

        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.stop();
                pauseOffset = 0;
                isRunning = false;
            }
        });

    }
}
