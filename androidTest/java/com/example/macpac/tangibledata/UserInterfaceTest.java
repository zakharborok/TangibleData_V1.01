package com.example.macpac.tangibledata;

import android.content.ComponentName;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;



public class UserInterfaceTest {

    @Rule
    public ActivityTestRule<Menu> menu = new ActivityTestRule<>(Menu.class);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @Test
    public void testSelectListViewItem(){
        //Select Sample File From ListView
        onView(withText("SampleFile.csv")).perform(longClick());
        //Check if GraphTypeView Activity has opened
        intended(hasComponent(new ComponentName(getTargetContext(), GraphView.class)));
    }

    @Test
    public void testSpeechListViewItem(){
        //Select Sample File From ListView
        onView(withText("SampleFile.csv")).perform(click());
    }

    @Test
    public void testSelectLineGraph(){
        //Select Sample File From ListView
        onView(withText("SampleFile.csv")).perform(longClick());
        //Check if GraphView Activity has opened
        intended(hasComponent(new ComponentName(getTargetContext(), GraphView.class)));
        //Check if Graph is in Line Graph Mode
        assertTrue(Graph.instance.getType() == Graph.LINEAR_MODE);
    }


    @Test
    public void testSelectBarGraph(){
        //Select Sample File From ListView
        onView(withText("BarGraph.csv")).perform(longClick());
        //Check if GraphView Activity has opened
        intended(hasComponent(new ComponentName(getTargetContext(), GraphView.class)));
        //Check if Graph is in Bar Graph Mode
        assertTrue(Graph.instance.getType() == Graph.BAR_CHART_MODE);
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

}