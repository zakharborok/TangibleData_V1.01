package com.example.macpac.tangibledata;

import android.Manifest;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Menu extends ListActivity
{
    //TODO Add permissions prompt
    private ArrayList<String> fileList = new ArrayList<String>();
    private GraphFileManager graphFileManager = new GraphFileManager();
    private ListView lv;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Speech.instance.talk("Select file");
        File root = new File("sdcard/CSV/");
        lv = getListView();
        ListDir(root);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Speech.instance.talk("Select file");
    }

    void ListDir(File f)
    {
        int permission = PermissionChecker.checkSelfPermission(getApplicationContext(),"android.permission.READ_EXTERNAL_STORAGE");

        if (permission != PermissionChecker.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Menu.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
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
                    Speech.instance.talk(fileList.get(i) + ", hold to select");
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ListDir(new File("sdcard/CSV/"));

                } else {
                    Toast.makeText(Menu.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
