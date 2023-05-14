package com.example.mario.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mario.Model.User;
import com.example.mario.R;
import com.example.mario.Sensors.Gps;
import com.example.mario.Sensors.Gyroscope;
import com.example.mario.databinding.FragmentGameBinding;

import java.util.Random;

public class GameFragment extends Fragment {

    private final int marioImg = R.drawable.mario, DIM = 5, LIFE = 3, SPEED = 1000 ,STARTING_MARIO_POS = 2;
    private final Handler handler = new Handler();
    private int speed;
    private FragmentGameBinding binding;
    private ImageView[] marioMovement, life;
    private ImageView[][] gift_img;
    private int[][] gift_int;
    private int currentMarioPos, currentLife, currentScore;
    private Gyroscope gyroscope;
    private Bundle bundle;

    private String username;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(inflater, container, false);
        startNewGame();

        return binding.getRoot();
    }

    private void getSetting() {
        bundle = getArguments();
        setMovementType();
        setSpeed();
        setUserName();
    }

    private void setUserName() {
        username = bundle.getString("username");
    }

    public void setMovementType() {
        boolean movement = bundle.getBoolean("buttons");
        if(!movement) gyroscope.start();
        int visible = (movement? View.VISIBLE: View.INVISIBLE);
        binding.rightBtn.setVisibility(visible);
        binding.leftBtn.setVisibility(visible);
    }

    private void setSpeed() {
        int s = bundle.getInt("speed");
        this.speed = SPEED - s * 100;
    }

    private void startNewGame() {
        reset();
        getSetting();
        startTimer();
    }

    private void reset() {
        currentMarioPos = STARTING_MARIO_POS;
        currentLife = LIFE;
        currentScore = 0;

        marioMovement = new ImageView[]{binding.marioImage40, binding.marioImage41, binding.marioImage42, binding.marioImage43, binding.marioImage44};
        for (ImageView marioImg : marioMovement) marioImg.setImageResource(0);

        life = new ImageView[]{binding.heart1, binding.heart2, binding.heart3};

        gift_img = new ImageView[][]
                {
                        {binding.image00, binding.image01, binding.image02, binding.image03, binding.image04},
                        {binding.image10, binding.image11, binding.image12, binding.image13, binding.image14},
                        {binding.image20, binding.image21, binding.image22, binding.image23, binding.image24},
                        {binding.image30, binding.image31, binding.image32, binding.image33, binding.image34},
                        {binding.image40, binding.image41, binding.image42, binding.image43, binding.image44},
                };

        gift_int = new int[DIM][DIM];
        marioMovement[currentMarioPos].setImageResource(marioImg);

        setMovement();
        displayLife();
        displayScore();

    }

    private void setMovement() {
        setGyroscope();
        setButtons();
    }

    private void setGyroscope() {
        gyroscope = new Gyroscope(getContext(), new Gyroscope.CallBack_Move() {
            @Override
            public void moveRight() {
                marioMove(1);
            }

            @Override
            public void moveLeft() {
                marioMove(-1);
            }
        });
    }

    public void setButtons() {
        binding.rightBtn.setOnClickListener(v -> marioMove(1));
        binding.leftBtn.setOnClickListener(v -> marioMove(-1));
    }

    public void marioMove(int d) {
        marioMovement[currentMarioPos].setImageResource(0);
        currentMarioPos += d;
        if (currentMarioPos < 0) currentMarioPos = 0;
        if (currentMarioPos > 4) currentMarioPos = 4;
        marioMovement[currentMarioPos].setImageResource(marioImg);
        checkHit();
    }




    private void startTimer() {
        handler.postDelayed(random_gift, speed);
        handler.postDelayed(shift_gift, 2 * speed);
    }

    public void shift() {
        for (int i = DIM - 1; i >= 0; i--) {
            for (int j = DIM - 1; j >= 0; j--) {
                changeImg(i, j);
            }
        }
    }    private final Runnable shift_gift = new Runnable() {
        public void run() {
            handler.postDelayed(shift_gift, speed);
            shift();
            checkHit();

        }
    };

    public void changeImg(int row, int col) {
        if (row == 0)
            gift_int[row][col] = 0;
        else
            gift_int[row][col] = gift_int[row - 1][col];

        gift_img[row][col].setImageResource(convertor(gift_int[row][col]));
    }

    private void setRandom_gift() {
        Random random = new Random();
        int gift = getCustomRandomNum(random);
        int pos = random.nextInt(5);
        gift_int[0][pos] = gift;
        gift_img[0][pos].setImageResource(convertor(gift));

    }

    public void checkHit() {
        if (gift_int[4][currentMarioPos] != 0) {
            hit(gift_int[4][currentMarioPos]);
            gift_img[4][currentMarioPos].setImageResource(0);
        }

    }

    public void hit(int type) {
        switch (type) {
            case 1:
                losePoint();
                break;
            case 2:
                gainScore();
                break;
        }

    }

    private void gainScore() {
        currentScore += 10;
        displayScore();

    }

    private void displayScore() {
        binding.scoreLBL.setText(String.valueOf(currentScore));
    }

    private void losePoint() {
        currentLife--;
        vibrate();
        displayLife();
        if (currentLife == 0) gameOver();

    }

    private void vibrate() {
        ((Vibrator) requireContext().getSystemService(getContext().VIBRATOR_SERVICE)).vibrate(200);

    }    private final Runnable random_gift = new Runnable() {
        public void run() {
            handler.postDelayed(random_gift, speed);
            setRandom_gift();
        }
    };

    private void gameOver() {
        binding.rightBtn.setVisibility(View.INVISIBLE);
        binding.leftBtn.setVisibility(View.INVISIBLE);
        gyroscope.stop();

        stopTimer();
        showDialog();
        Gps gps = new Gps(getActivity());

        ScoreFragment.getInstance().addScore(new User(username, currentScore, gps.getLat(), gps.getLon()));


    }

    private void stopTimer() {
        handler.removeCallbacks(random_gift);
        handler.removeCallbacks(shift_gift);

    }

    @Override
    public void onStop() {
        super.onStop();
        stopTimer();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("GameOver")
                .setMessage("Do you want to play another game ? ")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startNewGame();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MenuFragment menuFragment = new MenuFragment();

                        FragmentManager fm = getParentFragmentManager();
                        FragmentTransaction transaction = fm.beginTransaction();
                        transaction.replace(R.id.body_container, menuFragment);
                        transaction.commit();


                    }
                })
                .create()
                .show();


    }

    private void displayLife() {
        for (int i = 0; i < currentLife; i++) {
            life[i].setVisibility(View.VISIBLE);
        }
        for (int i = currentLife; i < LIFE; i++) {
            life[i].setVisibility(View.INVISIBLE);
        }

    }

    private int getCustomRandomNum(Random random) {
        int randomNumber = random.nextInt(100);
        if (randomNumber < 30) {
            return 0;
        } else if (randomNumber < 80) {
            return 1;
        } else {
            return 2;
        }
    }

    private int convertor(int i) {
        int rv;
        switch (i) {
            case 1:
                rv = R.drawable.goomba;
                break;
            case 2:
                rv = R.drawable.coin;
                break;
            default:
                rv = 0;
                break;
        }

        return rv;
    }







}









