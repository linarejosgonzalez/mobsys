package com.example.sensorservice;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = Activity.class.getSimpleName();
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private MediaPlayer mMediaPlayer;

    private boolean isPlaying = false;
    private float mThreshold = 2.5f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null){
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        }else{
            Log.d(TAG, "The device does not have a gyroscope");
        }
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.mario);
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                isPlaying=false;
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double rate =Math.sqrt(Math.pow(event.values[0], 2) + Math.pow(event.values[1], 2) + Math.pow(event.values[2], 2));
        if(rate > mThreshold){
            if(!isPlaying){
                mMediaPlayer.start();
                isPlaying = true;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public IBinder onBind(Intent intent){
        return null;
    }

    public int onStartCommand (Intent intent, int flags, int id){
        Context context = getApplicationContext();
        CharSequence text = "Service Started";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context,  text, duration);
        toast.show();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
        return Service.START_NOT_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        mSensorManager.unregisterListener(this);
    }

    public void startMyService(View v){
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this.toString());
                startService(i);
            }
        });
    }
}
