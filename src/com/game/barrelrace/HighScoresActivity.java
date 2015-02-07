package com.game.barrelrace;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ListView;
import appsrox.example.accelerometer.R;

/**
 * Activity to list the top 10 high scores. 
 * @author Kamesh Santhanam - kxs142530
 *
 */
public class HighScoresActivity extends Activity{
	ListView list;
	TextFileReader fileReader;
	ArrayList<HighScore> highScoresList;
	CustomListViewAdapter adapter;
	Context con;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.highscores_layout);
		con=this;
		highScoresList=new ArrayList<HighScore>();
		fileReader=new TextFileReader(Environment.getExternalStorageDirectory()+"/highscores.txt");
		try {
			highScoresList=fileReader.ReadRecordsToArrayList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		list = (ListView)findViewById(R.id.listView1);        
        adapter = new CustomListViewAdapter(con,R.layout.highscores_layout, highScoresList);
	    list.setAdapter(adapter);
	    list.setFastScrollEnabled(true);
	}
}
