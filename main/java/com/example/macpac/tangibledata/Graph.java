package com.example.macpac.tangibledata;

import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.StrictMath.floor;

/**
 * Created by MacPac on 15/01/2018.
 */

public class Graph
{
    public static Graph instance;
    public static final int LINEAR_MODE = 0, BAR_CHART_MODE = 1;
    public final int WIDTH, HEIGHT, X_OFFSET, Y_OFFSET;

    private int type;
    private ArrayList<Point> points;
    private ArrayList<Point> originalPoints;
    private ArrayList<String> barChartNames;

    public Graph(int type, ArrayList<Point> points, DisplayMetrics metrics)
    {
        this.type = type;
        this.points = points;
        WIDTH = metrics.widthPixels;
        HEIGHT = metrics.heightPixels;
        X_OFFSET = WIDTH / 20;
        Y_OFFSET = HEIGHT / 20;
    }

    public Graph(int type, ArrayList<Point> points)
    {
        this(type, points, Resources.getSystem().getDisplayMetrics());
    }

    public ArrayList<Point> getExampleLinerDataPoints()
    {
        ArrayList<Point> examplePoints = new ArrayList<>();

        for (int i = 0; i < 10; i++)
        {
            int tempY = (int) (Math.random() * 8192.0);
            examplePoints.add(new Point((int) (i*i*i), tempY));
            while ((Math.random() * 8192.0) > 2500)
            {
                i++;
                int tempY2 = tempY + ((int) (Math.random() * 1500.0) - 700);
                if (tempY2 < 0) tempY2 = 0;
                examplePoints.add(new Point((i*i*i), tempY2));
            }
        }

        return resizeLinearPoints(examplePoints);
    }

    public ArrayList<Point> getExampleBarChartDataPoints()
    {
        ArrayList<Point> examplePoints = new ArrayList<>();

        for (int i = 0; i < 6; i++)
        {
            int tempY = (int) (Math.random() * 8192.0);
            examplePoints.add(new Point((int) (i), tempY));
            while ((Math.random() * 8192.0) > 4000)
            {
                i++;
                int tempY2 = tempY + ((int) (Math.random() * 1500.0) - 700);
                if (tempY2 < 0) tempY2 = 0;
                examplePoints.add(new Point((i), tempY2));
            }
        }

//        examplePoints.add(new Point(0, 10));
//        examplePoints.add(new Point(0, 20));
//        examplePoints.add(new Point(0, 30));
//        examplePoints.add(new Point(0, 40));
//        examplePoints.add(new Point(0, 50));
//        examplePoints.add(new Point(0, 60));
//        examplePoints.add(new Point(0, 70));
//        examplePoints.add(new Point(0, 70));
//        examplePoints.add(new Point(0, 70));
//        examplePoints.add(new Point(0, 70));
//        examplePoints.add(new Point(0, 50));
//        examplePoints.add(new Point(0, 90));


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

            float yMultiplier = (float) (Y_OFFSET * 16) / yDiff;
            float xMultiplier = (float) (X_OFFSET * 16) / xDiff;

            if (minX <= 0)
                for (Point p : pointsToResize)
                    p.x = (int) ((float) (p.x + minX) * xMultiplier) + X_OFFSET + X_OFFSET / 2;

            if (minX > 0)
                for (Point p : pointsToResize)
                    p.x = (int) ((float) (p.x - minX) * xMultiplier) + X_OFFSET + X_OFFSET / 2;

            if (minY <= 0)
                for (Point p : pointsToResize)
                    p.y = HEIGHT - ((int) ((float) (p.y + minY) * yMultiplier) + Y_OFFSET * 2 + Y_OFFSET / 2);

            if (minY > 0)
                for (Point p : pointsToResize)
                    p.y = HEIGHT - ((int) ((float) (p.y - minY) * yMultiplier) + Y_OFFSET * 2 + Y_OFFSET / 2);


        }//

        return pointsToResize;//*/
    }

    public ArrayList<Point> resizeBarChartPoints(ArrayList<Point> pointsToResize)
    {
        if (pointsToResize.size() > 0)
        {
            //find min maxes to know the boundaries
            int maxY = pointsToResize.get(0).y, minY = pointsToResize.get(0).y;

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

            for (Point p : pointsToResize)
                p.y = (int) ((float) (p.y) * yMultiplier);

        }//

        return pointsToResize;//*/
    }

    public DisplayMetrics getMetrics()
    {
        return Resources.getSystem().getDisplayMetrics();
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public ArrayList<Point> getPoints()
    {
        return points;
    }

    public ArrayList<Point> getOriginalPoints()
    {
        return originalPoints;
    }

    public void setPoints(ArrayList<Point> points)
    {
        this.points = points;
    }

    public void setInstancePoints(ArrayList<Point> points)
    {
        Graph.instance.points = points;
    }

    private ArrayList<Point> copyPpints(ArrayList<Point> points)
    {
        ArrayList<Point> copy_point = new ArrayList();

        for (Point p : points)
        {
            copy_point.add(new Point(p.x, p.y));
        }

        return copy_point;
    }

    public void setOriginalPoints(ArrayList<Point> originalPoints)
    {
        int x,y;
        ArrayList<Point> copyList = new ArrayList<>();
        for (Point point: originalPoints){
            x = point.x;
            y = point.y;
            copyList.add(new Point(x,y));
        }
        this.originalPoints = copyList;
    }

    public void setBarChartNames(ArrayList<String> barChartNames)
    {
        this.barChartNames = barChartNames;
    }

    public ArrayList<String> getBarChartNames()
    {
        return barChartNames;
    }
}




