package com.example.macpac.tangibledata;

import android.app.Activity;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import java.util.Collections;
import java.util.HashMap;

import static android.content.Context.VIBRATOR_SERVICE;
import static java.lang.StrictMath.floor;

/**
 * Created by MacPac on 15/01/2018.
 */

public class ResponseHandler
{
    private Activity parentActivity;
    private final int THRESHOLD;
    private float LARGEST_VAL;
    private long startTime;
    private int singlePulseTime = 100, pulseStength = -1;
    private ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
    private HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

    public ResponseHandler(Activity parentActivity)
    {
        this.parentActivity = parentActivity;
        LARGEST_VAL = findTheLargestYVal();

        switch (Graph.getType())
        {
            case Graph.LINEAR_MODE:
                THRESHOLD = Graph.HEIGHT / 10;
                addAllLinearPointsToMap();
                break;

            case Graph.BAR_CHART_MODE:
                THRESHOLD = Graph.WIDTH / 10;
                break;

            default:
                THRESHOLD = 0;
                break;
        }
    }

    private float findTheLargestYVal()
    {
        float max = 0;
        if (Graph.getPoints().size() > 0)
        {
            max = Graph.getPoints().get(0).y;
            for (int i = 0; i < Graph.getPoints().size(); i++)
            {
                if (Graph.getPoints().get(i).y > max)
                    max = Graph.getPoints().get(i).y;
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
        }
    }

    private void hundleTouchNavigation(int x, int y)
    {
        if (map.containsKey(x))
        {
            int distanceToTheGraph = y - map.get(x);

            if (Math.abs(distanceToTheGraph) < THRESHOLD)
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
            if (x > singleWidth * (i) + Graph.X_OFFSET * 1.1 && x < (singleWidth * (0.7 + i) + Graph.X_OFFSET * 1.1) && y > Graph.HEIGHT - Graph.points.get(i).y - Graph.Y_OFFSET * 2.05 && y < (Graph.HEIGHT - Graph.Y_OFFSET * 2.05))
            {
                singlePulseTime = 500;
                pulseStength = getPulseStrenght(Graph.HEIGHT - Graph.points.get(i).y);
//                Log.d("ddd", pulseStength + "- is pulseStength");

                toneGen1.startTone(pulseStength, singlePulseTime);
                shakeItBaby();
            }
    }

    private int getPulseStrenght(int y)
    {
        int temp = 280 - (int) ((y / (LARGEST_VAL)) * 255);

        if (temp < 5) return 5;

        if (temp > 250) return 250;

        return temp;
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

    private void calculateSinglePulse(int distance)
    {
        if (distance < 0)
        {
            singlePulseTime = 200;
            pulseStength = 30;
        } else
        {
            singlePulseTime = 600;
            pulseStength = 250;
        }

//        Log.d("debuggg","time = " + singlePulseTime);
//        Log.d("debuggg","strength = " + pulseStength);
    }

    private void addAllLinearPointsToMap()
    {
        if (Graph.getPoints().size() > 0)
        {
            for (int i = 1; i < Graph.getPoints().size(); i++)
            {
                addValuesToMap(Graph.getPoints().get(i - 1).x, Graph.getPoints().get(i - 1).y, Graph.getPoints().get(i).x, Graph.getPoints().get(i).y);
            }
        }
    }

    private void addAllBarChartPointsToMap()
    {
        int singleWidth = (int) ((Graph.WIDTH * 0.8f) / Graph.getPoints().size());
        if (Graph.getPoints().size() > 0)
        {
            for (int i = 0; i < Graph.getPoints().size(); i++)
            {
                addValuesToMap((float) (singleWidth * (0.8 + i)) - Graph.WIDTH * 0.1f, Graph.getPoints().get(i).y, (float) (singleWidth * (1.4 + i)) - Graph.WIDTH * 0.1f, Graph.getPoints().get(i).y);
            }
        }
    }

    private void addValuesToMap(float stX, float stY, float endX, float endY)
    {
        float y_add = (endY - stY) / (endX - stX);
        for (int i = (int) stX; i < (int) endX; i++)
        {
            map.put(i, (int) stY);
            stY += y_add;
        }//*/
    }
}