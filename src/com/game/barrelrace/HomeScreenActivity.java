package com.game.barrelrace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import appsrox.example.accelerometer.R;

/**
 * Home Screen of the app, from where the user can start a new game, view high scores or instructions. 
 * @author Prashanth PC
 *
 */
public class HomeScreenActivity extends Activity{
	Button playGameButton,highScoresButton,instructionsButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreen_layout);
		playGameButton=(Button) findViewById(R.id.playGameButton);
		highScoresButton=(Button) findViewById(R.id.highScoresButton);
		instructionsButton=(Button) findViewById(R.id.instructionsButton);
		playGameButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HomeScreenActivity.this,MainActivity.class);
				startActivity(intent);
			}
		});
		highScoresButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HomeScreenActivity.this,HighScoresActivity.class);
				startActivity(intent);				
			}
		});
		
		instructionsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HomeScreenActivity.this,InstructionActivity.class);
				startActivity(intent);				
			}
		});
		
		
	}
}
