package com.example.macpac.tangibledata;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;

/**
 * \class GraphView
 * \brief Class to draw and display the graph and handle touch events.
 */
public class GraphView extends Activity
{
    private DrawView drawView; /**< Used to draw the graph and axis.*/
    private ResponseHandler responseHandler; /**< Provides audio/haptic feedback based on user input*/
    private GestureDetector gestureDetector; /**< Detects swipe to go back*/

    /**
     * Method is called when the activity is created to draw the graph.
     * It sets the view and sets up the gesture detector and response handler to listen for user input
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Speech.instance.talk("Reading file: " + GraphConverter.path.getName() + ". Screen orientation changed to landscape");
        gestureDetector = (new GestureDetector(this, new GestureListener(this)));

        drawView = new DrawView(this);
        drawView.setBackgroundColor(Color.rgb(7,7,7));
        setContentView(drawView);

        responseHandler = new ResponseHandler(this);
    }

    /**
     * Method listens for and detects a swipe gesture
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        responseHandler.handleTouch((int) event.getX(), (int) event.getY());
        return gestureDetector.onTouchEvent(event);
    }
}



