package com.example.mario.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.mario.Fragments.MenuFragment;
import com.example.mario.Fragments.ScoreFragment;
import com.example.mario.Model.Scores;
import com.example.mario.R;
import com.example.mario.Sensors.MSP;
import com.example.mario.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import im.delight.android.location.SimpleLocation;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().hide();

        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new MenuFragment()).commit();

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.body_container);
            if (!(currentFragment instanceof MenuFragment))
                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new MenuFragment()).commit();
            else
                finish();


            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Scores scores =  ScoreFragment.getInstance().getScores();
        String json = new Gson().toJson(scores);
        MSP.getInstance().saveString("SCORES",json);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String json2 = MSP.getInstance().readString("SCORES", "");

        Scores scores;
        try {
            scores = new Gson().fromJson(json2, Scores.class);
            if (scores != null)
                ScoreFragment.getInstance().setScores(scores);

        } catch (Exception ex) {
            Log.d("ptt", "onStart: null");
        }

    }
}