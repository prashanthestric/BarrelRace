package com.game.barrelrace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import appsrox.example.accelerometer.R;

/**
 * Dialog Activity to inform the user that the game has been completed.
 * It allows the user to enter his name and save his high score.
 * @author Prashanth Govindaraj - pxg142030	
 *
 */
public class GameCompleteActivity extends Activity{
	TextView promptText;
	EditText nameText;
	Button doneButton,saveAndRestartButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.gamecomplete_layout);
		getWindow().setFlags(LayoutParams.FLAG_NOT_TOUCH_MODAL, LayoutParams.FLAG_NOT_TOUCH_MODAL);
		getWindow().setFlags(LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
		promptText=(TextView) findViewById(R.id.promptTextComp);
		nameText=(EditText) findViewById(R.id.nameTextComp);
		doneButton=(Button) findViewById(R.id.doneButtonComp);
		saveAndRestartButton=(Button) findViewById(R.id.saveAndExitButtonComp);
		promptText.setText(getIntent().getStringExtra("gameCompletePrompt"));
		doneButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent returnIntent = new Intent();
				returnIntent.putExtra("playerName", nameText.getText().toString());
				//Log.d("name", " "+nameText.getText().toString());
				setResult(4, returnIntent);
				finish();
			}
		});
		saveAndRestartButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent returnIntent = new Intent();
				returnIntent.putExtra("playerName", nameText.getText().toString());
				setResult(5, returnIntent);
				//Log.d("return intent", returnIntent.getStringExtra("playerName").toString());
				finish();
			}
		});
	}
	
	/**
	 * Overriding touch event to prevent closing the dialog box when touched outside.
	 */
	@Override
	  public boolean onTouchEvent(MotionEvent event) {
	    if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
	      //finish();
	      return true;
	    }
	    return super.onTouchEvent(event);
	  }
}
