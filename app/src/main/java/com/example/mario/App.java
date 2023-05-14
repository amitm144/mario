package com.example.mario;

import android.app.Application;

import com.example.mario.Sensors.MSP;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MSP.init(this);
    }
}
