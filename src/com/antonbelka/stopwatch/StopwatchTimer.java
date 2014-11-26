package com.antonbelka.stopwatch;

import java.util.Timer;

public class StopwatchTimer {
	private Timer timer;
	private StopwatchTimerTask timerTask;

	public StopwatchTimer() {

	}

	public void start() {
		timer = new Timer();
		timerTask = new StopwatchTimerTask();
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

}
