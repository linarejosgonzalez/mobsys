package com.example.sensordemo1;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private static final String TAG = Activity.class.getSimpleName();
    TextView textView;
    SensorManager mSensorManager;
    Sensor mSensor;
    SensorEventListener mSensorEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null){
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }else{
            Log.d(TAG, "Not light sensor found");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float lux = event.values[0];
        if (lux < SensorManager.LIGHT_SUNLIGHT){
            textView.setText(Float.toString(lux));
        }else{
            // It might not change because the LIGHT_SUNLIGHT constant is
            // equal to 110000.0f, so it is hard to reach
            textView.setText("Sunny");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mSensor,  SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
