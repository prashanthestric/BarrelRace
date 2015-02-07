package com.game.barrelrace;

//import android.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import appsrox.example.accelerometer.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Main game screen which contains the custom game view, timer, start and pause buttons.
 */
public class MainActivity extends Activity {
	
	private WakeLock mWakeLock;
	private GameView mSimulationView;
	private Button startButton,pauseButton;
	private ImageButton hiScoreActBar;
	public static TextView stopClockText;
	private TextFileReader fileReader;
	private TextFileWriter fileWriter;
	private ArrayList<HighScore> highScoresList;
	private long timeToCompleteThisGame;
	private static final ScheduledExecutorService worker=Executors.newSingleThreadScheduledExecutor();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        setContentView(R.layout.game_layout);
        
		PowerManager mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "myWakeLock");
        
			//createFileIfNotExists();
		highScoresList=new ArrayList<HighScore>();
		fileReader=new TextFileReader(Environment.getExternalStorageDirectory()+"/highscores.txt");
		try {
			highScoresList=fileReader.ReadRecordsToArrayList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fileWriter=new TextFileWriter(Environment.getExternalStorageDirectory()+"/highscores.txt", highScoresList);
		
        stopClockText= (TextView) findViewById(R.id.textView1);
        mSimulationView = (GameView) findViewById(R.id.myGameView);
        //mSimulationView.setGameScreenContext(this);
        stopClockText= (TextView) findViewById(R.id.textView1);
        pauseButton = (Button) findViewById(R.id.button2);
        pauseButton.setEnabled(false);
        pauseButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				pauseGame();
			}
		});
        startButton = (Button) findViewById(R.id.button1);
        startButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startButton.setEnabled(false);
				pauseButton.setEnabled(true);
				mWakeLock.acquire();		
				mSimulationView.setGamePaused(false);
				mSimulationView.startSimulation();
			}
		});
        hiScoreActBar=(ImageButton) findViewById(R.id.hiScoreActBar);
        hiScoreActBar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,HighScoresActivity.class);
				startActivity(intent);
			}
		});
        mSimulationView.setCollisionEventListener(new CollisionEventListener() {
			
			@Override
			public void onCollision() {
				// TODO Auto-generated method stub
				pauseButton.setEnabled(false);
				Intent intent=new Intent(MainActivity.this, CollidedActivity.class);
				intent.putExtra("prompt", "Oops! You collided with a barrel");
				startActivityForResult(intent, 1);
			}
		});
        mSimulationView.setGameCompletionListener(new GameCompletionListener() {
			
			@Override
			public void onGameComplete(long elapsedTime) {
				// TODO Auto-generated method stub
				Log.d("ongamecomplete","ongamecompllleteeeeee");
				timeToCompleteThisGame=elapsedTime;
				Intent intent=new Intent(MainActivity.this, GameCompleteActivity.class);
				if(isHighestScore(elapsedTime)){
					intent.putExtra("gameCompletePrompt", "New high score! Enter your name\nYour time: "+stopClockText.getText());
				}
				else{
					intent.putExtra("gameCompletePrompt", "Game completed! Enter your name\nYour time: "+stopClockText.getText());
				}
				startActivityForResult(intent, 1);
			}
		});
	}
	
	/**
	 * Checks if the time that the player has completed in is the best time.
	 * @param time
	 * @return
	 */
	boolean isHighestScore(long time){
		if(highScoresList.size()==0){
			return true;
		}
		else if(time<highScoresList.get(0).getMilliSeconds()){
			return true;
		}
		else{
			return false;
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	   super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedState) {
	   super.onRestoreInstanceState(savedState);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
    	mWakeLock.acquire();
    	if(mSimulationView.isGamePaused()){
		pauseButton.setEnabled(true);
    	}
    	hiScoreActBar.setEnabled(true);
		if(mSimulationView.isGamePaused()){
		  new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
							mSimulationView.stopClock.resume();
							mSimulationView.setGamePaused(false);
			            }
			        }, 
			        5000 
			);}
	}
	
	
	@Override
	public void onBackPressed()
	{
		pauseGame();
	}

	/**
	 * Opens the pause menu and sets flags to indicate that the game has been paused.
	 */
	private void pauseGame() {
		pauseButton.setEnabled(false);
		mSimulationView.setGamePaused(true);
		Intent intent=new Intent(MainActivity.this, PauseDialogActivity.class);
		startActivityForResult(intent, 1);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 1){
			Log.d("resume","resume");
			//mSimulationView.setGamePaused(false);
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
							mSimulationView.stopClock.resume();
							mSimulationView.setGamePaused(false);
			            }
			        }, 
			        5000 
			);
		}else if(resultCode == 2){
			Log.d("exit","exit");
			finish();
		}else if(resultCode == 3){
			Log.d("restart","restart");
			finish();
			Intent intent=new Intent(getApplicationContext(), MainActivity.class);
			startActivity(intent);
		}else if(resultCode==4){
			finish();
			Log.d("done", "done");
			updateFile(data.getStringExtra("playerName"), timeToCompleteThisGame);
			
		}else if(resultCode==5){
			finish();
			Log.d("save and restart", "save and restart");
			updateFile(data.getStringExtra("playerName"), timeToCompleteThisGame);			
			Intent intent=new Intent(getApplicationContext(), MainActivity.class);
			startActivity(intent);
		}
	}
	
	void updateFile(String name,long time){
		highScoresList.add(new HighScore(name, time));
		sortRecords(highScoresList);
		curtailLength(highScoresList, 10);
		try {
			fileWriter.reWriteFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Restricts the length of thr high scores array list to 10.
	 * @param hiScoresList
	 * @param length
	 */
	void curtailLength(ArrayList<HighScore> hiScoresList,int length){
		for(int i=0;i<hiScoresList.size();i++){
			if(i>length){
				highScoresList.remove(i);
			}
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		hiScoreActBar.setEnabled(false);
		mSimulationView.stopClock.pause();
		mSimulationView.setGamePaused(true);
		mWakeLock.release();
	}
	
	/**
	 * Sorts the array list of high scores based on game completion times.
	 * @param recordsList
	 */
	void sortRecords(final ArrayList<HighScore> recordsList){
		Collections.sort(recordsList,new Comparator<HighScore>() {
            public int compare(HighScore rec1, HighScore rec2) {
            	return (int) (rec1.getMilliSeconds()-rec2.getMilliSeconds());                     	
            }
        });
	}
	
}
