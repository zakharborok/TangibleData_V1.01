package com.example.macpac.tangibledata;

import android.content.Context;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * \class GestureListener
 * \brief Class to listen for and identify the swipe motion from right to left to go back.
 */
public class GestureListener extends GestureDetector.SimpleOnGestureListener {
    private static final int SWIPE_MIN_DISTANCE = 120; /**< Minimum swipe distance*/
    private static final int SWIPE_MAX_OFF_PATH = 250; /**< Maximum swipe distance*/
    private static final int SWIPE_THRESHOLD_VELOCITY = 200; /**< Velocity of swipe */


    private Context context;

    public GestureListener(Context context) {
        this.context = context;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    /**
     *Method listens for and identifies swipe from left to right and launches the start activity
     * @return Boolean value True: Swipe detected, False: No swipe detected
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
            Toast.makeText(context, "GO Back", Toast.LENGTH_SHORT).show();
            startActivity();
            return true;
        }
        return false;
    }

    /**
     * Method to launch the menu activity if a swipe motion is detected by the listener
     */
    public void startActivity() {
        context.startActivity(new Intent(context, Menu.class));
    }
}
