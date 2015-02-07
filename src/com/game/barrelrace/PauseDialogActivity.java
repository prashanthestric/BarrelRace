package com.game.barrelrace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import appsrox.example.accelerometer.R;

/**
 * Dialog activity of the pause menu, consisting of resume, restart and exit buttons.
 * @author Prashanth Govindaraj - pxg142030
 *
 */
public class PauseDialogActivity extends Activity{
	Button resumeButton,exitButton,restartButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pausemenu_layout);
		getWindow().setFlags(LayoutParams.FLAG_NOT_TOUCH_MODAL, LayoutParams.FLAG_NOT_TOUCH_MODAL);
		getWindow().setFlags(LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
		resumeButton=(Button) findViewById(R.id.resumeButton);
		resumeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent returnIntent = new Intent();
				setResult(1, returnIntent);
				finish();
			}
		});
		exitButton=(Button) findViewById(R.id.exitButton);
		exitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent returnIntent = new Intent();
				setResult(2, returnIntent);
				finish();
			}
		});
		restartButton=(Button) findViewById(R.id.restartButton);
		restartButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent returnIntent = new Intent();
				setResult(3, returnIntent);
				finish();
			}
		});
	}
	
	/**
	 * To prevent closing the dialog when touched outside.
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
