package com.example.compassapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView mCompass;
    private SensorManager mSensorManager;
    private Sensor mAccelerometerSensor, mMagnetometerSensor;
    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;
    private long lastUpdateTime = 0;
    private float[] mRotationMatrix = new float[9];
    private float[] mOrientation = new float[3];
    private float mCurrentDegree = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCompass = (ImageView) findViewById(R.id.compass);

        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!=null){
            mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mLastAccelerometerSet = true;

        }else{
            mLastAccelerometerSet=false;
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)!=null){
            mMagnetometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            mLastMagnetometerSet = true;

        }else{
            mLastMagnetometerSet = false;
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        rotateUsingOrientationAPI(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume(){
        super.onResume();
        if(mLastAccelerometerSet){
            mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_UI);
        }
        if(mLastMagnetometerSet){
            mSensorManager.registerListener(this, mMagnetometerSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        if(mLastAccelerometerSet){
            mSensorManager.unregisterListener(this, mAccelerometerSensor);
        }
        if(mLastMagnetometerSet){
            mSensorManager.unregisterListener(this, mMagnetometerSensor);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mSensorManager!=null){
            mSensorManager = null;
        }
        if(mAccelerometerSensor!=null){
            mAccelerometerSensor=null;
        }
        if(mMagnetometerSensor != null){
            mMagnetometerSensor = null;
        }
    }

    public void rotateUsingOrientationAPI(SensorEvent event){
        if(event.sensor == mAccelerometerSensor){
            System.arraycopy(event.values,0,mLastAccelerometer,0,event.values.length);
            mLastAccelerometerSet = true;
        }else if(event.sensor == mMagnetometerSensor){
            System.arraycopy(event.values,0,mLastMagnetometer,0,event.values.length);
            mLastMagnetometerSet = true;
        }

        if((mLastAccelerometerSet) && (mLastMagnetometerSet) && ((System.currentTimeMillis() - lastUpdateTime) >250)){

            SensorManager.getRotationMatrix(mRotationMatrix, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(mRotationMatrix, mOrientation);

            float azimuthInRadians = mOrientation[0];
            float azimuthInDegrees = (float) (Math.toDegrees(azimuthInRadians) + 360) %360;

            RotateAnimation mRotateAnimation = new RotateAnimation(
                    mCurrentDegree,
                    -azimuthInDegrees,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f);
            mRotateAnimation.setDuration(250);
            mRotateAnimation.setFillAfter(true);
            mCompass.startAnimation(mRotateAnimation);

            mCurrentDegree = -azimuthInDegrees;
            lastUpdateTime = System.currentTimeMillis();
        }

    }

}
