package com.antonbelka.stopwatch;

import java.util.ArrayList;
import java.util.TimerTask;

import android.content.Context;

public class StopwatchTimerTask extends TimerTask {
	protected int time;
	protected boolean isRunning = false;
	protected ArrayList<Integer> timerValues;
	private DbHelper mDbHelper;
	
	public StopwatchTimerTask(Context context) {
		super();
		mDbHelper = new DbHelper(context);
		timerValues = new ArrayList<Integer>();

	}
	
	@Override
	public void run() {
		if (time == 10000) {
			mDbHelper.addTime(timerValues.toString().replace("[", "")
					.replace("]", ""));
			isRunning = false;
			cancel();
		} else {
			time++;
		}
	}

}
