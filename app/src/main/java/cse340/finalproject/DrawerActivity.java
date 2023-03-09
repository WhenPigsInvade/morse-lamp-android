package cse340.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


// DrawerActivity is a super class that handles all the code relating to the navigation drawer
// Children classes will need to extend it and use its special onCreate class in order to
// use its functions properly.
public abstract class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout mLayout;
    ActionBarDrawerToggle mToggle;
    NavigationView mNavigationView;

    protected void onCreate(Bundle savedInstanceState, @IdRes int viewID) {
        super.onCreate(savedInstanceState);

        // Inflate and display our navigation drawer
        // Source: https://www.geeksforgeeks.org/navigation-drawer-in-android/
        mLayout = findViewById(viewID);
        mToggle = new ActionBarDrawerToggle(
                this, mLayout, R.string.nav_open, R.string.nav_close);

        mNavigationView = findViewById(R.id.navigation);
        mNavigationView.setNavigationItemSelectedListener(this);

        mLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // TODO: Change activity intent when navigation drawer buttons are clicked
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_item_main:
                if(!(this instanceof MainActivity)) {
                    intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.menu_item_tree:
                if(!(this instanceof TreeActivity)) {
                    intent = new Intent(this, TreeActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.menu_item_about:
                if(!(this instanceof AboutActivity)) {
                    intent = new Intent(this, AboutActivity.class);
                    startActivity(intent);
                }
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
