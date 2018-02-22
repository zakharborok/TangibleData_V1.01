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

    GraphAnalyser(ArrayList<Point> points){
        this.points = points;
        analysePoints();
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
        description += "The Graph starts at point "+points.get(0).x +" ,"+ points.get(0).y +".";
        description += "The Graph ends at point "+points.get(len).x +" ,"+ points.get(len).y +".";
        description += "The Graph has a maximum Y value at point "+maxY.x +" ,"+ maxY.y +".";
        description += "The Graph has a minimum Y value at point "+minY.x +" ,"+ minY.y +".";

    }
    
    public void speak(Context context){
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
