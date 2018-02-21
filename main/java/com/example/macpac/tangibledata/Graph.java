package com.example.macpac.tangibledata;

import android.content.res.Resources;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.StrictMath.floor;

/**
 * Created by MacPac on 15/01/2018.
 */

public class Graph//:TODO get rid of static shit
{
    public static final int LINEAR_MODE = 0,
            BAR_CHART_MODE = 1,
            WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels,
            HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels,
            X_OFFSET = WIDTH / 20,
            Y_OFFSET = Graph.HEIGHT / 20;

    public static Graph instance;

    private int type;
    private ArrayList<Point> points;

    public Graph(int type, ArrayList<Point> points)
    {
        this.type = type;
        this.points = points;
    }

    public ArrayList<Point> getExampleLinerDataPoints()
    {
        ArrayList<Point> examplePoints = new ArrayList<>();

        for (int i = 0; i < 14; i++)
        {
            int tempY = (int) (Math.random() * 8192.0);
            examplePoints.add(new Point((int) (i), tempY));
            while ((Math.random() * 8192.0) > 2500)
            {
                i++;
                int tempY2 = tempY + ((int) (Math.random() * 1500.0) - 700);
                if (tempY2 < 0) tempY2 = 0;
                examplePoints.add(new Point((i), tempY2));
            }
        }

        return resizeLinearPoints(examplePoints);
    }

    public ArrayList<Point> getExampleBarChartDataPoints()
    {
        ArrayList<Point> examplePoints = new ArrayList<>();

        examplePoints.add(new Point(0, 10));
        examplePoints.add(new Point(0, 20));
        examplePoints.add(new Point(0, 30));
        examplePoints.add(new Point(0, 40));
        examplePoints.add(new Point(0, 50));
        examplePoints.add(new Point(0, 60));
        examplePoints.add(new Point(0, 70));
        examplePoints.add(new Point(0, 70));
        examplePoints.add(new Point(0, 70));
        examplePoints.add(new Point(0, 70));
        examplePoints.add(new Point(0, 50));
        examplePoints.add(new Point(0, 90));


        return resizeBarChartPoints(examplePoints);
    }

    public ArrayList<Point> resizeLinearPoints(ArrayList<Point> pointsToResize)
    {
        if (pointsToResize.size() > 0)
        {
            //find min maxes to know the boundaries
            int minX = pointsToResize.get(0).x, minY = pointsToResize.get(0).y;
            int maxX = pointsToResize.get(0).x, maxY = pointsToResize.get(0).y;

            for (int i = 0; i < pointsToResize.size(); i++)
            {
                if (minY > pointsToResize.get(i).y)
                    minY = pointsToResize.get(i).y;

                if (minX > pointsToResize.get(i).x)
                    minX = pointsToResize.get(i).x;

                if (maxX < pointsToResize.get(i).x)
                    maxX = pointsToResize.get(i).x;

                if (maxY < pointsToResize.get(i).y)
                    maxY = pointsToResize.get(i).y;
            }

            float yDiff = (float) (maxY - minY);
            float xDiff = (float) (maxX - minX);

            float yMultiplier = (float)(Y_OFFSET * 16) / yDiff;
            float xMultiplier = (float)(X_OFFSET * 16) / xDiff;

            if (minX <= 0)
                for (Point p:pointsToResize)
                    p.x = (int) ((float)(p.x + minX) * xMultiplier) + X_OFFSET + X_OFFSET / 2;

            if (minX > 0)
                for (Point p:pointsToResize)
                    p.x = (int) ((float)(p.x - minX) * xMultiplier) + X_OFFSET + X_OFFSET / 2;

            if (minY <= 0)
                for (Point p:pointsToResize)
                    p.y = HEIGHT - ((int) ((float)(p.y + minY) * yMultiplier) + Y_OFFSET * 2 + Y_OFFSET / 2);

            if (minY > 0)
                for (Point p:pointsToResize)
                    p.y = HEIGHT - ((int) ((float)(p.y - minY) * yMultiplier) + Y_OFFSET * 2 + Y_OFFSET / 2);


        }//

        return pointsToResize;//*/
    }

    public ArrayList<Point> resizeBarChartPoints(ArrayList<Point> pointsToResize)
    {
        if (pointsToResize.size() > 0)
        {
            //find min maxes to know the boundaries
            int  maxY = pointsToResize.get(0).y, minY = pointsToResize.get(0).y;

            for (int i = 0; i < pointsToResize.size(); i++)
            {
                ///*
                if (minY > pointsToResize.get(i).y)
                    minY = pointsToResize.get(i).y;

                if (maxY < pointsToResize.get(i).y)
                    maxY = pointsToResize.get(i).y;
                //*/
            }

            float yDiff = (float) (maxY - minY);

            float yMultiplier = (float) (Y_OFFSET * 16) / yDiff;

            for (Point p:pointsToResize)
                p.y = (int) ((float) (p.y) * yMultiplier);

        }//

        return pointsToResize;//*/
    }

    public int getType()
    {
        return type;
    }

    public ArrayList<Point> getPoints()
    {
        return points;
    }

    public void setPoints(ArrayList<Point> points)
    {
        Graph.instance.points = points;
    }
}




