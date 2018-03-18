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
 * \class ResponseHandler
 * \brief Class to manage audio and haptic feedback of the app.
 * ResponseHandler is the core of the system, as all touches detected by the Android device are sent to this class. These inputs from a user are analysed in this class and appropriate feedback is provided.
 */
public class ResponseHandler
{
    public static final int SINGLE_PULSE_TIME_ABOVE = 600;/**< Time duration of a single vibration when user touch is detected close to and above the line.*/
    public static final int SINGLE_PULSE_TIME_BELOW = 200;/**< Time duration of a single vibration when user touch is detected close to and below the line.*/
    public static final int PULSE_STRENGTH_ABOVE = 250;/**< Strength of a single vibration when user touch is detected close to and above the line.*/
    public static final int PULSE_STRENGTH_BELOW = 30;/**< Strength of a single vibration when user touch is detected close to and below the line.*/

    private Activity parentActivity; /**< Stores reference of a current activity. Used to create vibrations. */
    private HashMap<Integer, Integer> map; /**< Stores x,y pixel coordinates of a line graph.*/
    private long timeKeeper/**< Stores time between user touches. Used to distinguish multiple touches and hold. Updated on any touch.*/,
                 timeBetweenTouch/**< Stores time between user touches. Used to update touch counter.(Different from timeKeeper, as updated only on accepted touches)*/,
                 timeBetweenVibrations/**< Stores time between each vibration. Used to create pulses of vibrations.*/;
    private int singlePulseTime,
                pulseStength/**< Strength of a vibration.*/,
                touchCounter/**< Stores number of touches detected in a row.*/,
                lastBarChartPressed = -1 /**< Stores index of last bar chart touched.*/;
    private ToneGenerator toneGen1/**< Builtin class to generate tone.*/;
    private MToneGenerator toneGenerator/**< Our tone generator to create arbitrary tone.*/;

