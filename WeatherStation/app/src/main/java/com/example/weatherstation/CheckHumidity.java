package com.example.weatherstation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CheckHumidity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensorTemperature;
    private Sensor mSensorHumidity;
    private boolean isTemperaturePresent = false;
    private boolean isHumidityPresent = false;

    private TextView relativeHumidity;
    private TextView absoluteHumidity;
    private TextView dewPoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_humidity);

        relativeHumidity = (TextView) findViewById(R.id.textView1);
        absoluteHumidity = (TextView) findViewById(R.id.textView2);
        dewPoint = (TextView) findViewById(R.id.textView3);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)!=null){
            mSensorHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            isHumidityPresent = true;
        }else{
            isHumidityPresent = false;
            relativeHumidity.setText("There is not a humidity sensor in this device \n");
        }
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)!=null){
            mSensorTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isTemperaturePresent = true;
        }else{
            isTemperaturePresent = false;
            relativeHumidity.setText("There is not a temperature sensor in this device");
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        if(isTemperaturePresent){
            mSensorManager.registerListener(this, mSensorTemperature, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(isHumidityPresent){
            mSensorManager.registerListener(this, mSensorHumidity, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        if(isTemperaturePresent){
            mSensorManager.unregisterListener(this);
        }
        if(isHumidityPresent){
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mSensorManager != null){
            mSensorManager = null;
        }
        if(mSensorTemperature != null){
            mSensorTemperature = null;
        }
        if(mSensorHumidity != null){
            mSensorHumidity = null;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        relativeHumidity.append(event.values[0] + "\n");

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // These two methods have to be implemented, but implementation does not come
    // in the tutorial PDF.
    public void calculateAbsoluteHumidity(){

    }
    public void calculateDewPoint(){

    }

}
