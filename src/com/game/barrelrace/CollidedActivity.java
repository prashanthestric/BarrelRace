package com.game.barrelrace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import appsrox.example.accelerometer.R;

/**
 * Activity to indicate user that the horse has collided with the barrel
 * @author Kamesh Santhanam - kxs142530
 *
 */
public class CollidedActivity extends Activity{
	Button exitButton,restartButton;
	TextView promptText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.collided_layout);
		getWindow().setFlags(LayoutParams.FLAG_NOT_TOUCH_MODAL, LayoutParams.FLAG_NOT_TOUCH_MODAL);
		getWindow().setFlags(LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
		promptText=(TextView) findViewById(R.id.promptTextView);
		promptText.setText(getIntent().getStringExtra("prompt"));
		exitButton=(Button) findViewById(R.id.exitButtonCol);
		exitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent returnIntent = new Intent();
				setResult(2, returnIntent);
				finish();
			}
		});
		restartButton=(Button) findViewById(R.id.restartButtonCol);
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
