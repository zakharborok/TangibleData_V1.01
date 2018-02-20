package com.example.macpac.tangibledata;

import android.app.Activity;
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
    public static final int PULSE_STRENGHT_ABOVE = 250;
    public static final int PULSE_STRENGHT_BELOW = 30;

    private Activity parentActivity;
    private HashMap<Integer, Integer> map;
    private float LARGEST_Y_VAL;
    private long startTime;
    private int singlePulseTime = 100, pulseStength = -1;
    private ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);


    public ResponseHandler(Activity parentActivity, ArrayList<Point> points, int graphType)
    {
        this.parentActivity = parentActivity;
        LARGEST_Y_VAL = findTheLargestYVal(points);
        map = new HashMap<>();

        switch (graphType)
        {
            case Graph.LINEAR_MODE:
                addAllLinearPointsToMap(points);
                break;

            case Graph.BAR_CHART_MODE:
                break;
        }


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
        switch (Graph.type)
        {
            case Graph.LINEAR_MODE:
                hundleTouchNavigation(x, y);
                break;

            case Graph.BAR_CHART_MODE:
                hundleTouchRepresentation(x, y);
                break;
        }//*/
    }

    private void hundleTouchNavigation(int x, int y)
    {
        if (map.containsKey(x))
        {
            int distanceToTheGraph = y - map.get(x);

            if (Math.abs(distanceToTheGraph) < Resources.getSystem().getDisplayMetrics().widthPixels / 10)
            {
                calculateSinglePulse(distanceToTheGraph);

                if (System.currentTimeMillis() - startTime > singlePulseTime * 2)
                {
                    startTime = System.currentTimeMillis();
                    toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, singlePulseTime);
                    shakeItBaby();
                }
            }
        }
    }

    private void hundleTouchRepresentation(int x, int y)
    {
        int singleWidth = (int) ((Graph.X_OFFSET * 17) / Graph.getPoints().size());
        for (int i = 0; i < Graph.getPoints().size(); i++)
            if (x > singleWidth * (i) + Graph.X_OFFSET * 1.1 && x < (singleWidth * (0.7 + i) + Graph.X_OFFSET * 1.1) && y > Graph.HEIGHT - Graph.getPoints().get(i).y - Graph.Y_OFFSET * 2.05 && y < (Graph.HEIGHT - Graph.Y_OFFSET * 2.05))
            {
                singlePulseTime = 500;
                pulseStength = calculatePulseStrenght(Graph.HEIGHT - Graph.getPoints().get(i).y);
                Log.d("ddd", pulseStength + "- is pulseStength");
                toneGen1.startTone(pulseStength / 2, singlePulseTime);
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
            pulseStength = PULSE_STRENGHT_BELOW;
        } else
        {
            singlePulseTime = SINGLE_PULSE_TIME_ABOVE;
            pulseStength = PULSE_STRENGHT_ABOVE;
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
}