    /**
     * \brief Custom Constructor.
     * Method to create instance of ResponseHandler with custom data entry.
     * @param parentActivity current activity.
     * @param points data set.
     * @param graphType type of data set.
     */
    public ResponseHandler(Activity parentActivity, ArrayList<Point> points, int graphType)
    {
        this.parentActivity = parentActivity;
        map = new HashMap<>();
        toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGenerator = new MToneGenerator();
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

    /**
     * \brief Default Constructor.
     * Method to create instance of ResponseHandler.
     * @param parentActivity current activity.
     */
    public ResponseHandler(Activity parentActivity)
    {
        this(parentActivity, Graph.instance.getPoints(), Graph.instance.getType());
    }

    /**
     * Method which depending on the type of data set calls following functions to interpret input and produce corresponding feedback.
     * @param x x-coordinate of detected touch.
     * @param y y-coordinate of detected touch.
     */
    public void handleTouch(int x, int y)
    {
        if (System.currentTimeMillis() - timeKeeper > 200)
        {
            timeKeeper = System.currentTimeMillis();
            updateTouchCounter(System.currentTimeMillis() - timeBetweenTouch);

            detectAxesStartEndOfGraph(x, y);

            if (touchCounter == 1) Speech.instance.stopTalk();

            if (touchCounter == 2)
            {
                if (!TouchFunctionController.instanse.isAlive())
                {
                    TouchFunctionController.instanse = new TouchFunctionController(this);
                    TouchFunctionController.instanse.start();
                }
            }

            switch (Graph.instance.getType())
            {
                case Graph.LINEAR_MODE:
                    hundleTouchNavigation(x, y);
                    break;

                case Graph.BAR_CHART_MODE:
                    hundleTouchRepresentation(x, y);
                    break;
            }
        }
    }

    /**
     * Method to produce verbal feedback for the user, when user touch axes and touches outside the graph space.
     * @param x x-coordinate of detected touch.
     * @param y y-coordinate of detected touch.
     */
    private void detectAxesStartEndOfGraph(int x, int y)
    {
        if (x < (double) Graph.instance.X_OFFSET * 0.9)
        {
            Speech.instance.talk("Start of the Graph");
        } else if (x >= (double) Graph.instance.X_OFFSET * 0.9 && x <= (double) Graph.instance.X_OFFSET * 1.1)
        {
            Speech.instance.talk("Y Axis, with range from " + findTheSmallesYVal(Graph.instance.getOriginalPoints()) + " to " + findTheLargestYVal(Graph.instance.getOriginalPoints()) + " .");
        } else if (y >= (double) Graph.instance.Y_OFFSET * 18 * 0.9 && y <= (double) Graph.instance.Y_OFFSET * 18 * 1.1 && Graph.instance.getType() == Graph.LINEAR_MODE)
        {
            Speech.instance.talk("X Axis, with range from " + findTheSmallesXVal(Graph.instance.getOriginalPoints()) + " to " + findTheLargestXVal(Graph.instance.getOriginalPoints()) + " .");
        } else if (x > Graph.instance.X_OFFSET * 18)
        {
            Speech.instance.talk("End of the Graph");
        }
    }

    /**
     * Method to produce feedback for Line Graphs, to help user navigate towards the line.
     * @param x x-coordinate of detected touch.
     * @param y y-coordinate of detected touch.
     */
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
                    setSinglePulse(distanceToTheGraph);
                    toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, singlePulseTime);
                    makeVibration();
                }
            } else
            {
                if (distanceToTheGraph > Resources.getSystem().getDisplayMetrics().widthPixels / 10)
                    Speech.instance.talk("Graph is above");
                else if (distanceToTheGraph < -Resources.getSystem().getDisplayMetrics().widthPixels / 10)
                    Speech.instance.talk("Graph is below");
            }
        }
    }

    /**
     * Method to produce feedback for the user which will represent Bar charts.
     * @param x x-coordinate of detected touch.
     * @param y y-coordinate of detected touch.
     */
    private void hundleTouchRepresentation(int x, int y)
    {
        int singleWidth = (int) ((float) (Graph.instance.X_OFFSET * 17) / (float) Graph.instance.getPoints().size());
        for (int i = 0; i < Graph.instance.getPoints().size(); i++)
            if (x > singleWidth * (i) + Graph.instance.X_OFFSET * 1.1 && x < (singleWidth * (0.7 + i) + Graph.instance.X_OFFSET * 1.1) && y > Graph.instance.HEIGHT - Graph.instance.getPoints().get(i).y - Graph.instance.Y_OFFSET * 2.05 && y < (Graph.instance.HEIGHT - Graph.instance.Y_OFFSET * 2.05))
            {
                pulseStength = 200;

                if (touchCounter < 1)
                {
                    lastBarChartPressed = i;
                    Speech.instance.talk(Graph.instance.getBarChartNames().get(i));
                } else if (lastBarChartPressed == i)
                {
                    toneGenerator.generateAndPlayTone((int) (((double) (255 - pulseStength) / (255.0)) * (2048.0 - 256.0)) + 256, 1000).start();
                }

                makeVibration();
            }
    }


    /**
     * Method to set Pulse time and strength depending weather touch was detected above or below the graph.
     * @param distance Distance to the graph. Matters only if a value positive or negative.
     */
    public void setSinglePulse(int distance)
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

    /**
     * Method to add pixel coordinates of a line graph into the map between points.
     * @param listOfPoints Data set.
     */
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

    /**
     * Helper method for addAllLinearPointsToMap(). Adds pixel coordinates to the map, between two points given.
     * @param stX x-coordinate of first point.
     * @param stY y-coordinate of first point.
     * @param endX x-coordinate of second point.
     * @param endY y-coordinate of second point.
     */
    public void addValuesToMap(float stX, float stY, float endX, float endY)
    {
        float y_add = (endY - stY) / (endX - stX);
        for (int i = (int) stX; i <= (int) endX; i++)
        {
            map.put(i, (int) stY);
            stY += y_add;
        }//*/
    }

    /**
     * Method to make device vibrate.
     */
    private void makeVibration()
    {
        if (Build.VERSION.SDK_INT >= 26)
        {
            ((Vibrator) parentActivity.getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(singlePulseTime, pulseStength));
        } else
        {
            ((Vibrator) parentActivity.getSystemService(VIBRATOR_SERVICE)).vibrate(singlePulseTime);
        }
    }

    /**
     * Method to update touch counter.
     * @param diff Time difference between current time and last time counter was updated.
     */
    private void updateTouchCounter(long diff)
    {
        if (300 < diff && diff < 1000)
        {
            touchCounter++;
        } else
        {
            touchCounter = 0;
        }

        Log.d("ddd", "touch count is " + touchCounter);

        timeBetweenTouch = System.currentTimeMillis();
    }

    /**
     * Getter method for singlePulseTime;
     * @return return is the global, to this class, reference of the variable singlePulseTime.
     */
    public int getSinglePulseTime()
    {
        return singlePulseTime;
    }

    /**
     * Getter method for singlePulseTime;
     * @return return is the global, to this class, reference of the variable pulseStength.
     */
    public int getPulseStength()
    {
        return pulseStength;
    }

    /**
     * Getter method for singlePulseTime;
     * @return return is the global, to this class, reference of the variable map.
     */
    public HashMap<Integer, Integer> getMap()
    {
        return map;
    }

    /**
     * Getter method for singlePulseTime;
     * @return return is the global, to this class, reference of the variable touchCounter.
     */
    public int getTouchCounter()
    {
        return touchCounter;
    }

    /**
     * Getter method of the context of current activity.
     * @return context of current activity.
     */
    public Context getContext()
    {
        return parentActivity.getApplicationContext();
    }

    /**
     * Method to find largest y-coordinate on the passed list of points.
     * @param listOfPoints Data set.
     * @return largest y-coordinate.
     */
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

    /**
     * Method to find smallest y-coordinate on the passed list of points.
     * @param listOfPoints Data set.
     * @return smallest y-coordinate.
     */
    private float findTheSmallesYVal(ArrayList<Point> listOfPoints)
    {
        float min = listOfPoints.get(0).y;
        if (listOfPoints.size() > 0)
        {
            min = listOfPoints.get(0).y;
            for (int i = 0; i < listOfPoints.size(); i++)
            {
                if (listOfPoints.get(i).y < min)
                    min = listOfPoints.get(i).y;
            }
        }
        return min;
    }

    /**
     * Method to find largest x-coordinate on the passed list of points.
     * @param listOfPoints Data set.
     * @return largest x-coordinate.
     */
    private float findTheLargestXVal(ArrayList<Point> listOfPoints)
    {
        float max = 0;
        if (listOfPoints.size() > 0)
        {
            max = listOfPoints.get(0).x;
            for (int i = 0; i < listOfPoints.size(); i++)
            {
                if (listOfPoints.get(i).x > max)
                    max = listOfPoints.get(i).x;
            }
        }
        return max;
    }

    /**
     * Method to find smallest x-coordinate on the passed list of points.
     * @param listOfPoints Data set.
     * @return smallest x-coordinate.
     */
    private float findTheSmallesXVal(ArrayList<Point> listOfPoints)
    {
        float min = listOfPoints.get(0).x;
        if (listOfPoints.size() > 0)
        {
            min = listOfPoints.get(0).x;
            for (int i = 0; i < listOfPoints.size(); i++)
            {
                if (listOfPoints.get(i).x < min)
                    min = listOfPoints.get(i).x;
            }
        }
        return min;
    }
}