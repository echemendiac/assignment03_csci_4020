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
     */
    public void showMenu(View v){
        View view = findViewById(R.id.layout_1);
        view.setVisibility(View.VISIBLE);
    }

    /**
     * This function hides the menu layer
     */
    public void hideMenu(View v){
        View view = findViewById(R.id.layout_1);
        view.setVisibility(View.INVISIBLE);
    }
}
