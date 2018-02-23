package com.example.macpac.tangibledata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by cabeywickra on 05/02/2018.
 */

public class GraphTypeView extends Activity
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        Speech.instance.talk(getApplicationContext(), "Select graph type");
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_select_mode);
        final Button barGraph = (Button) findViewById(R.id.buttonBarGraph);
        final Button lineGraph = (Button) findViewById(R.id.buttonLineGraph);


        barGraph.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Speech.instance.talk(getApplicationContext(), "Bar Graph, hold to select");
            }
        });
        barGraph.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                GraphConverter.setGraphType(Graph.BAR_CHART_MODE);
                Intent i = new Intent(GraphTypeView.this, GraphView.class);
                startActivity(i);
                return false;
            }
        });

        lineGraph.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Speech.instance.talk(getApplicationContext(), "Line Graph, hold to select");
            }
        });

        lineGraph.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                GraphConverter.setGraphType(Graph.LINEAR_MODE);
                Intent i = new Intent(GraphTypeView.this, GraphView.class);
                startActivity(i);
                return false;
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Speech.instance.talk(getApplicationContext(), "Select graph type");
    }


}
