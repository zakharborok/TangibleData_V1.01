package com.example.macpac.tangibledata;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by MacPac on 21/02/2018.
 */
public class GraphConverterTest
{
    @Before
    public void setup()
    {

    }

    @Test
    public void setGraphTypeTest() throws Exception
    {
        GraphConverter.setGraphType(Graph.LINEAR_MODE);
        assertThat(GraphConverter.getGraphType(), is(Graph.LINEAR_MODE));
    }

    @Test
    public void getGraphTypeTest() throws Exception
    {
        GraphConverter.setGraphType(Graph.BAR_CHART_MODE);
        assertThat(GraphConverter.getGraphType(), is(Graph.BAR_CHART_MODE));
    }

    @Test
    public void convertPointsTest() throws Exception
    {
        ArrayList<String[]> pointsFromFile = new ArrayList<>();
        String[] vals = {"1", "2", "3"};
        pointsFromFile.add(new String[]{"x", "y"});
        pointsFromFile.add(new String[]{"1", "2"});
        pointsFromFile.add(new String[]{"1", "2"});
    }

}