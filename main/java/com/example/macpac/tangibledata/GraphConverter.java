package com.example.macpac.tangibledata;

import android.util.Log;
import android.graphics.Point;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * \class GraphConverter
 * \brief Class converts a csv file to a list of points and identifies the graph type based on the file format.
 */
public class GraphConverter {
    public static CSVFile csv; /**< Stores the csv file that is to be converted */
    public static List<String[]> csvPoints; /**< List of entries from the parsed csv file*/
    public static File path; /**< File path of the csv file on the device */
    private static int graphType; /**< Int that represents the type of graph that is identified */

    /**
     * Method to create a CSV object, parse the contents of the csv file and set its file path
     * @param filePath Path to selected csv file
     */
    public static void setCSVfile(File filePath) throws IOException
    {
        csv = new CSVFile(filePath.getAbsolutePath());
        csvPoints = csv.read();
        path = filePath;

    }

    /**
     * Method to convert the parsed values of the csv file and convert them into a list of points. The graph type is also identified depending on the structure of the data.
     * Line Graphs are detected only if the file contain a column labeled x and y
     * Bar Graphs are detected if there are two columns one must be a label column and the other must be a y column
     * This function works only if the graph is 2 Dimenional by adding x and y coordinates to a point array
     */
    public static void convertPoints()
    {
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

    /**
     * Method to print out the parsed data of the csv file
     */
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

    /**
     * \brief Setter method
     * Method to set the graph type this allows alteration of the type if required
     * @param type represents the graph type that ahs been identified
     */
    public static void setGraphType(int type)
    {
        graphType = type;
    }

    /**
     * \brief Getter method
     * Method to get the current graph type of the converter
     * @return returns the graph type as an int Linear Graphs = 0 , Bar Graphs = 1
     */
    public static int getGraphType()
    {
        return graphType;
    }

}