package com.example.macpac.tangibledata;

import android.content.res.Resources;
import android.graphics.Point;

import java.util.ArrayList;

import static java.lang.StrictMath.floor;

/**
 * Created by MacPac on 15/01/2018.
 */

public class Graph
{
    public static final int LINEAR_MODE = 0,
            BAR_CHART_MODE = 1,
            WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels,
            HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;

    public static int type;
    public static ArrayList<Point> points;
    public static int X_OFFSET = Graph.WIDTH / 20;
    public static int Y_OFFSET = Graph.HEIGHT / 20;


    public static ArrayList<Point> getExampleLinerDataPoints()
    {
        ArrayList<Point> examplePoints = new ArrayList<>();

        /*examplePoints.add(new Point(Graph.WIDTH / 5, (Graph.HEIGHT / 5) * 4));
        examplePoints.add(new Point((Graph.WIDTH / 5) * 2, Graph.HEIGHT / 5));
        examplePoints.add(new Point((Graph.WIDTH / 5) * 3, Graph.HEIGHT / 5));
        examplePoints.add(new Point((Graph.WIDTH / 5) * 4, (Graph.HEIGHT / 5) * 4));//*/

        examplePoints.add(new Point(1, 1));
        examplePoints.add(new Point(2, 2));
        examplePoints.add(new Point(2, 4));
        examplePoints.add(new Point(5, 1));


        return resizeLinearPoints(examplePoints);
    }

    public static ArrayList<Point> getExampleBarChartDataPoints()
    {
        ArrayList<Point> examplePoints = new ArrayList<>();
        /*examplePoints.add(new Point(0, (Graph.HEIGHT / 5) * 4));
        examplePoints.add(new Point(0, Graph.HEIGHT / 5));
        examplePoints.add(new Point(0, Graph.HEIGHT / 5));
        examplePoints.add(new Point(0, (Graph.HEIGHT / 5) * 4));
        examplePoints.add(new Point(0, (Graph.HEIGHT / 5) * 2));//*/


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

    public static ArrayList<Point> resizeLinearPoints(ArrayList<Point> pointsToResize)
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

            int yDiff = maxY - minY;
            int xDiff = maxX - minX;

            float yMultiplier = (Y_OFFSET * 16) / yDiff;
            float xMultiplier = (X_OFFSET * 16) / xDiff;

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

    private static ArrayList<Point> resizeBarChartPoints(ArrayList<Point> pointsToResize)
    {
        if (pointsToResize.size() > 0)
        {
            //find min maxes to know the boundaries
            int maxY = pointsToResize.get(0).y, minY = pointsToResize.get(0).y;

            for (int i = 0; i < pointsToResize.size(); i++)
            {
                if (minY > pointsToResize.get(i).y)
                    minY = pointsToResize.get(i).y;

                if (maxY < pointsToResize.get(i).y)
                    maxY = pointsToResize.get(i).y;
            }

            int yDiff = maxY - minY;

            float yMultiplier = (Y_OFFSET * 16) / yDiff;

            for (Point p : pointsToResize)
                p.y = (int) (p.y * yMultiplier);


        }//

        return pointsToResize;//*/
    }

    public static int getType()
    {
        return type;
    }

    public static ArrayList<Point> getPoints()
    {
        return points;
    }
}
