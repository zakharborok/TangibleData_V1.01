package com.example.macpac.tangibledata;

import android.content.Context;
import android.graphics.Point;

import java.util.ArrayList;

/**
 * Created by cabeywickra on 22/02/2018.
 */

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
        int len = points.size()-1;
        if (graphType == Graph.LINEAR_MODE) {
            description += "Graph Type: Line Graph.";
            description += "The line graph starts at point " + points.get(0).x + " ," + points.get(0).y + ",";
            description += "it ends at point " + points.get(len).x + " ," + points.get(len).y + ",";
            description += "Further, The line graph has a maximum Y value at point " + maxY.x + " ," + maxY.y + ",";
            description += "and a minimum Y value at point " + minY.x + " ," + minY.y + ",";
        }else if(graphType == Graph.BAR_CHART_MODE){
            description += "Graph Type: Bar Graph.";
            description += "The bar graph has a maximum value of " + maxY.y + ",";
            description += "it also has a minimum value of " + minY.y ;
            int i=0;
            while(i <= len){
                description += "The "+(i+1);
                if (i==0){
                    description += "st ";
                }else if(i==1){
                    description += "nd ";
                }else if(i==2){
                    description += "rd ";
                }else{
                    description+= "th ";
                }
                description += "bar graph has a value of " + points.get(i).y + " .";
            }
        }

    }

    public void speak(Context context){
        analysePoints();
        Speech.instance.Talk(context, description);
    }

    private void getInfo(){
        Point maxY = points.get(0), minY = points.get(0);

        for (int i = 0; i < points.size(); i++)
        {
            if (minY.y > points.get(i).y)
                minY.y = points.get(i).y;
                minY.x = points.get(i).x;

            if (maxY.y < points.get(i).y)
                maxY.y = points.get(i).y;
                maxY.x = points.get(i).x;
        }
        this.maxY = maxY;
        this.minY = minY;
    }
}
