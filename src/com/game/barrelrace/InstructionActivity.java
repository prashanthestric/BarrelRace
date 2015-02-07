package com.game.barrelrace;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import appsrox.example.accelerometer.R;

/**
 * Dialog Activity to display game instructions.
 * @author Prashanth PC
 */
public class InstructionActivity extends Activity{
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.instruction_layout);
		}
}
