package com.example.macpac.tangibledata;

import android.graphics.Point;
import android.icu.text.StringPrepParseException;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by MacPac on 23/02/2018.
 */

public class SoundGraphGenerator
{
    private final float OVERALL_DURATION = 5;
    private final int minFreq = 256, maxFreq = 2048;
    private ArrayList<Integer> frequenciesToPlay;
    private mToneGenerator toneGenerator;
    private int counter;

    public SoundGraphGenerator(ArrayList<Point> points)
    {
        toneGenerator = new mToneGenerator();
        frequenciesToPlay = new ArrayList<>();
        transformYValuesIntoFreq(points);
        counter = 0;
    }

    public void run()
    {
        float playingTimeSingleTone = OVERALL_DURATION / frequenciesToPlay.size();

      for (int i = 0; i < frequenciesToPlay.size(); i++)
        {
            //try
            {
                toneGenerator.generateAndPlayTone(playingTimeSingleTone, frequenciesToPlay.get(counter));
                Log.d("ddd", frequenciesToPlay.get(counter)+"\t- frequency\n"+i+"\t- num of point");
                counter++;
                if (counter == frequenciesToPlay.size() - 1) counter = 0;
                   // wait((int)(playingTimeSingleTone * 2000));
            }/* catch (InterruptedException e)
            {
                e.printStackTrace();
            }//*/
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
                    minY= points.get(i).y;

                if (maxY < points.get(i).y)
                    maxY = points.get(i).y;
            }

            float muliplier = (float) (maxFreq - minFreq) / (float) (maxY - minY);

            for (Point p:points)
                frequenciesToPlay.add(maxFreq - (int) ((float) p.y * muliplier));
        }87
    }
}
