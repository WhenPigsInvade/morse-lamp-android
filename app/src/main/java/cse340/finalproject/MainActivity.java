package cse340.finalproject;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            String camID = null;
            try {
                camID = manager.getCameraIdList()[0];
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
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