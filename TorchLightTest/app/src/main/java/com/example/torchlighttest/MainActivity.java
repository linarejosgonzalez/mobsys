package com.example.torchlighttest;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private boolean isSensorPresent = false;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TextView textView;

    private boolean isFlashLightOn = false;

    private Camera mCamera;
    private Camera.Parameters mParameters;
    private SurfaceTexture mPreviewTexture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)!=null){
            isSensorPresent = true;
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        }else{
            isSensorPresent = false;
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float distanceFromPhone = event.values[0];
        if(distanceFromPhone < mSensor.getMaximumRange()){
            if(!isFlashLightOn){
                try {
                    turnTorchLightOn();
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }

        }else{
            if(isFlashLightOn){
                try {
                    turnTorchLightOff();
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        }
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



    public void turnTorchLightOn() throws CameraAccessException {
        CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraId = camManager.getCameraIdList()[0]; // usualmente la camara delantera esta en la posicion 0
        camManager.setTorchMode(cameraId, true);
        isFlashLightOn = true;
    }
    public void turnTorchLightOff() throws CameraAccessException {
        CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraId = camManager.getCameraIdList()[0]; // usualmente la camara delantera esta en la posicion 0
        camManager.setTorchMode(cameraId, false);
        isFlashLightOn = false;
    }
}
