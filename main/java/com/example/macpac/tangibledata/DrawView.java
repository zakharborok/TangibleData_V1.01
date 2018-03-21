package com.example.macpac.tangibledata;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * \class DrawView
 * \brief Class to draw the graph based on the graph type and the points from the Graph Converter.
 */
public class DrawView extends View
{
    Paint paint = new Paint(); /**< holds the style and color information about how to draw geometries, text and bitmaps.*/

    /**
     * Method identifies graph type and converts the parsed values into point objects
     */
    public DrawView(Context context)
    {
        super(context);
        Graph.instance = new Graph(GraphConverter.getGraphType(), null);
        GraphConverter.convertPoints();

//        switch (Graph.instance.getType())
//        {
//            case Graph.LINEAR_MODE:
//                Graph.instance.setInstancePoints(Graph.instance.resizeLinearPoints(/*GraphConverter.convertPoints()*/Graph.instance.getExampleLinerDataPoints()));
//                break;
//
//            case Graph.BAR_CHART_MODE:
//                Graph.instance.setInstancePoints(Graph.instance.resizeBarChartPoints(/*GraphConverter.convertPoints()*/Graph.instance.getExampleBarChartDataPoints()));
//                break;
//        }
    }

    public DrawView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    /**
     * Method draws the y axis and x axis of the graph and then plots the points.
     * @param canvas holds the draw calls
     */
    @Override
    public void onDraw(Canvas canvas)
    {
        drawScale(canvas);
        drawGraph(canvas);
    }

    /**
     * Method draws the graph depending on the graph type.
     *
     * Default Color: Yellow
     * Linear Graph: Points are drawn and joined by lines
     * Bar Graph: Points are drawn as rectangles
     * @param canvas holds the draw calls
     */
    private void drawGraph(Canvas canvas)
    {
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(16f);

        if (Graph.instance.getPoints().size() > 0)
            switch (Graph.instance.getType())
            {
                case Graph.LINEAR_MODE:
                    for (int i = 1; i < Graph.instance.getPoints().size(); i++)
                        canvas.drawLine(Graph.instance.getPoints().get(i - 1).x, Graph.instance.getPoints().get(i - 1).y, Graph.instance.getPoints().get(i).x, Graph.instance.getPoints().get(i).y, paint);
                    break;

                case Graph.BAR_CHART_MODE:
                    int singleWidth = (int) ((Graph.instance.X_OFFSET * 17) / Graph.instance.getPoints().size());
                    for (int i = 0; i < Graph.instance.getPoints().size(); i++)
                        canvas.drawRect((int) (singleWidth * (i) + Graph.instance.X_OFFSET * 1.1), (int) (Graph.instance.HEIGHT - Graph.instance.getPoints().get(i).y - Graph.instance.Y_OFFSET * 2.05), (int) (singleWidth * (0.7 + i) + Graph.instance.X_OFFSET * 1.1), (int) (Graph.instance.HEIGHT - Graph.instance.Y_OFFSET * 2.05), paint);
                    break;
            }
    }

    /**
     * Method draws lines to denote the X axis and the Y axis
     * Default Colour: Yellow
     * @param canvas holds the draw calls
     */
    private void drawScale(Canvas canvas)
    {
        paint.setARGB(250,104,104,0);
        paint.setStrokeWidth(10f);

        canvas.drawLine(Graph.instance.X_OFFSET, Graph.instance.Y_OFFSET, Graph.instance.X_OFFSET, Graph.instance.Y_OFFSET * 18, paint);
        canvas.drawLine(Graph.instance.X_OFFSET, Graph.instance.Y_OFFSET * 18, Graph.instance.X_OFFSET * 18, Graph.instance.Y_OFFSET * 18, paint);

        paint.setStrokeWidth(2f);
    }
}
