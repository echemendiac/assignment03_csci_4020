package com.example.chris.assignment03_csci_4020;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This function shows the menu layer
     *
     * @param v passes in the button view
     */
    public void showMenu(View v){
        View view1 = findViewById(R.id.layout_1);
        View view2 = findViewById(R.id.layout_2);

        view1.setVisibility(View.VISIBLE);
        view2.setVisibility(View.INVISIBLE);
    }

    /**
     * This an onclick lister for select image button
     * This function will get an image and store it into
     * the phone's photo gallery
     *
     * @param v passes in the button view
     */
    public void getImage (View v){

    }


    /**
     * This function hides the menu layer
     *
     * @param v passes in the button view
     */
    public void hideMenu(View v){
        View view = findViewById(R.id.layout_1);
        view.setVisibility(View.INVISIBLE);
    }
}
