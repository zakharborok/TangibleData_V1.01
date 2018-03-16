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
        graphAnalyser = new GraphAnalyser(Graph.instance.getOriginalPoints(), Graph.instance.getType());
        soundGraphGenerator = new SoundGraphGenerator(Graph.instance.getPoints());
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

        switch (Graph.instance.getType())
        {
            case Graph.LINEAR_MODE:
                switch (responseHandler.getTouchCounter())
                {
                    case 2:
                        graphAnalyser.speak(responseHandler.getContext());
                        break;
                    case 3:
                        if (!Speech.instance.isTalking())
                            soundGraphGenerator.run();
                        break;

                }
                break;

            case Graph.BAR_CHART_MODE:
                if (responseHandler.getTouchCounter() == 2)
                    graphAnalyser.speak(responseHandler.getContext());
                break;
        }
    }

}
