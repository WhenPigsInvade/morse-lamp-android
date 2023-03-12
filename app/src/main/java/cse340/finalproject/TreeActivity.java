package cse340.finalproject;

import android.os.Bundle;

public class TreeActivity extends DrawerActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tree);
        super.onCreate(savedInstanceState, R.id.activity_tree);


    }

    // Set the tree scroll view to the middle of the picture
    @Override
    protected void onResume(){
        super.onResume();
    }



}
