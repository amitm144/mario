package com.example.mario.Sensors;

import android.content.Context;
import android.content.SharedPreferences;

public class MSP {

    private static final String SP_FILE_NAME = "SP_FILE_NAME";
    private static MSP instance;
    private SharedPreferences prefs = null;

    private MSP(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new MSP(context);
        }
    }

    public static MSP getInstance() {
        return instance;
    }


    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String readString(String key, String def) {
        String value = prefs.getString(key, def);
        return value;
    }


}
