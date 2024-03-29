package ch.zli.kilometerkoenig.activity;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.Manifest;

import java.time.Instant;

import ch.zli.kilometerkoenig.R;
import ch.zli.kilometerkoenig.domain.AppDatabase;
import ch.zli.kilometerkoenig.domain.entity.Measurement;
import ch.zli.kilometerkoenig.service.StepService;

public class StepCounterActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ACTIVITY_RECOGNITION = 101;

    private Button stopMeasurementButton;

    private TextView count;
    private StepService stepService;

    private int stepsCount;

    private boolean isStepServiceBound = false;


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_step_counter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        askForSensorPermission();
        setView();
        stopMeasurementButton.setOnClickListener(view -> {
            Intent intent = getIntent();
            long startTime = intent.getLongExtra("startTime", 0);
            long endTime = System.currentTimeMillis();
            saveMeasurement(String.valueOf(startTime),String.valueOf(endTime));
            Intent mainActivityIntend = new Intent(this, MainActivity.class);
            startActivity(mainActivityIntend);
        });
    }

    private void askForSensorPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                    REQUEST_CODE_ACTIVITY_RECOGNITION);

            Intent bindStepServiceIntent = new Intent(this, StepService.class);
            bindService(bindStepServiceIntent, connection, Context.BIND_AUTO_CREATE);
        } else {
            Intent bindStepServiceIntent = new Intent(this, StepService.class);
            bindService(bindStepServiceIntent, connection, Context.BIND_AUTO_CREATE);

        }
    }

    private void saveMeasurement(String startTime, String endTime) {
        AsyncTask.execute(() -> {
            Measurement measurement = new Measurement();
            measurement.setSteps(stepsCount);
            measurement.setStartTime(startTime);
            measurement.setEndTime(endTime);
            measurement.setLvlPoints(stepService.steps);
            AppDatabase.getInstance(this).measurementDao().insertAll(measurement);
        });
    }

    private void setView() {
        stopMeasurementButton = findViewById(R.id.stopMeasurement);
        count = findViewById(R.id.stepCount);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isStepServiceBound) {
            stepService.startBackgroundCounting();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isStepServiceBound) {
            stepService.stopBackgroundCounting();
        }
    }


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            StepService.StepBinder binder = (StepService.StepBinder) iBinder;
            stepService = binder.getService();
            isStepServiceBound = true;
            stepService.initializeSensor();
            stepService.getStepCountLiveData().observe(StepCounterActivity.this, steps -> {
                stepsCount += steps;
                count.setText(String.valueOf(stepsCount));
            });
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isStepServiceBound = false;
        }
    };
}