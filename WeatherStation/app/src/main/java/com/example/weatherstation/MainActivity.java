package com.example.weatherstation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToTemperatureActivity(v);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToHumidityActivity(v);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToPressureAndAltitudeActivity(v);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCompareSensorActivity(v);
            }
        });

    }

    public void navigateToTemperatureActivity(View v){
        Intent intent = new Intent (v.getContext(), CheckTemperature.class);
        startActivityForResult(intent, 0);
    }
    public void navigateToHumidityActivity(View v){
        Intent intent = new Intent (v.getContext(), CheckHumidity.class);
        startActivityForResult(intent, 0);
    }
    public void navigateToPressureAndAltitudeActivity(View  v){
        Intent intent = new Intent (v.getContext(), CheckPressureAndAltitude.class);
        startActivityForResult(intent, 0);
    }

    public void navigateToCompareSensorActivity(View v){
        Intent intent = new Intent (v.getContext(), CompareSensorActivity.class);
        startActivityForResult(intent, 0);
    }
}
