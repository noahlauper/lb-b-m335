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

import androidx.lifecycle.MutableLiveData;

public class StepService extends Service implements SensorEventListener {

    private final static int SAMPLING_RATE = 100;

    private MutableLiveData<Integer> stepCountLiveData = new MutableLiveData<>(0); // Initialize with 0

    boolean isRunningInBackground = false;

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

    public void startBackgroundCounting() {
        isRunningInBackground = true;
        initializeSensor();
    }

    public void stopBackgroundCounting() {
        isRunningInBackground = false;
    }

    public MutableLiveData<Integer> getStepCountLiveData() {
        return stepCountLiveData;
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            stepCountLiveData.postValue((int) sensorEvent.values[0]);
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