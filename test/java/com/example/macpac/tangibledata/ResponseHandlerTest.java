package com.example.macpac.tangibledata;

import android.app.Activity;
import android.graphics.Point;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.ArrayList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ResponseHandlerTest
{
    @Mock
    Activity activity = new Activity();

    @Test
    public void findTheLargestYValTest()
    {
        assertThat((int) getExampleResponseHandler(0).findTheLargestYVal(getExampleListOfPoints()), is(100));
    }

    @Test
    public void getPulseStrenghtTest()
    {
        ResponseHandler responseHandler = getExampleResponseHandler(0);

        assertThat(responseHandler.calculatePulseStrenght(1), is(5));
        assertThat(responseHandler.calculatePulseStrenght(20), is(51));
        assertThat(responseHandler.calculatePulseStrenght(99), is(250));
    }

    @Test
    public void calculateSinglePulseTest()
    {
        ResponseHandler responseHandler = getExampleResponseHandler(0);

        responseHandler.calculateSinglePulse(-1);
        assertThat(responseHandler.getPulseStength(), is(ResponseHandler.PULSE_STRENGTH_BELOW));
        assertThat(responseHandler.getSinglePulseTime(), is(ResponseHandler.SINGLE_PULSE_TIME_BELOW));

        responseHandler.calculateSinglePulse(1);
        assertThat(responseHandler.getPulseStength(), is(ResponseHandler.PULSE_STRENGTH_ABOVE));
        assertThat(responseHandler.getSinglePulseTime(), is(ResponseHandler.SINGLE_PULSE_TIME_ABOVE));
    }

    @Test
    public void addValuesToMapTest()//also tests internal function 'addValuesToMap'
    {
        ResponseHandler responseHandler = getExampleResponseHandler(1);
        responseHandler.addValuesToMap(10,10,20,20);

        assertThat(responseHandler.getMap().size(), is(11));
        assertThat(responseHandler.getMap().containsKey(10), is(true));
        assertThat(responseHandler.getMap().containsKey(15), is(true));
        assertThat(responseHandler.getMap().containsKey(20), is(true));
        assertThat(responseHandler.getMap().containsKey(28), is(false));
        assertThat(responseHandler.getMap().containsKey(0), is(false));
        assertThat(responseHandler.getMap().containsKey(-7), is(false));
    }

    private ResponseHandler getExampleResponseHandler(int type)
    {
        ResponseHandler responseHandler = new ResponseHandler(activity, getExampleListOfPoints(), type);
        responseHandler.setLARGEST_Y_VAL(100);

        return responseHandler;
    }

    private ArrayList<Point> getExampleListOfPoints()
    {
        ArrayList<Point> tempList = new ArrayList<>();

        tempList.add(new Point(10, 10));
        tempList.add(new Point(50, 50));
        tempList.add(new Point(100, 100));

        tempList.get(0).y = 10;
        tempList.get(1).y = 50;
        tempList.get(2).y = 100;

        return tempList;
    }


}
