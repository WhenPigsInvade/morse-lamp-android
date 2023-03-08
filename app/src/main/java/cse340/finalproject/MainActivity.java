package cse340.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mLayout;
    ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inflate and display our navigation drawer
        // Source: https://www.geeksforgeeks.org/navigation-drawer-in-android/
        mLayout = findViewById(R.id.main);
        mToggle = new ActionBarDrawerToggle(
                this, mLayout, R.string.nav_open, R.string.nav_close);

        mLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // TODO: Set onClickListener() for the FLASH! button
    }

    // TODO: Translate text to morse. We should try to simplify it by translating text
    // straight into time spacing.


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(mToggle.onOptionsItemSelected(item)) return true;

        return super.onOptionsItemSelected(item);
    }
}