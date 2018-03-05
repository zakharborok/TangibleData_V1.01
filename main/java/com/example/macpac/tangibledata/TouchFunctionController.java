package com.example.macpac.tangibledata;

/**
 * Created by MacPac on 05/03/2018.
 */

public class TouchFunctionController extends Thread
{
    public static TouchFunctionController instanse;
    private ResponseHandler responseHandler;
    private GraphAnalyser graphAnalyser;
    private SoundGraphGenerator soundGraphGenerator;


    public TouchFunctionController(ResponseHandler responseHandler)
    {
        super("Give me touches");
        this.responseHandler = responseHandler;
        graphAnalyser = new GraphAnalyser(Graph.instance.getPoints(),Graph.instance.getType());
        soundGraphGenerator = new SoundGraphGenerator(Graph.instance.getPoints());
        if (instanse != null) return instanse;
    }

    @Override
    public void run()
    {
        try
        {
            sleep(1500);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        switch (responseHandler.getTouchCounter())
        {
            case 2:
                graphAnalyser.speak(responseHandler.getContext());
                break;
            case 3:
                soundGraphGenerator.run();
                break;

        }
    }

}
