package com.example.macpac.tangibledata;

import android.content.Context;
import android.graphics.Point;

import java.util.ArrayList;

public class GraphAnalyser {
    private String description;
    private ArrayList<Point> points;
    private Point maxY;
    private Point minY;
    private int graphType;

    GraphAnalyser(ArrayList<Point> points, int graphType){
        this.points = points;
        this.graphType = graphType;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    private void analysePoints(){
        getInfo();
        //:TODO Round Values
        //:TODO add stopper
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

    public void speak(Context context){
        analysePoints();
        Speech.instance.talk(description);
    }

    private void getInfo(){
        Point maxY = new Point(points.get(0).x, points.get(0).y);
        Point minY = new Point(points.get(0).x, points.get(0).y);

        //:TODO save corresponding Xs to maxs
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
