package com.example.macpac.tangibledata;

import android.content.Context;
import android.graphics.Point;

import java.util.ArrayList;

/**
 * \class GraphAnalyser
 * \brief Class to analyse a graph and provide a description based on its type.
 */
public class GraphAnalyser {
    private String description; /**< String to represent the description of the graph */
    private ArrayList<Point> points; /**< Arraylist that stores the points to be analysed */
    private Point maxY; /**< Point object of the point that has the max Y value */
    private Point minY; /**< Point object of the point that has the min Y value */
    private int graphType; /**< int to identify the graph type */

    /**
     * \brief Constructor.
     * Method to construct a GraphAnalyser and set the points it must analyse and graph type
     */
    GraphAnalyser(ArrayList<Point> points, int graphType){
        this.points = points;
        this.graphType = graphType;
    }

    /**
     * \brief Getter method
     * Method to get the description of the graph that is produced by the analyser
     * @return returns the description of the graph
     */
    public String getDescription() {
        return description;
    }

    /**
     * \brief Getter method
     * Method to get the points of the graph that are used by the analyser
     * @return returns the points of the graph
     */
    public ArrayList<Point> getPoints() {
        return points;
    }

    /**
     * Method to analyse the points of the graph based on the graph type and create a breif description
     */
    private void analysePoints(){
        getInfo();
        int len = points.size()-1;
        if (graphType == Graph.LINEAR_MODE) {
            description += "This is a Line Graph, ";
            description += "The line graph starts at point " + points.get(0).x + " ," + points.get(0).y + ",";
            description += "it ends at point " + points.get(len).x + " ," + points.get(len).y + ",";
            description += "Further, The line graph has a maximum Y value at point " + maxY.x + " ," + maxY.y + ",";
            description += "and a minimum Y value at point " + minY.x + " ," + minY.y + ",";
        }else if(graphType == Graph.BAR_CHART_MODE){
            description += "This is a Bar Graph, ";
            description += "The bar graph has a maximum value of " + maxY.y + ",";
            description += "it also has a minimum value of " + minY.y ;
        }

    }

    /**
     * Method to enable the audio assistant to describe the graph
     * @param context Current context of the function call
     */
    public void speak(Context context){
        analysePoints();
        Speech.instance.talk(description);
    }

    /**
     * \brief Find Max and Min Y value
     * Method to find points with the maximum and minimum values for Y
     */
    private void getInfo(){
        Point maxY = new Point(points.get(0).x, points.get(0).y);
        Point minY = new Point(points.get(0).x, points.get(0).y);

        for (int i = 0; i < points.size(); i++)
        {
            if (minY.y > points.get(i).y) {
                minY.y = points.get(i).y;
                minY.x = points.get(i).x;
            }

            if (maxY.y < points.get(i).y) {
                maxY.y = points.get(i).y;
                maxY.x = points.get(i).x;
            }
        }
        this.maxY = maxY;
        this.minY = minY;
    }
}
