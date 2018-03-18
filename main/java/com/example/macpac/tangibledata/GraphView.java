package com.example.macpac.tangibledata;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;

public class GraphView extends Activity
{
    private DrawView drawView;
    private ResponseHandler responseHandler;
    private GestureDetector gestureDetector;

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

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        responseHandler.handleTouch((int) event.getX(), (int) event.getY());
        return gestureDetector.onTouchEvent(event);
    }
}



