package com.example.macpac.tangibledata;

import android.util.Log;
import android.graphics.Point;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cabeywickra on 31/01/2018.
 */

public class GraphConverter
{
    public static CSVFile csv;
    public static List<String[]> csvPoints;
    public static File path;
    private static int graphType;

    public static void setCSVfile(File filePath) throws IOException
    {
        csv = new CSVFile(filePath.getAbsolutePath());
        csvPoints = csv.read();
        path = filePath;

    }

    public static ArrayList<Point> convertPoints()
    {
        //TODO Convert the points in to x and y coordinates to be used by the graph

        //Works if graph is 2 Dimenional by adding x and y coordinates to point array
        ArrayList<Point> graph = new ArrayList<Point>();
        int x, y;
        for (String[] points : csvPoints)
        {
            if (points[0].matches("-?\\d+") && points[1].matches("-?\\d+"))
            {
                x = Integer.parseInt(points[0]);
                y = Integer.parseInt(points[1]);
                graph.add(new Point(x, y));
            }
        }

        return graph;
    }

    public static void displayPoints()
    {
        //Prints all the data read by the csv file
        Log.d("CSV File Data", "Reading File " + csv.filePath);
        for (String[] points : csvPoints)
        {
            //Prints X coordinates
            for (String point : points)
            {
                Log.d("CSV File Data", point);
            }

        }
    }

    public static void setGraphType(int type)
    {
        graphType = type;
    }

    public static int getGraphType()
    {
        return graphType;
    }

}