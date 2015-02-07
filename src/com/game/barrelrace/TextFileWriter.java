package com.game.barrelrace;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *The TextFileWriter class contains the properties and methods required for writing the current data to the file. 
 * @author Kamesh Santhanam  -  kxs142530
 *
 * 
 */
public class TextFileWriter {

	String fileLocation;
	ArrayList<HighScore> recordsList;
	
	/**
	 * Constructor for TextFileWriter receives the path of the text file and the ArrayList of records to be used.
	 */
	public TextFileWriter(String file_location,ArrayList<HighScore> recoList){
		
		fileLocation=file_location;
		recordsList=recoList;
	}
	
	
	/**
	 * reWriteFile() is used to re-write the text file with the new, updated, contents of the ArrayList of records
	 */
	public void reWriteFile() throws IOException{
		FileWriter fileWriter=new FileWriter(fileLocation,false);
		PrintWriter printWriter=new PrintWriter(fileWriter);
		
		for(int i=0;i<recordsList.size();i++){
			//Log.d("in loop","in loop");
			HighScore temp_record=recordsList.get(i);
			//Log.d("fetch element","got "+i+"th element");
			String temp=temp_record.getName()+"\t"+temp_record.getMilliSeconds();
			//Log.d("temp","temp");
			printWriter.println(temp);
			//Log.d("p write","p write");
		}
		printWriter.close();
		
	}
}
