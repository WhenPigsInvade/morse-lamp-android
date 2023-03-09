package cse340.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public abstract class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout mLayout;
    ActionBarDrawerToggle mToggle;
    NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // TODO: Change activity intent when navigation drawer buttons are clicked
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        Intent intent;
        switch (item.getItemId()) {
            case R.id.morse_tree:
                intent = new Intent(this, TreeActivity.class);
                startActivity(intent);
                break;

            case R.id.about_me:
                intent = new Intent(this, AboutActivity.class);
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
