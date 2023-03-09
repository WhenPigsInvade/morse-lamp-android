package cse340.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout mLayout;
    ActionBarDrawerToggle mToggle;
    NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inflate and display our navigation drawer
        // Source: https://www.geeksforgeeks.org/navigation-drawer-in-android/
        mLayout = findViewById(R.id.main);
        mToggle = new ActionBarDrawerToggle(
                this, mLayout, R.string.nav_open, R.string.nav_close);

        mNavigationView = findViewById(R.id.navigation_main);
        mNavigationView.setNavigationItemSelectedListener(this);

        mLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // TODO: Set onClickListener() for the FLASH! button
    }

    // TODO: Change activity intent when navigation drawer buttons are clicked
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        Log.i("TAG", item.getItemId()+"");
        switch (item.getItemId()) {
            case R.id.morse_tree:
                Intent intent = new Intent(MainActivity.this, TreeActivity.class);
                startActivity(intent);
                break;

        }
        mLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // TODO: Translate text to morse. We should try to simplify it by translating text
    // straight into time spacing.


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(mToggle.onOptionsItemSelected(item)) return true;

        return super.onOptionsItemSelected(item);
    }
}