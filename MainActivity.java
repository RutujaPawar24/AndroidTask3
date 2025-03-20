package com.example.stopwatchapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvMinutes, tvSeconds, tvMilliseconds;
    private Button btnStartPause, btnReset;
    private boolean isRunning = false;
    private long startTime = 0;
    private long timeInMillis = 0;
    private Handler handler = new Handler();
    private Runnable updateTimerRunnable = new Runnable() {
        @Override
        public void run() {
            timeInMillis = System.currentTimeMillis() - startTime;
            int minutes = (int) (timeInMillis / 60000);
            int seconds = (int) ((timeInMillis % 60000) / 1000);
            int milliseconds = (int) (timeInMillis % 1000);

            tvMinutes.setText(String.format("%02d", minutes));
            tvSeconds.setText(String.format("%02d", seconds));
            tvMilliseconds.setText(String.format("%03d", milliseconds));

            if (isRunning) {
                handler.postDelayed(this, 10);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMinutes = findViewById(R.id.tv_minutes);
        tvSeconds = findViewById(R.id.tv_seconds);
        tvMilliseconds = findViewById(R.id.tv_milliseconds);
        btnStartPause = findViewById(R.id.btn_start_pause);
        btnReset = findViewById(R.id.btn_reset);

        btnStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    // Pause the stopwatch
                    isRunning = false;
                    btnStartPause.setText("Start");
                } else {
                    // Start the stopwatch
                    startTime = System.currentTimeMillis() - timeInMillis;
                    handler.post(updateTimerRunnable);
                    isRunning = true;
                    btnStartPause.setText("Pause");
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset the stopwatch
                isRunning = false;
                timeInMillis = 0;
                tvMinutes.setText("00");
                tvSeconds.setText("00");
                tvMilliseconds.setText("000");
                btnStartPause.setText("Start");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isRunning) {
            isRunning = false;
        }
    }
}
