package ch.zli.kilometerkoenig.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.widget.TextView;

public class StepService extends Service implements SensorEventListener {

    private final static int SAMPLING_RATE = 100;

    private SensorManager sensorManager;

    private TextView count;

    private Sensor sensor;

    public int steps;

    private final IBinder binder = new StepBinder();



    public void initializeSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorManager.registerListener((SensorEventListener) this, sensor, SAMPLING_RATE);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            steps = (int) sensorEvent.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public class StepBinder extends Binder {
        public StepService getService() {
            return StepService.this;
        }
    }

    public StepService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}