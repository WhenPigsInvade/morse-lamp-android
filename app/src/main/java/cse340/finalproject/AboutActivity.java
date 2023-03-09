package cse340.finalproject;

import android.os.Bundle;

public class AboutActivity extends DrawerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about);
        super.onCreate(savedInstanceState, R.id.activity_about);
    }
}
