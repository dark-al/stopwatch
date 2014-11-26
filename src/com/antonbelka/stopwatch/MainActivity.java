package com.antonbelka.stopwatch;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private int key;
	private TextView infoTextView;
	private ListView swListView;
	private ArrayAdapter<String> swAdapter;
	private ArrayList<String> swArrayList;
	private AlertDialog dialogSettings, dialogStopwatch;
	private Vibrator vibrator;
	private StopwatchTimer swTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		infoTextView = (TextView) findViewById(R.id.info);
		swListView = (ListView) findViewById(R.id.list);

		swArrayList = new ArrayList<String>();
		swAdapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_list_item_1, swArrayList);
		swListView.setAdapter(swAdapter);
		swTimer = new StopwatchTimer();

		infoTextView.setText(R.string.info_start);
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

		createDialogSettings();
		createDialogStopwatch();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			dialogSettings.show();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == key) {

			if (!swTimer.isRunning()) {
				if (swTimer.getTime() == 0) {
					vibrator.vibrate(500);
					swTimer.start();
					infoTextView.setText(R.string.info_stopwatch_started);
				} else {
					dialogStopwatch.show();
				}
			} else {
				if (infoTextView.getVisibility() == View.VISIBLE) {
					infoTextView.setVisibility(View.INVISIBLE);
				}
				swAdapter.add(String.valueOf(swTimer.getTime()));
			}

			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == key) {
			return true;
		}

		return super.onKeyUp(keyCode, event);
	}

	private void createDialogSettings() {
		dialogSettings = new AlertDialog.Builder(this)
				.setTitle(R.string.dialog_settings)
				.setMessage(R.string.dialog_settings_message)
				.setNegativeButton(R.string.dialog_cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						}).create();

		dialogSettings.setOnKeyListener(new Dialog.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface arg0, int keyCode,
					KeyEvent event) {
				key = keyCode;
				dialogSettings.dismiss();

				return true;
			}
		});
	}

	private void createDialogStopwatch() {
		dialogStopwatch = new AlertDialog.Builder(this)
				.setTitle(R.string.dialog_stopwatch)
				.setMessage(R.string.dialog_stopwatch_message)
				.setPositiveButton(R.string.dialog_yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								swAdapter.clear();
								vibrator.vibrate(500);
								swTimer.start();
								infoTextView.setVisibility(View.VISIBLE);
							}
						})
				.setNegativeButton(R.string.dialog_no,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						}).create();

		dialogStopwatch.setOnKeyListener(new Dialog.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface arg0, int keyCode,
					KeyEvent event) {
				return true;
			}
		});

	}
}
