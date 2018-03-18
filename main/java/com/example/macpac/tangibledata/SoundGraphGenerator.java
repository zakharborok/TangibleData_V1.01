package com.example.macpac.tangibledata;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;

/**
 * \class SoundGraphGenerator
 * \brief Class to create sound version of the graph.
 * SoundGraphGenerator creates MToneGenerator object for each point from the graphs and assembles them together as a single continues sound.
 */
public class SoundGraphGenerator
{
    private final float LARGEST_TIME_TO_PLAY = 3000; /**< Longest time of a sound for any point. */
    private final int minFreq = 256/**< Minimum frequency of a sound to be created. */, maxFreq = 2048/**< Maximum frequency of a sound to be created. */;
    private ArrayList<Integer> frequenciesToPlay; /**< List of frequencies created. */
    private float timeToPlay[]; /**< Durations of sounds. */

    /**
     * \brief Constructor.
     * Method to create instance of SoundGraphGenerator.
     * @param points List of points, based on which sound representation will be created.
     */
    public SoundGraphGenerator(ArrayList<Point> points)
    {
        frequenciesToPlay = new ArrayList<>();
        timeToPlay = new float[points.size()];
        transformYValuesIntoFreq(points);
    }

    /**
     * Creates objects which contain sound representations of each point and plays them one after another.
     */
    public void run()
    {
        ArrayList<Thread> soundsToPlay = new ArrayList<>();
        ArrayList<MToneGenerator> toneGenerators = new ArrayList<>();
        for (int i =0; i < frequenciesToPlay.size(); i++ )
        {
            toneGenerators.add(new MToneGenerator());
            soundsToPlay.add(toneGenerators.get(i).generateAndPlayTone(frequenciesToPlay.get(i), timeToPlay[i]));
            soundsToPlay.get(i).start();

            try
            {
                soundsToPlay.get(i).join();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }

        }
    }

    /**
     * Converts y values of points into frequencies.
     * @param points Points to convert.
     */
    private void transformYValuesIntoFreq(ArrayList<Point> points)
    {
        if (points.size() > 0)
        {
            int maxY = points.get(0).y;
            int minY = points.get(0).y;

            float x_gap_largest = 0;

            for (int i = 1; i < points.size(); i++)
            {
                if (minY > points.get(i).y)
                    minY = points.get(i).y;

                if (maxY < points.get(i).y)
                    maxY = points.get(i).y;

                if (points.get(i).x - points.get(i-1).x > x_gap_largest)
                    x_gap_largest = points.get(i).x - points.get(i-1).x;
            }

            float muliplier = (float) (maxFreq - minFreq) / (float) (maxY - minY);

            for (Point p : points)
                frequenciesToPlay.add(maxFreq - (int) ((float) p.y * muliplier));

            timeToPlay[0] = LARGEST_TIME_TO_PLAY / 2;
            Log.d("ddd", "time to play '" + 0 + "' := " + timeToPlay[0]);
            for (int i = 1; i < points.size(); i++)
            {
                float temp = ((float) (points.get(i).x - points.get(i - 1).x) / x_gap_largest) * LARGEST_TIME_TO_PLAY;
                if (temp < 500) temp = 500;
                timeToPlay[i] = temp;
                Log.d("ddd", "time to play '" + i + "' := " + timeToPlay[i]);
            }
        }
    }
}
