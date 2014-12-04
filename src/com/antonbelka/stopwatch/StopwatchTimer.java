package com.antonbelka.stopwatch;

import java.util.Timer;

import android.content.Context;

public class StopwatchTimer {
	private Timer timer;
	private StopwatchTimerTask timerTask;
	private Context context;

	public StopwatchTimer(Context context) {
		this.context = context;
	}

	public void start() {
		timer = new Timer();
		timerTask = new StopwatchTimerTask(context);
		timerTask.isRunning = true;
		timer.schedule(timerTask, 0, 1);
	}

	public int getTime() {
		if (timerTask == null) {
			return 0;
		}
		return timerTask.time;
	}

	public boolean isRunning() {
		if (timerTask == null) {
			return false;
		}

		return timerTask.isRunning;
	}
	
	public void addValue(Integer value) {
		timerTask.timerValues.add(value);
	}

}
