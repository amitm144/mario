package com.example.mario.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mario.R;
import com.example.mario.databinding.FragmentMenuBinding;


public class MenuFragment extends Fragment {

    private FragmentMenuBinding binding;
    private int speed;

    private GameFragment gameFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMenuBinding.inflate(inflater, container, false);   // Inflate the layout for this fragment
//        restoreData();

        binding.playBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameFragment = new GameFragment();

                String username = binding.userName.getText().toString();
                if (username.isEmpty()) username = "guest";

                Bundle bundle = new Bundle();
                bundle.putBoolean("buttons", binding.buttonsSWITCH.isChecked());
                bundle.putInt("speed", speed);
                bundle.putString("username", username);
                gameFragment.setArguments(bundle);

                FragmentManager fm = getParentFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.body_container, gameFragment);
                transaction.commit();
            }
        });


        binding.allScoresBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getParentFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.body_container, ScoreFragment.getInstance());
                transaction.commit();
            }
        });


        return binding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        setSpeed();

    }

    public void setSpeed() {
        binding.speedSEEKBAR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.speedLBL.setText(String.valueOf(progress));
                speed = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


    }


}