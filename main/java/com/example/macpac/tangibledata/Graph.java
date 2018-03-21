package com.example.macpac.tangibledata;

import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.StrictMath.floor;

/**
 * \class Graph
 * \brief Class to store data about currently open graph & manipulate this data.
 */
public class Graph
{
    public static Graph instance; /**< Global reference to a single instance of the class. */
    public static final int LINEAR_MODE = 0/**< Constant defining graph type.*/;
    public static final int BAR_CHART_MODE = 1/**< Constant defining graph type.*/;
    public final int WIDTH/**< Constant which stores pixel width of a screen.*/;
    public final int HEIGHT/**< Constant which stores pixel height of a screen.*/;
    public final int X_OFFSET/**< Constant which stores horizontal unit of length. Used for drawing scale e.t.c.*/;
    public final int Y_OFFSET/**< Constant which stores vertical unit of length. Used for drawing scale e.t.c.*/;
    private int type /**< Stores graph type.*/;
    private ArrayList<Point> points /**< Stores data set.(in pixels)*/;
    private ArrayList<Point> originalPoints/**< Stores data set.(Actual values from the csv file)*/;
    private ArrayList<String> barChartNames/**< Stores names of bars in Bar charts*/;

    /**
     * \brief Custom Constructor.
     * Method to create instance of Graph with custom metrics.
     * @param type Graph type.
     * @param points data set.
     * @param metrics Screen pixel size.
     */
    public Graph(int type, ArrayList<Point> points, DisplayMetrics metrics)
    {
        this.type = type;
        this.points = points;
        WIDTH = metrics.widthPixels;
        HEIGHT = metrics.heightPixels;
        X_OFFSET = WIDTH / 20;
        Y_OFFSET = HEIGHT / 20;
    }

    /**
     * \brief Default Constructor.
     * Method to create instance of Graph.
     * @param type Graph type.
     * @param points data set.
     */
    public Graph(int type, ArrayList<Point> points)
    {
        this(type, points, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * Method to get random data set for Linear Graphs. Used for testing.
     * @return random data set.
     */
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

    /**
     * Method to get random data set for Bar Graphs. Used for testing.
     * @return random data set.
     */
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

    /**
     * Method to resize data set (Linear Graphs) to match the screen.
     * @param pointsToResize Data set.
     * @return Resized data set.
     */
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

    /**
     * Method to resize data set (Bar charts) to match the screen.
     * @param pointsToResize Data set.
     * @return Resized data set.
     */
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

    /**
     * Getter method for graph type.
     * @return return is graph type.
     */
    public int getType()
    {
        return type;
    }

    /**
     * Setter method for graph type.
     * @param type graph type.
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * Getter method for data set (resized for screen size).
     * @return data set (resized for screen size).
     */
    public ArrayList<Point> getPoints()
    {
        return points;
    }

    /**
     * Getter method for data set (original).
     * @return data set (original).
     */
    public ArrayList<Point> getOriginalPoints()
    {
        return originalPoints;
    }

    /**
     * Setter method for data set (resized).
     * @param points data set.
     */
    public void setPoints(ArrayList<Point> points)
    {
        this.points = points;
    }

    /**
     * Setter method for data set (resized) of Global instance of the class.
     * @param points data set.
     */
    public void setInstancePoints(ArrayList<Point> points)
    {
        Graph.instance.points = points;
    }

    /**
     * Setter method for data set (original).
     * @param originalPoints data set.
     */
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

    /**
     * Setter method for names of bar in bar charts.
     * @param barChartNames bar chart names.
     */
    public void setBarChartNames(ArrayList<String> barChartNames)
    {
        this.barChartNames = barChartNames;
    }

    /**
     * Getter method for names of bar in bar charts.
     * @return names of bar in bar charts.
     */
    public ArrayList<String> getBarChartNames()
    {
        return barChartNames;
    }
}




