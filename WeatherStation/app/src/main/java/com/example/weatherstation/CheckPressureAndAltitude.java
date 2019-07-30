package com.example.weatherstation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CheckPressureAndAltitude extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private boolean isSensorPresent = false;

    private TextView mPressureValue;
    private TextView mAltitudeValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_pressure_and_altitude);

        mPressureValue = (TextView) findViewById(R.id.textView4);
        mAltitudeValue = (TextView) findViewById(R.id.textView5);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if ( mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null){
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
            isSensorPresent = true;
        }else{
            isSensorPresent = false;
            mPressureValue.setText("There is not pressure sensor in your device");
            mAltitudeValue.setText("");

        }

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

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(isSensorPresent){
            float pressure = event.values[0];
            mPressureValue.append(pressure + "\n");
            float altitude = SensorManager.getAltitude(SensorManager.PRESSURE_STANDARD_ATMOSPHERE, pressure);

            mAltitudeValue.append(altitude + "\n");
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
