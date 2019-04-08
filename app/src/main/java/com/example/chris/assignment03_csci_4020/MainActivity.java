package com.example.chris.assignment03_csci_4020;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //---- Declaring Variables ----//
    private ColorSelect colorSelect;

    /**
     * ColorSelect is the inner enum class that holds
     * the color selection data
     *
     */
    static enum ColorSelect {
        BLACK, WHITE, RED, GREEN, BLUE, ERROR;

        public int getValue(){
            switch (this)
            {
                case BLACK: return Color.BLACK;
                case WHITE: return Color.WHITE;
                case RED: return Color.RED;
                case GREEN: return Color.GREEN;
                case BLUE: return Color.BLUE;
                case ERROR: ;
                default: return -999;
            }
        }

    }

    /**
     * Constructor
     */
    MainActivity (){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //---- Initialize Variables ----//
        colorSelect = ColorSelect.BLACK;
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
     *
     * This is the onClick listner for all the color buttons
     * This function gets the color that the user passes in
     *
     * @param v
     */
    public void selectColor (View v){
        Log.i("selectColor","Select Color was called");
        switch(v.getId()){
            case R.id.black_b:
                colorSelect = ColorSelect.BLACK;
                break;
            case R.id.white_b:
                colorSelect = ColorSelect.WHITE;
                break;
            case R.id.red_b:
                colorSelect = ColorSelect.RED;
                break;
            case R.id.green_b:
                colorSelect = ColorSelect.GREEN;
                break;
            case R.id.blue_b:
                colorSelect = ColorSelect.BLUE;
                break;
            default: colorSelect = ColorSelect.ERROR;
        }

        Log.i("selectColor",colorSelect.toString()+ " was selected");
        setColorV();
    }

    private void setColorV (){
        if(colorSelect.getValue() != -999) {
            Log.i("setColorV", "This is running");
            /*Button b = findViewById(buttonId);
            b.setWidth(R.dimen.color_button_selected);
            b.setHeight(R.dimen.color_button_selected);*/
            findViewById(R.id.color_iv).setBackgroundColor(colorSelect.getValue());
        }else {
            Log.i("setColor","Error when trying to set the color");
        }
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
