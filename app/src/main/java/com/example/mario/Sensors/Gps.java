package com.example.mario.Sensors;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import im.delight.android.location.SimpleLocation;


public class Gps {


    private Context context;
    private LocationManager locationManager;
    private SimpleLocation location;
    private double lat, lon;

    public Gps(Activity activity) {
        this.location = new SimpleLocation(activity);

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        }
        location.beginUpdates();

        setLat(location.getLatitude());
        setLon(location.getLongitude());

    }

    public double getLat() {
        return lat;
    }

    public Gps setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public Gps setLon(double lon) {
        this.lon = lon;
        return this;
    }
}
