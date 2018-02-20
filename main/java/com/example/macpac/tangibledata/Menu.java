package com.example.macpac.tangibledata;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class Menu extends ListActivity
{
    //TODO Add permissions prompt
    private ArrayList<String> fileList = new ArrayList<String>();
    private GraphFileManager graphFileManager = new GraphFileManager();
    private ListView lv;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Speech.Talk(getApplicationContext(), "Select file");

        File root = new File("sdcard/CSV/");
        lv = getListView();
        ListDir(root);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Speech.Talk(getApplicationContext(), "Select file");
    }

    void ListDir(File f)
    {
        fileList.clear();
        fileList = (ArrayList<String>) graphFileManager.getPath(f);
        if (fileList.size() != 0)
        {
            ArrayAdapter<String> directoryList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, graphFileManager.getPath(f));
            setListAdapter(directoryList);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    Speech.Talk(getApplicationContext(), fileList.get(i) + ", hold to select");
                }
            });
            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
            {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    try
                    {
                        viewCsv(fileList.get(i));
                    } catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                    return true;
                }
            });
        }
    }

    private void viewCsv(String file) throws FileNotFoundException
    {
        File csvFile = new File("sdcard/CSV/" + file);

        try
        {
            Toast.makeText(this, "Reading file: " + csvFile.getPath(), Toast.LENGTH_SHORT).show();
            GraphConverter.setCSVfile(csvFile);

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        Intent i = new Intent(Menu.this, GraphTypeView.class);
        startActivity(i);
    }

}
