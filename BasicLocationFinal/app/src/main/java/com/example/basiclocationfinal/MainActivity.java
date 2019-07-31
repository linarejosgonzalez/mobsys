package com.example.basiclocationfinal;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private static final String TAG = Activity.class.getSimpleName();

    private TextView textView;
    private LocationManager mLocationManager;
    private LocationProvider mLocationProvider;
    private LocationListener mLocationListener;
    private List<String> providers;

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 123;
    private static final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION = 456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION
                        }, MY_PERMISSIONS_REQUEST_FINE_LOCATION);

                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION
                        }, MY_PERMISSIONS_REQUEST_COARSE_LOCATION);




        }else{
            providers = mLocationManager.getAllProviders();

            for (String name : providers) {
                mLocationProvider = mLocationManager.getProvider(name);
                Log.d(TAG, mLocationProvider.getName() + " -- isProviderEnabled(): "
                        + mLocationManager.isProviderEnabled(name));
                Log.d(TAG, "requiresCell(): " + mLocationProvider.requiresCell());
                Log.d(TAG, "requiresNetwork(): " +
                        mLocationProvider.requiresNetwork());
                Log.d(TAG, "requiresSatellite(): " +
                        mLocationProvider.requiresSatellite());
            }

            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            String name = mLocationManager.getBestProvider(criteria,
                    true);
            Log.d(TAG, name);

            mLocationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        String s = "Latitude: " + location.getLatitude() +
                                "\nLongitude: " + location.getLongitude();
                        textView.setText(s);
                    }else{
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };

        }







    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

           /* if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION
                        }, MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION
                        }, MY_PERMISSIONS_REQUEST_COARSE_LOCATION);
            }*/


        }else{
            mLocationManager.requestLocationUpdates(mLocationManager.GPS_PROVIDER, 3000, 0, mLocationListener);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(mLocationManager!= null){
            mLocationManager.removeUpdates(mLocationListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION:{
                if(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    providers = mLocationManager.getAllProviders();

                    for (String name : providers) {
                        mLocationProvider = mLocationManager.getProvider(name);
                        Log.d(TAG, mLocationProvider.getName() + " -- isProviderEnabled(): "
                                + mLocationManager.isProviderEnabled(name));
                        Log.d(TAG, "requiresCell(): " + mLocationProvider.requiresCell());
                        Log.d(TAG, "requiresNetwork(): " +
                                mLocationProvider.requiresNetwork());
                        Log.d(TAG, "requiresSatellite(): " +
                                mLocationProvider.requiresSatellite());
                    }

                    Criteria criteria = new Criteria();
                    criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                    criteria.setPowerRequirement(Criteria.POWER_LOW);
                    String name = mLocationManager.getBestProvider(criteria,
                            true);
                    Log.d(TAG, name);

                    mLocationListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            if (location != null) {
                                String s = "Latitude: " + location.getLatitude() +
                                        "\n Longitude: " + location.getLongitude();
                                textView.setText(s);
                            }else{
                            }
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    };

                }else{
                    finish();
                }
            }
            case MY_PERMISSIONS_REQUEST_COARSE_LOCATION: {
                if(grantResults.length > 0
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED){

                }else{

                }
            }
        }
    }

}
