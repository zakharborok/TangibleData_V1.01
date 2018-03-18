package com.example.macpac.tangibledata;

/**
 * \class TouchFunctionController
 * \brief Class which is responsible for function managing calls depending on number of touches.
 * Thread of this class is created when user touches the screen. Then put to sleep for 1.5s. After which it wakes up to check number of touches made during that period and calls correspondingly functions.
 */
public class TouchFunctionController extends Thread
{
    public static TouchFunctionController instanse; /**< Global reference to a single instance of the class. */
    private ResponseHandler responseHandler; /**< Reference of a ResponseHandler which created a thread. It is used to check number of touches made during sleep period.*/
    private GraphAnalyser graphAnalyser; /**< GraphAnalyser is called when number of touches is 3.*/
    private SoundGraphGenerator soundGraphGenerator; /**< GraphAnalyser is called when number of touches is 4.*/

    /**
     * \brief Constructor.
     * Method to create instance of TouchFunctionController.
     */
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
