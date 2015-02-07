package com.game.barrelrace;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The TextFileReader class contains the properties and methods needed to read data from the text file.
 * @author Prashanth Govindaraj  -  pxg142030
 *
 * 
 */
public class TextFileReader {
	
	private String fileLocation;
	
	/**
	 * Constructor for TextFileReader receives the path of the text file to be used.
	 */
	public TextFileReader(String file_location){
		fileLocation=file_location;
	}
	
	/**
	 * noOfLines is used to compute the total number of records stored in the text file.
	 */
	int noOfLines() throws IOException{
		FileReader fileReader=new FileReader(fileLocation);
		BufferedReader bufferedReader=new BufferedReader(fileReader);
		int numOfLines=0;
		
		while ((bufferedReader.readLine())!=null){
			numOfLines++;
			}
		
		bufferedReader.close();
		return numOfLines;
	}

	/**
	 * ReadLinesToArray() returns an ArrayList containing all the records in the text file. 
	 * Each entry in the ArrayList is a Contact object.   
	 */
	public ArrayList<HighScore> ReadRecordsToArrayList() throws IOException{
		FileReader fileReader=new FileReader(fileLocation);
		BufferedReader bufferedReader=new BufferedReader(fileReader);
		int numOfLines=noOfLines();
		ArrayList<HighScore> recordList=new ArrayList<HighScore>();
		
		for(int i=0;i<numOfLines;i++){
			String temp_record=bufferedReader.readLine();
			String[] record=temp_record.split("\t");
			recordList.add(new HighScore(record[0],Long.parseLong(record[1])));
		}
		sortRecords(recordList);
		
		bufferedReader.close();
		return recordList;
	}
	
	/**
	 * sortRecords() is used to sort the records in the ArrayList based on highscore.
	 */
	void sortRecords(final ArrayList<HighScore> recordsList){
		Collections.sort(recordsList,new Comparator<HighScore>() {
            public int compare(HighScore rec1, HighScore rec2) {
            	return (int) (rec1.getMilliSeconds()-rec2.getMilliSeconds());                     	
            }
        });
	}
}
