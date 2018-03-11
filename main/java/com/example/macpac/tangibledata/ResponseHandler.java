package com.example.macpac.tangibledata;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import java.util.HashMap;

import static android.content.Context.VIBRATOR_SERVICE;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by MacPac on 15/01/2018.
 */

public class ResponseHandler
{
    public static final int SINGLE_PULSE_TIME_ABOVE = 600;
    public static final int SINGLE_PULSE_TIME_BELOW = 200;
    public static final int PULSE_STRENGTH_ABOVE = 250;
    public static final int PULSE_STRENGTH_BELOW = 30;

    private Activity parentActivity;
    private HashMap<Integer, Integer> map;
    private float LARGEST_Y_VAL;
    private long timeKeeper, timeBetweenTouch, timeBetweenVibrations;
    private int singlePulseTime, pulseStength, touchCounter;
    private ToneGenerator toneGen1;
    private mToneGenerator toneGenerator = new mToneGenerator();

    public ResponseHandler(Activity parentActivity, ArrayList<Point> points, int graphType)
    {
        this.parentActivity = parentActivity;
        LARGEST_Y_VAL = findTheLargestYVal(points);
        map = new HashMap<>();
        toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        singlePulseTime = 100;
        pulseStength = -1;
        touchCounter = 0;
        timeKeeper = 0;
        timeBetweenTouch = 0;
        timeBetweenVibrations = 0;
        TouchFunctionController.instanse = new TouchFunctionController(this);

        switch (graphType)
        {
            case Graph.LINEAR_MODE:
                addAllLinearPointsToMap(points);
                break;

            case Graph.BAR_CHART_MODE:
                break;
        }
    }

    public ResponseHandler(Activity parentActivity)
    {
        this(parentActivity, Graph.instance.getPoints(), Graph.instance.getType());
    }

    public float findTheLargestYVal(ArrayList<Point> listOfPoints)
    {
        float max = 0;
        if (listOfPoints.size() > 0)
        {
            max = listOfPoints.get(0).y;
            for (int i = 0; i < listOfPoints.size(); i++)
            {
                if (listOfPoints.get(i).y > max)
                    max = listOfPoints.get(i).y;
            }
        }
        return max;
    }

    public void handleTouch(int x, int y)
    {
        if (System.currentTimeMillis() - timeKeeper > 200)
        {
            timeKeeper = System.currentTimeMillis();
            updateTouchCounter(System.currentTimeMillis() - timeBetweenTouch);

            switch (Graph.instance.getType())
            {
                case Graph.LINEAR_MODE:
                    if (touchCounter == 2)
                    {
                        if (!TouchFunctionController.instanse.isAlive())
                        {
                            TouchFunctionController.instanse = new TouchFunctionController(this);
                            TouchFunctionController.instanse.start();
                        }
                    }
                    else
                        hundleTouchNavigation(x, y);
                    break;

                case Graph.BAR_CHART_MODE:
                    hundleTouchRepresentation(x, y);
                    break;
            }
        }
    }

