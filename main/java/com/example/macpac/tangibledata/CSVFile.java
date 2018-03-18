package com.example.macpac.tangibledata;

import android.util.Log;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * \class CSVFile
 * Class to represent and parse CSV File
 */
public class CSVFile {
    String filePath; /**< Stores the file path of the CSV file*/

    /**
     * \brief Constructor.
     * Method to construct a CSV File and set its file path
     * @param filePath Current file path of the csv file.
     */
    public CSVFile(String filePath)
    {
        this.filePath = filePath;
    }

    /**
     * Method to parse the csv file and read each of the values
     * @return result of entries in the csv file as a list
     */
    public List<String[]> read() throws IOException
    {
        CSVReader reader = new CSVReader(new FileReader(filePath));
        List<String[]> myEntries = reader.readAll();
        return myEntries;
    }
}
