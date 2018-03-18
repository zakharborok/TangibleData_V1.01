package com.example.macpac.tangibledata;

import android.util.Log;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GraphFileManager
{
    //Filters The parent directory and gets all the CSV files
    public List<File> getAllFiles(File parentDir)
    {
        List<File> inFiles = new ArrayList<>();
        Queue<File> files = new LinkedList<>();
        File[] parentDirFiles = parentDir.listFiles();
        if (parentDirFiles != null)
        {
            files.addAll(Arrays.asList(parentDir.listFiles()));
        }
        while (!files.isEmpty())
        {
            File file = files.remove();
            if (file.isDirectory())
            {
                files.addAll(Arrays.asList(file.listFiles()));
            } else if (file.getName().endsWith(".csv"))
            {
                Log.i("File:", file.getName());
                inFiles.add(file);
            }
        }
        return inFiles;
    }

    //Gets the file name and converts the List<File> to List<String>
    public List<String> getPath(File parentDir)
    {
        List<String> mPath = new ArrayList<String>();
        for (File f : getAllFiles(parentDir))
        {
            mPath.add(f.getName());
        }
        return mPath;
    }
}