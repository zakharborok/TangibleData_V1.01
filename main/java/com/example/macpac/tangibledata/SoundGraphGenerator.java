package com.example.macpac.tangibledata;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by MacPac on 23/02/2018.
 */

public class SoundGraphGenerator extends Thread
{
    private final int minFreq = 256, maxFreq = 2048;
    private ArrayList<Integer> frequenciesToPlay;
    private int counter;

    public SoundGraphGenerator(ArrayList<Point> points)
    {
        super("Sound Graph Generation Thread");
        frequenciesToPlay = new ArrayList<>();
        transformYValuesIntoFreq(points);
        counter = 0;
    }

    public void run()
    {
        ArrayList<Thread> soundsToPlay = new ArrayList<>();
        ArrayList<mToneGenerator> toneGenerators = new ArrayList<>();
        for (int i =0; i < frequenciesToPlay.size(); i++ )
        {
            toneGenerators.add(new mToneGenerator());
            soundsToPlay.add(toneGenerators.get(i).generateAndPlayTone(frequenciesToPlay.get(i)));
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

    private void transformYValuesIntoFreq(ArrayList<Point> points)
    {
        if (points.size() > 0)
        {
            int maxY = points.get(0).y;
            int minY = points.get(0).y;

            for (int i = 0; i < points.size(); i++)
            {
                if (minY > points.get(i).y)
                    minY = points.get(i).y;

                if (maxY < points.get(i).y)
                    maxY = points.get(i).y;
            }

            float muliplier = (float) (maxFreq - minFreq) / (float) (maxY - minY);

            for (Point p : points)
                frequenciesToPlay.add(maxFreq - (int) ((float) p.y * muliplier));
        }
    }
}
