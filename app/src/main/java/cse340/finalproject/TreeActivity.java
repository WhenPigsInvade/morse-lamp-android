package cse340.finalproject;

import android.os.Bundle;
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

import androidx.appcompat.app.ActionBarDrawerToggle;

public class TreeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout mLayout;
    ActionBarDrawerToggle mToggle;
    NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree);

        // Inflate and display our navigation drawer
        // Source: https://www.geeksforgeeks.org/navigation-drawer-in-android/
        mLayout = findViewById(R.id.tree);
        mToggle = new ActionBarDrawerToggle(
                this, mLayout, R.string.nav_open, R.string.nav_close);

        mNavigationView = findViewById(R.id.navigation_tree);
        mNavigationView.setNavigationItemSelectedListener(this);

        mLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // TODO: Set onClickListener() for the FLASH! button
    }

    // TODO: Change activity intent when navigation drawer buttons are clicked
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        Intent intent;
        switch (item.getItemId()) {
            case R.id.home:
                intent = new Intent(TreeActivity.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.about_me:
                intent = new Intent(TreeActivity.this, AboutActivity.class);
                startActivity(intent);
                break;

            default:
                Log.i("TAG", "Unknown menu item");
                break;

        }
        mLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(mToggle.onOptionsItemSelected(item)) return true;

        return super.onOptionsItemSelected(item);
    }
}
