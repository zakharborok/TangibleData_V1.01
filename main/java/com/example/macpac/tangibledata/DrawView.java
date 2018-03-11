package com.example.macpac.tangibledata;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by MacPac on 15/01/2018.
 */

public class DrawView extends View
{
    Paint paint = new Paint();

    public DrawView(Context context)
    {
        super(context);

        Graph.instance = new Graph(GraphConverter.getGraphType(), null);
        Graph.instance.setOriginalPoints(Graph.instance.getExampleLinerDataPoints());

        switch (Graph.instance.getType())
        {
            case Graph.LINEAR_MODE:
                Graph.instance.setInstancePoints(Graph.instance.resizeLinearPoints(/*GraphConverter.convertPoints()*/Graph.instance.getExampleLinerDataPoints()));
                break;

            case Graph.BAR_CHART_MODE:
                Graph.instance.setInstancePoints(Graph.instance.resizeBarChartPoints(/*GraphConverter.convertPoints()*/Graph.instance.getExampleBarChartDataPoints()));
                break;
        }
    }

    public DrawView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        drawScale(canvas);
        drawGraph(canvas);
    }

    private void drawGraph(Canvas canvas)
    {
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(4f);

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

    private void drawScale(Canvas canvas)
    {
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(6f);

        canvas.drawLine(Graph.instance.X_OFFSET, Graph.instance.Y_OFFSET, Graph.instance.X_OFFSET, Graph.instance.Y_OFFSET * 18, paint);
        canvas.drawLine(Graph.instance.X_OFFSET, Graph.instance.Y_OFFSET * 18, Graph.instance.X_OFFSET * 18, Graph.instance.Y_OFFSET * 18, paint);

        paint.setStrokeWidth(2f);
    }
}
