package com.example.mario.Sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

public class Gyroscope {

    private static SensorManager mySensorManager;
    private static Sensor sensor;
    private static int sensorType = Sensor.TYPE_GYROSCOPE;
    private CallBack_Move callBack_Move;
    SensorEventListener sensorListener = new SensorEventListener() {


        @Override
        public void onSensorChanged(SensorEvent event) {

            float y = event.values[1];

            if (y > 0.6) callBack_Move.moveRight();
            if (y < -0.6) callBack_Move.moveLeft();

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }


    };


    public Gyroscope(Context context, CallBack_Move callBack_Move) {
        this.callBack_Move = callBack_Move;

        mySensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = mySensorManager.getDefaultSensor(sensorType);
        if (sensor == null)
            Toast.makeText(context, "No Sensor!", Toast.LENGTH_SHORT).show();


    }

    public void start() {
        mySensorManager.registerListener(sensorListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop() {
        mySensorManager.unregisterListener(sensorListener);
    }

    public interface CallBack_Move {
        void moveRight();

        void moveLeft();
    }


}
