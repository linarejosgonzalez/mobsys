package com.example.shakingcolor;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private boolean color = false;
    private View view;
    private long lastUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.textView);
        view.setBackgroundColor(Color.GREEN);

        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        lastUpdate = System.currentTimeMillis();

    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            float[] values = sensorEvent.values;

            float x = values[0];
            float y = values[1];
            float z = values[2];

            float accelerationSquareRoot = (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
            long actualTime = sensorEvent.timestamp;

            if(accelerationSquareRoot >= 2){
                if(actualTime - lastUpdate <500){
                    return;
                }
                Toast.makeText(this, "Device was shaken", Toast.LENGTH_SHORT).show();

                lastUpdate = actualTime;

                if(color){
                    view.setBackgroundColor(Color.GREEN);
                }else{
                    view.setBackgroundColor(Color.RED);
                }
                color = !color;

            }


        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
    }


}
