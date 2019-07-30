package com.example.approtatetest;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensorGyroscope;
    private boolean isGyroscope = false;
    private Sensor mSensorRotationVector;
    private boolean isRotationVector = false;

    private SensorEventListener gyroscopeSensorListener;
    private boolean activateGyroscope = true;
    private boolean activateRotationVector = false;
    private Button buttonGyroscope;
    private Button buttonRotationVector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)!=null){
            mSensorGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            isGyroscope = true;
        }
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)!=null){
            mSensorRotationVector = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            isRotationVector = true;
        }else{
            Toast.makeText(this, "this device does not contain a rotation vector", Toast.LENGTH_SHORT).show();
        }

        buttonGyroscope = (Button) findViewById(R.id.buttongyroscope);
        buttonRotationVector = (Button) findViewById(R.id.buttonrotationvector);

        buttonGyroscope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateGyroscope = true;
                activateRotationVector = false;
            }
        });

        buttonRotationVector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateGyroscope = false;
                activateRotationVector = true;
            }
        });




    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float axisY = event.values[1];

        if(activateGyroscope){
            if(axisY > 0.5){
                getWindow().getDecorView().setBackgroundColor(Color.BLUE);
            }else if(axisY < -0.5){
                getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
            }
        }else if(activateRotationVector){

            SensorEventListener rvListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {

                    float[] rotationMatrix = new float[16];
                    SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);

                    //Remap coordinate system
                    float[] remappedRotationMatrix = new float[16];
                    SensorManager.remapCoordinateSystem(rotationMatrix,SensorManager.AXIS_X, SensorManager.AXIS_Z, remappedRotationMatrix);

                    float[] orientations = new float[3];
                    SensorManager.getOrientation(remappedRotationMatrix, orientations);

                    for(int i = 0; i <3; i++){
                        orientations[i] = (float) (Math.toDegrees(orientations[i]));
                    }

                    float axisY = orientations[1];
                    if(axisY > 45){
                        getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                    }else if(axisY < -45){
                        getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                    }

                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            };
            rvListener.onSensorChanged(event);

        }


    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause(){
        super.onPause();
        if(isGyroscope){
            mSensorManager.unregisterListener(this, mSensorGyroscope);
        }
        if(isRotationVector){
            mSensorManager.unregisterListener(this, mSensorRotationVector);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();

        mSensorManager.registerListener(this, mSensorGyroscope, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, mSensorRotationVector, SensorManager.SENSOR_DELAY_UI);
    }

}
