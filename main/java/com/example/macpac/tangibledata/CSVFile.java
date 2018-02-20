package com.example.macpac.tangibledata;

import android.util.Log;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by cabeywickra on 31/01/2018.
 */

public class CSVFile
{
    String filePath;

    public CSVFile(String filePath)
    {
        this.filePath = filePath;
    }

    public List<String[]> read() throws IOException
    {
        CSVReader reader = new CSVReader(new FileReader(filePath));
        List<String[]> myEntries = reader.readAll();
        return myEntries;
    }
}
