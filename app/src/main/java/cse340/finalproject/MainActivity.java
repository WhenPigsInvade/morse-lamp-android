package cse340.finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends DrawerActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState, R.id.activity_main);

        // TODO: Set onClickListener() for the FLASH! button
        Button flash = findViewById(R.id.flash);
        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    // Reminder to add activity to android manifest
    // Source: https://stackoverflow.com/questions/24853942/android-unable-to-find-explicit-activity-class


    // TODO: Translate text to morse. We should try to simplify it by translating text
    // straight into time spacing.


}