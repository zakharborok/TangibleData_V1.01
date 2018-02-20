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
        intended(hasComponent(new ComponentName(getTargetContext(), GraphTypeView.class)));
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
        //Select Line Graph mode
        onView(withId(R.id.buttonLineGraph)).perform(longClick());
        //Check if GraphView Activity has opened
        intended(hasComponent(new ComponentName(getTargetContext(), GraphView.class)));
        //Check if Graph is in Line Graph Mode
        assertTrue(Graph.type == Graph.LINEAR_MODE);
    }

    @Test
    public void testSpeechLineGraph(){
        //Select Sample File From ListView
        onView(withText("SampleFile.csv")).perform(longClick());
        //Select Line Graph mode
        //click() will not Change Activity Instead will Activate Speech
        onView(withId(R.id.buttonLineGraph)).perform(click());
        //Check if Activity Stays the Same
        intended(hasComponent(new ComponentName(getTargetContext(), GraphTypeView.class)));
    }

    @Test
    public void testSelectBarGraph(){
        //Select Sample File From ListView
        onView(withText("SampleFile.csv")).perform(longClick());
        //Select Bar Graph mode
        onView(withId(R.id.buttonBarGraph)).perform(longClick());
        //Check if GraphView Activity has opened
        intended(hasComponent(new ComponentName(getTargetContext(), GraphView.class)));
        //Check if Graph is in Bar Graph Mode
        assertTrue(Graph.type == Graph.BAR_CHART_MODE);

    }

    @Test
    public void testSpeechBarGraph(){
        //Select Sample File From ListView
        onView(withText("SampleFile.csv")).perform(longClick());
        //Select Bar Graph mode
        //click() will not Change Activity Instead will Activate Speech
        onView(withId(R.id.buttonBarGraph)).perform(click());
        //Check if Activity Stays the Same
        intended(hasComponent(new ComponentName(getTargetContext(), GraphTypeView.class)));
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

}