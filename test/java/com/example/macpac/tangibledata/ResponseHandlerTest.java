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
    public void calculateSinglePulseTest()
    {
        ResponseHandler responseHandler = getExampleResponseHandler(0);

        responseHandler.setSinglePulse(-1);
        assertThat(responseHandler.getPulseStength(), is(ResponseHandler.PULSE_STRENGTH_BELOW));
        assertThat(responseHandler.getSinglePulseTime(), is(ResponseHandler.SINGLE_PULSE_TIME_BELOW));

        responseHandler.setSinglePulse(1);
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

    @Test
    public void findTheLargestYValTest()
    {
        assertThat((int) getExampleResponseHandler(0).findTheLargestYVal(getExampleListOfPoints()), is(100));
    }

    @Test
    public void findTheLargestXValTest()
    {
        assertThat((int) getExampleResponseHandler(0).findTheLargestXVal(getExampleListOfPoints()), is(147));
    }

    @Test
    public void findTheSmallesYValTest()
    {
        assertThat((int) getExampleResponseHandler(0).findTheSmallesYVal(getExampleListOfPoints()), is(10));
    }

    @Test
    public void findTheSmallesXValTest()
    {
        assertThat((int) getExampleResponseHandler(0).findTheSmallesXVal(getExampleListOfPoints()), is(7));
    }

    private ResponseHandler getExampleResponseHandler(int type)
    {
        ResponseHandler responseHandler = new ResponseHandler(activity, getExampleListOfPoints(), type);

        return responseHandler;
    }

    private ArrayList<Point> getExampleListOfPoints()
    {
        ArrayList<Point> tempList = new ArrayList<>();

        tempList.add(new Point(7, 10));
        tempList.add(new Point(47, 50));
        tempList.add(new Point(147, 100));

        tempList.get(0).y = 10;
        tempList.get(1).y = 50;
        tempList.get(2).y = 100;

        tempList.get(0).x = 7;
        tempList.get(1).x = 47;
        tempList.get(2).x = 147;

        return tempList;
    }


}
