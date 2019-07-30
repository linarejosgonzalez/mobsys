package com.example.weatherstation;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.browse.MediaBrowser;
import android.media.browse.MediaBrowser.ConnectionCallback;
import android.se.omapi.SEService;
import android.se.omapi.SEService.OnConnectedListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CompareSensorActivity extends AppCompatActivity implements SensorEventListener, OnConnectedListener {

    private SensorManager mSensorManager;
    private Sensor mHumiditySensor;
    private Sensor mTemperatureSensor;
    private Sensor mPressureSensor;
    private boolean isHumiditySensorPresent;
    private boolean isTemperatureSensorPresent;
    private boolean isPressureSensorPresent;
    private TextView mRelativeHumiditySensorValue;
    private TextView mPressureSensorValue;
    private TextView mTemperatureSensorValue;
    private TextView mRelativeHumidityWSValue;
    private TextView mPressureWSValue;
    private TextView mTemperatureWSValue;
    //protected GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_sensor);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onConnected() {

    }
}
