package com.example.macpac.tangibledata;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;

/**
 * Created by cabeywickra on 18/01/2018.
 */

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
        if(Graph.instance.getType() == Graph.LINEAR_MODE)
            drawView.setBackgroundColor(Color.rgb(34, 49, 63));
        else
            drawView.setBackgroundColor(Color.rgb(30, 139, 195));
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



