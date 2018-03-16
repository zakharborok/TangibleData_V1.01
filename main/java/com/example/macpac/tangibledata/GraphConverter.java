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

    public static void convertPoints()
    {
        //TODO Convert the points in to x and y coordinates to be used by the graph
        //Works if graph is 2 Dimenional by adding x and y coordinates to point array
        ArrayList<Point> graph = new ArrayList<Point>();
        ArrayList<String> names = new ArrayList<>();


        if (csvPoints.get(0).length == 2 && csvPoints.get(0)[0].replaceAll("[\uFEFF-\uFFFF]","").equals("x") && csvPoints.get(0)[1].replaceAll("\\s+","").equals("y"))
        {
            Log.d("ddd", "convertPoints: Linear ");
            for (String[] points : csvPoints)
            {
                if (points[0].matches("-?\\d+") && points[1].matches("-?\\d+"))
                    graph.add(new Point(Integer.parseInt(points[0]), Integer.parseInt(points[1])));

            }
            graphType = Graph.LINEAR_MODE;
            Graph.instance.setType(Graph.LINEAR_MODE);
            Graph.instance.setOriginalPoints(graph);
            Graph.instance.setInstancePoints(Graph.instance.resizeLinearPoints(graph));

        } else if (csvPoints.get(0).length == 2 && !/*not*/csvPoints.get(0)[0].equals("\uFEFFx") && csvPoints.get(0)[1].equals("y"))
        {
            Log.d("ddd", "convertPoints: Bar ");
            for (String[] points : csvPoints)
            {
                if (points[1].matches("-?\\d+"))
                {
                    graph.add(new Point(0, Integer.parseInt(points[1])));
                    names.add(points[0]);
                }
            }
            graphType = Graph.BAR_CHART_MODE;
            Graph.instance.setType(Graph.BAR_CHART_MODE);
            Graph.instance.setOriginalPoints(graph);
            Graph.instance.setInstancePoints(Graph.instance.resizeBarChartPoints(graph));
            Graph.instance.setBarChartNames(names);
        }
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