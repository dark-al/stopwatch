package com.antonbelka.stopwatch;

import java.util.TimerTask;

public class StopwatchTimerTask extends TimerTask {
	protected int time;
	protected boolean isRunning = false;

	@Override
	public void run() {
		if (time == 10000) {
			isRunning = false;
			cancel();
		} else {
			time++;
		}
	}

}