    private void hundleTouchNavigation(int x, int y)
    {
        if (map.containsKey(x))
        {
            int distanceToTheGraph = y - map.get(x);

            if (Math.abs(distanceToTheGraph) < Resources.getSystem().getDisplayMetrics().widthPixels / 10)
            {
                if (System.currentTimeMillis() - timeBetweenVibrations > singlePulseTime * 2)
                {
                    timeBetweenVibrations = System.currentTimeMillis();
                    calculateSinglePulse(distanceToTheGraph);
                    toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, singlePulseTime);
                    shakeItBaby();
                }
            } else
            {
                if (distanceToTheGraph > Resources.getSystem().getDisplayMetrics().widthPixels / 10)
                    Speech.instance.talk("Graph is above");
                else if (distanceToTheGraph < -Resources.getSystem().getDisplayMetrics().widthPixels / 10)
                    Speech.instance.talk("Graph is below");
            }
        } else
        {
            if (x < Graph.instance.X_OFFSET)
                Speech.instance.talk("Start of the Graph");
            else if (x > Graph.instance.X_OFFSET * 18)
                Speech.instance.talk("End of the Graph");
        }
    }

    private void hundleTouchRepresentation(int x, int y)
    {
        int singleWidth = (int) ((float) (Graph.instance.X_OFFSET * 17) / (float) Graph.instance.getPoints().size());
        for (int i = 0; i < Graph.instance.getPoints().size(); i++)
            if (x > singleWidth * (i) + Graph.instance.X_OFFSET * 1.1 && x < (singleWidth * (0.7 + i) + Graph.instance.X_OFFSET * 1.1) && y > Graph.instance.HEIGHT - Graph.instance.getPoints().get(i).y - Graph.instance.Y_OFFSET * 2.05 && y < (Graph.instance.HEIGHT - Graph.instance.Y_OFFSET * 2.05))
            {
                pulseStength = calculatePulseStrenght(Graph.instance.HEIGHT - Graph.instance.getPoints().get(i).y);
                toneGenerator.generateAndPlayTone((int) (((double) (255 - pulseStength) / (255.0)) * (2048.0-256.0)) + 256, 1000).start();
                shakeItBaby();
            }
    }

    public int calculatePulseStrenght(int y)
    {
        int temp = (int) ((y / (LARGEST_Y_VAL)) * 255);

        if (temp < 5) return 5;

        if (temp > 250) return 250;

        return temp;
    }

    public void calculateSinglePulse(int distance)
    {
        if (distance < 0)
        {
            singlePulseTime = SINGLE_PULSE_TIME_BELOW;
            pulseStength = PULSE_STRENGTH_BELOW;
        } else
        {
            singlePulseTime = SINGLE_PULSE_TIME_ABOVE;
            pulseStength = PULSE_STRENGTH_ABOVE;
        }
    }

    private void addAllLinearPointsToMap(ArrayList<Point> listOfPoints)
    {
        if (listOfPoints.size() > 0)
        {
            for (int i = 1; i < listOfPoints.size(); i++)
            {
                addValuesToMap(listOfPoints.get(i - 1).x, listOfPoints.get(i - 1).y, listOfPoints.get(i).x, listOfPoints.get(i).y);
            }
        }
    }

    public void addValuesToMap(float stX, float stY, float endX, float endY)
    {
        float y_add = (endY - stY) / (endX - stX);
        for (int i = (int) stX; i <= (int) endX; i++)
        {
            map.put(i, (int) stY);
            stY += y_add;
        }//*/
    }

    private void shakeItBaby()
    {
        if (Build.VERSION.SDK_INT >= 26)
        {
            ((Vibrator) parentActivity.getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(singlePulseTime, pulseStength));
        } else
        {
            ((Vibrator) parentActivity.getSystemService(VIBRATOR_SERVICE)).vibrate(singlePulseTime);
        }
    }

    private void updateTouchCounter(long diff)
    {
        if (400 < diff && diff < 1000)
        {
            touchCounter++;
        } else
        {
            touchCounter = 0;
        }

        Log.d("ddd", "touch count is " + touchCounter);

        timeBetweenTouch = System.currentTimeMillis();
    }

    public void setLARGEST_Y_VAL(float LARGEST_Y_VAL)
    {
        this.LARGEST_Y_VAL = LARGEST_Y_VAL;
    }

    public float getLARGEST_Y_VAL()
    {
        return LARGEST_Y_VAL;
    }

    public void setMap(HashMap<Integer, Integer> map)
    {
        this.map = map;
    }

    public int getSinglePulseTime()
    {
        return singlePulseTime;
    }

    public int getPulseStength()
    {
        return pulseStength;
    }

    public HashMap<Integer, Integer> getMap()
    {
        return map;
    }

    public int getTouchCounter()
    {
        return touchCounter;
    }

    public Context getContext(){
        return parentActivity.getApplicationContext();
    }
}