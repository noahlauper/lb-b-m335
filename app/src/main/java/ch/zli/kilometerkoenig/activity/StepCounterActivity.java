package ch.zli.kilometerkoenig.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ch.zli.kilometerkoenig.R;

public class StepCounterActivity extends AppCompatActivity {

    private final static int SAMPLING_RATE = 100000;

    private SensorManager sensorManager;
    private Sensor sensor;

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

        Button stopMeasurementButton = findViewById(R.id.stopMeasurement);

        stopMeasurementButton.setOnClickListener(view -> {
            Intent mainActivityIntend = new Intent(this, MainActivity.class);
            startActivity(mainActivityIntend);
        });

        initializeSensor();

    }


    private void initializeSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorManager.registerListener((SensorEventListener) this, sensor, SAMPLING_RATE);
    }
}