package cse340.finalproject;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Queue;
import java.util.TreeMap;

public class MainActivity extends DrawerActivity{

    private Handler mHandler;
    private TreeMap<Character, ArrayList<Integer>> mTiming;
    private Queue<Integer> mQueue;
    public static final int DELAY = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState, R.id.activity_main);

        mHandler = new Handler(Looper.getMainLooper());
        mQueue = new ArrayDeque<>();

        // Adds a-z to timing
        // Timing contains spacing values with key:value being char:timing
        String[] letters = { ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..",
                ".---", "-.-", ".-..", "--", "-.", "---", ".---.", "--.-", ".-.",
                "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.." };

        mTiming = new TreeMap<>();
        for(int i = 0; i < letters.length; i++){
            ArrayList<Integer> sequence = new ArrayList<>();
            for(int j = 0; j < letters[i].length(); j++){
                if(letters[i].charAt(j) == '.'){
                    sequence.add(1);
                } else {
                    sequence.add(3);
                }
            }
            mTiming.put((char)('a' + i), sequence);
        }


        // Add numbers to mTiming
        String[] numbers = {"-----", ".----", "..---", "...--", "....-",
                ".....", "-....", "--...", "---..", "----."};

        for(int i = 0; i < numbers.length; i++){
            ArrayList<Integer> sequence = new ArrayList<>();
            for(int j = 0; j < letters[i].length(); j++){
                if(letters[i].charAt(j) == '.'){
                    sequence.add(1);
                } else {
                    sequence.add(3);
                }
            }
            mTiming.put((char)('0' + i), sequence);
        }

        // Add special characters to mTiming
        // ',' '.' '?' is  "--..--", ".-.-.-", "..--.." respectively
        char[] specialLetter = {',', '.', '?'};
        String[] specialMorse = {"--..--", ".-.-.-", "..--.."};
        for(int i = 0; i < specialMorse.length; i++) {
            ArrayList<Integer> sequence = new ArrayList<>();
            for (int j = 0; j < specialMorse[i].length(); j++) {
                if(specialMorse[i].charAt(j) == '.'){
                    sequence.add(1);
                } else {
                    sequence.add(3);
                }
            }
            mTiming.put(specialLetter[i], sequence);
        }

        // TODO: Set onClickListener() for the FLASH! button
        Button flash = findViewById(R.id.flash);
        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gets permission to use camera
                // https://www.tutorialspoint.com/how-to-turn-on-flash-light-programmatically-in-android
                Log.i("TAG", "FLash clicked");
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    Log.i("TAG", "Camera flash perms requested");
                    // Permission is not granted
                    String[] perm = {Manifest.permission.CAMERA};
                    int camRequestCode = 100;
                    ActivityCompat.requestPermissions(MainActivity.this, perm, camRequestCode);
                }

                // If phone has no camera
                // Source: https://www.tutorialspoint.com/how-to-turn-on-flash-light-programmatically-in-android
                if(!getApplicationContext().getPackageManager()
                        .hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)){
                    Log.i("TAG", "Has not camera");
                    return;
                }

                Log.i("TAG", "Removing previous runnable");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    if(mHandler.hasCallbacks(runnable)){
                        mHandler.removeCallbacks(runnable);
                    }
                }
                String message = ((EditText)findViewById(R.id.textbox)).getText().toString();
                sendToQueue(message.toLowerCase());
                mHandler.post(runnable);
            }
        });
    }

    // Reminder to add activity to android manifest
    // Source: https://stackoverflow.com/questions/24853942/android-unable-to-find-explicit-activity-class


    // Runnable to turn on and off flashlight according to message in queue
    // Source: https://stackoverflow.com/a/40748375

    // Turn on flashlight and check for permission
    // Source: https://www.tutorialspoint.com/how-to-turn-on-flash-light-programmatically-in-android

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            String camID = null;
            try {
                camID = manager.getCameraIdList()[0];

                //Check if flashlight is available
                // Source: https://www.demo2s.com/android/android-cameramanager-getcameracharacteristics-nonnull-string-camerai.html#:~:text=Android%20CameraManager%20getCameraCharacteristics%20%28%40NonNull%20String%20cameraId%29%201%20Introduction,use%20Java%20CameraManager%20getCameraCharacteristics%20%28%40NonNull%20String%20cameraId%29%20
                if(!manager.getCameraCharacteristics(camID).
                        get(CameraCharacteristics.FLASH_INFO_AVAILABLE)){
                    Log.i("TAG", "Flash not available");
                    getString(R.string.no_flashlight);
                    Toast toast = Toast.makeText(MainActivity.this,
                            R.string.no_flashlight, Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }

            Log.i("TAG", "mQueue is empty: " + mQueue.isEmpty());
            while(!mQueue.isEmpty()){
                int time = mQueue.remove();
                if(time > 0){
                    try {
                        manager.setTorchMode(camID, true);   //Turn ON
                        Thread.sleep(time * DELAY);
                        manager.setTorchMode(camID, false);
                    } catch (CameraAccessException | InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Thread.sleep(Math.abs(time) * DELAY);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            mHandler.removeCallbacks(this);
        }
    };

    public void sendToQueue(String message){

        for(int i = 0; i < message.length(); i++){
            // If letter is a-z
            char ltr = message.charAt(i);
            if(mTiming.containsKey(ltr)){
                mQueue.addAll(mTiming.get(ltr));
                mQueue.add(-1);
            } else if(ltr == ' '){
                mQueue.add(-2);
            } else {
                Log.i("TAG", "Unknown char: " + ltr);
            }
        }

    }



}