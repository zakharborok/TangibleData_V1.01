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

        switch (GraphConverter.getGraphType())
        {
            case Graph.LINEAR_MODE:
                Graph.instance = new Graph(Graph.LINEAR_MODE, Graph.instance.resizeLinearPoints(GraphConverter.convertPoints()));
                break;

            case Graph.BAR_CHART_MODE:
                Graph.instance = new Graph(Graph.BAR_CHART_MODE, Graph.instance.resizeBarChartPoints(GraphConverter.convertPoints()));
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
                    int singleWidth = (int) ((Graph.X_OFFSET * 17) / Graph.instance.getPoints().size());
                    for (int i = 0; i < Graph.instance.getPoints().size(); i++)
                        canvas.drawRect((int) (singleWidth * (i) + Graph.X_OFFSET * 1.1), (int) (Graph.HEIGHT - Graph.instance.getPoints().get(i).y - Graph.Y_OFFSET * 2.05), (int) (singleWidth * (0.7 + i) + Graph.X_OFFSET * 1.1), (int) (Graph.HEIGHT - Graph.Y_OFFSET * 2.05), paint);
                    break;
            }
    }

    private void drawScale(Canvas canvas)
    {
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(6f);

        canvas.drawLine(Graph.X_OFFSET, Graph.Y_OFFSET, Graph.X_OFFSET, Graph.Y_OFFSET * 18, paint);
        canvas.drawLine(Graph.X_OFFSET, Graph.Y_OFFSET * 18, Graph.X_OFFSET * 18, Graph.Y_OFFSET * 18, paint);

        paint.setStrokeWidth(2f);
    }
}
