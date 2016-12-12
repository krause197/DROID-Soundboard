package com.example.guest.soundboard;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, SensorEventListener {
    private String TAG = MainActivity.class.getSimpleName();
    private ImageView mCar;
    private ImageView mChainsaw;
    private MediaPlayer car;
    private MediaPlayer carHorn;
    private MediaPlayer chainsaw;
    private MediaPlayer marching;
    private MediaPlayer jungle;
    private GestureDetector mGestureDetector;
    private GestureDetector mChainsawDetector;

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 500;


    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCar = (ImageView) findViewById(R.id.car);
        mChainsaw = (ImageView) findViewById(R.id.chainsaw);
        context = this;
        car = MediaPlayer.create(this, R.raw.car);
        chainsaw = MediaPlayer.create(this, R.raw.chainsaw);
        marching = MediaPlayer.create(this, R.raw.marching);
        jungle = MediaPlayer.create(this, R.raw.jungle);
        carHorn = MediaPlayer.create(this,R.raw.carhorn);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensor, mSensorManager.SENSOR_DELAY_NORMAL);


        Android_Gesture_Detector chainsaw_gesture_detector = new Android_Gesture_Detector() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                chainsaw.start();
                return true;
            }
        };

        // Create an object of the Android_Gesture_Detector  Class
        Android_Gesture_Detector android_gesture_detector = new Android_Gesture_Detector(){

            @Override
            public void onSwipeUp() {
                car.start();
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                carHorn.start();
                return true;
            }

        };
        // Create a GestureDetector
        mGestureDetector = new GestureDetector(this, android_gesture_detector);
        mChainsawDetector = new GestureDetector(this, chainsaw_gesture_detector);
        mCar.setOnTouchListener(this);
        mChainsaw.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view == mCar) {
            mGestureDetector.onTouchEvent(motionEvent);
            return true;
        }
        else if(view == mChainsaw){
            mChainsawDetector.onTouchEvent(motionEvent);
            return true;
        }
        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;

        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
                Log.d(TAG, "x is : " + x);
                Log.d(TAG, "y is : " + y);
                Log.d(TAG, "z is : " + z);
                if (speed > SHAKE_THRESHOLD) {
                    Log.d("SensorEventListener", "shaking");


                    last_x = x;
                    last_y = y;
                    last_z = z;

                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    class Android_Gesture_Detector implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        public void onSwipeDown() {
        }

        public void onSwipeLeft() {
        }

        public void onSwipeRight() {
        }

        public void onSwipeUp() {
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d("Gesture ", " onDown");
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d("Gesture ", " onSingleTapConfirmed");
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d("Gesture ", " onSingleTapUp");
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d("Gesture ", " onShowPress");
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d("Gesture ", " onDoubleTap");
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.d("Gesture ", " onDoubleTapEvent");
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.d("Gesture ", " onLongPress");
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            Log.d("Gesture ", " onScroll");
            if (e1.getY() < e2.getY()){
                Log.d("Gesture ", " Scroll Down");
            }
            if(e1.getY() > e2.getY()){
                Log.d("Gesture ", " Scroll Up");
            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeDown();
                        } else {
                            onSwipeUp();
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }
}
