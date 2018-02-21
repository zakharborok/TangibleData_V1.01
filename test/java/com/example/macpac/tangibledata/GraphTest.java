package com.example.macpac.tangibledata;

/**
 * Created by MacPac on 21/02/2018.
 */

import android.graphics.Point;
import android.util.DisplayMetrics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.ArrayList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class GraphTest
{
    @Mock
    Graph graph;

    @Test
    public void resizeLinearPointsTest()
    {
        DisplayMetrics metric = new DisplayMetrics();
        metric.widthPixels = 768;
        metric.heightPixels = 1024;
        graph = new Graph(Graph.LINEAR_MODE, getExampleListOfPoints(), metric);
        graph.setPoints(graph.resizeLinearPoints(graph.getPoints()));

        assertThat(graph.getPoints().get(0).y, is(graph.Y_OFFSET * 16 + 81));
        assertThat(graph.getPoints().get(1).y, is(graph.Y_OFFSET * 8 + 81));
        assertThat(graph.getPoints().get(2).y, is(81));

        assertThat(graph.getPoints().get(0).x, is(57));
        assertThat(graph.getPoints().get(1).x, is(graph.X_OFFSET * 8 + 57));
        assertThat(graph.getPoints().get(2).x, is(graph.X_OFFSET * 16 + 57));
    }

    @Test
    public void resizeBarChartPointsTest()
    {
        DisplayMetrics metric = new DisplayMetrics();
        metric.widthPixels = 768;
        metric.heightPixels = 1024;
        graph = new Graph(Graph.BAR_CHART_MODE, getExampleListOfPoints(), metric);
        graph.setPoints(graph.resizeBarChartPoints(graph.getPoints()));

        assertThat(graph.getPoints().get(0).y, is(0));
        assertThat(graph.getPoints().get(1).y, is(graph.Y_OFFSET * 8));
        assertThat(graph.getPoints().get(2).y, is(graph.Y_OFFSET * 16));
    }

    private ArrayList<Point> getExampleListOfPoints()
    {
        ArrayList<Point> tempList = new ArrayList<>();

        tempList.add(new Point(0, 0));
        tempList.add(new Point(50, 50));
        tempList.add(new Point(100, 100));

        tempList.get(0).x = 0;
        tempList.get(1).x = 50;
        tempList.get(2).x = 100;

        tempList.get(0).y = 0;
        tempList.get(1).y = 50;
        tempList.get(2).y = 100;

        return tempList;
    }
}
