package com.example.screenbrightness;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = Activity.class.getSimpleName();

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private  ContentResolver mContentResolver;
    private Window mWindow;
    private int light = 0;

    private boolean isLight = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)!= null){
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            isLight = true;
        } else{
            isLight = false;
            Log.d(TAG, "There is not light sensor in this device");
        }
        initScreenBrightness();
        Settings.System.putInt(mContentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

        try {
            light = Settings.System.getInt(mContentResolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float lightvalue = event.values[0];
        if(lightvalue > 0 && lightvalue < 1000){
            changeScreenBrightness(lightvalue/100);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    public void initScreenBrightness(){
        mContentResolver = getContentResolver();
        mWindow = getWindow();

    }

    public void changeScreenBrightness(float brightness){
        Settings.System.putInt(mContentResolver, Settings.System.SCREEN_BRIGHTNESS, (int) brightness);
        WindowManager.LayoutParams layoutpars = mWindow.getAttributes();
        layoutpars.screenBrightness = brightness/(float) 255;
        mWindow.setAttributes(layoutpars);

    }
}
