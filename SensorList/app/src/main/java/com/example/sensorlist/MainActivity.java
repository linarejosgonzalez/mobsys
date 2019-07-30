package com.example.sensorlist;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView textView;
    private SensorManager mSensorManager;
    private List<Sensor> mSensorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);

        /**
         * Necessary hashmap to show a String description in the type, not only a number
         * */
        HashMap<Integer, String> sensorTypes = new HashMap< Integer, String>();

        sensorTypes.put(Sensor.TYPE_ACCELEROMETER, "TYPE_ACCELEROMETER");
        sensorTypes.put(Sensor.TYPE_AMBIENT_TEMPERATURE, "TYPE_AMBIENT_TEMPERATURE");
        sensorTypes.put(Sensor.TYPE_GRAVITY, "TYPE_GRAVITY");
        sensorTypes.put(Sensor.TYPE_GYROSCOPE, "TYPE_GYROSCOPE");
        sensorTypes.put(Sensor.TYPE_LIGHT, "TYPE_LIGHT");
        sensorTypes.put(Sensor.TYPE_LINEAR_ACCELERATION, "TYPE_LINEAR_ACCELERATION");
        sensorTypes.put(Sensor.TYPE_MAGNETIC_FIELD, "TYPE_MAGNETIC_FIELD");
        sensorTypes.put(Sensor.TYPE_ORIENTATION, "TYPE_ORIENTATION");
        sensorTypes.put(Sensor.TYPE_PRESSURE, "TYPE_PRESSURE");
        sensorTypes.put(Sensor.TYPE_PROXIMITY, "TYPE_PROXIMITY");
        sensorTypes.put(Sensor.TYPE_RELATIVE_HUMIDITY, "TYPE_RELATIVE_HUMIDITY");
        sensorTypes.put(Sensor.TYPE_ROTATION_VECTOR, "TYPE_ROTATION_VECTOR");
        sensorTypes.put(Sensor.TYPE_TEMPERATURE, "TYPE_TEMPERATURE");
        sensorTypes.put(Sensor.TYPE_AMBIENT_TEMPERATURE, "TYPE_AMBIENT_TEMPERATURE");


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder message = new StringBuilder(2048);
        message.append("The sensors on this device are: \n");
        for (Sensor mSensor : mSensorList) {
            message.append("Name: " + mSensor.getName() + "\n");
            message.append("ID: " + mSensor.getId() + "\n");
            message.append("Type: " + sensorTypes.get(mSensor.getType()) + "\n");
            message.append("Version: " + mSensor.getVersion() + "\n");
            message.append("Resolution: " + mSensor.getResolution() + "\n");
            message.append("Maximum Range: " + mSensor.getMaximumRange() + "\n");
            message.append("Power:" + mSensor.getPower() + "\n");
            message.append("Vendor: " + mSensor.getVendor() + "\n\n");

        }

        textView.setText(message);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
