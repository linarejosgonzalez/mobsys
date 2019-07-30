package com.example.weatherstation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CheckTemperature extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor = null;
    private boolean isSensorPresent = false;
    TextView mTemperatureValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_temperature);

        mTemperatureValue = (TextView) findViewById(R.id.textView6);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isSensorPresent = true;
        }else{
            isSensorPresent = false;
            mTemperatureValue.setText("Your device does not have a temperature sensor");
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        mTemperatureValue.append(event.values[0] + "");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume(){
        super.onResume();
        if(isSensorPresent){
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        if(isSensorPresent){
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mSensorManager != null){
            mSensorManager = null;
        }
        if(mSensor != null){
            mSensor = null;
        }
    }
}
