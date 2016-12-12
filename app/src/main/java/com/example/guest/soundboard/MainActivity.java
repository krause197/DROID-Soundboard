package com.example.guest.soundboard;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    TextView mTextView;
    Integer timesTouched = 0;
    MediaPlayer car;
    MediaPlayer chainsaw;
    MediaPlayer marching;
    MediaPlayer jungle;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.touch);
        context = this;
        car = MediaPlayer.create(this,R.raw.car);
        chainsaw = MediaPlayer.create(this,R.raw.chainsaw);
        marching = MediaPlayer.create(this,R.raw.marching);
        jungle = MediaPlayer.create(this,R.raw.jungle);

//        mTextView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                timesTouched++;
//                Log.d(TAG, "onTouch: " + timesTouched.toString());
//
//                return true;
//            }
//        });

        mTextView.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeDown() {
                car.start();
                Toast.makeText(MainActivity.this, "Down", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeLeft() {
                chainsaw.start();
                Toast.makeText(MainActivity.this, "Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeRight() {
                marching.start();
                Toast.makeText(MainActivity.this, "Right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeUp() {
                jungle.start();
                Toast.makeText(MainActivity.this, "Up", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
