package com.antonbelka.stopwatch;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ResultsActivity extends Activity {
	private ArrayAdapter<String> resultsAdapter;
	private ArrayList<String> timeValues, resultsArrayList;
	private AlertDialog dialogDelete;
	private ListView resultsListView;
	private TextView infoTextView;
	private DbHelper mDbHelper;
	private String selectedResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);

		infoTextView = (TextView) findViewById(R.id.infoResults);
		infoTextView.setText(R.string.info_empty_results);

		mDbHelper = new DbHelper(this);
		resultsListView = (ListView) findViewById(R.id.listResults);

		resultsArrayList = new ArrayList<String>();
		resultsAdapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_list_item_1, resultsArrayList);
		resultsListView.setAdapter(resultsAdapter);
		resultsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectedResult = (String) (resultsListView
						.getItemAtPosition(position));
				dialogDelete.show();
			}

		});

		timeValues = mDbHelper.getTime();
		if (!timeValues.isEmpty()) {
			for (String time : timeValues) {
				resultsAdapter.add(time);
			}
			if (infoTextView.getVisibility() == View.VISIBLE) {
				infoTextView.setVisibility(View.INVISIBLE);
			}
		}

		createDialogDelete();
	}

	private void createDialogDelete() {
		dialogDelete = new AlertDialog.Builder(this)
				.setTitle(R.string.dialog_delete)
				.setMessage(R.string.dialog_delete_message)
				.setPositiveButton(R.string.dialog_yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								mDbHelper.deleteTime(selectedResult);
								resultsAdapter.remove(selectedResult);
								if (resultsAdapter.isEmpty()) {
									if (infoTextView.getVisibility() == View.INVISIBLE) {
										infoTextView
												.setVisibility(View.VISIBLE);
									}
								}
							}
						})
				.setNegativeButton(R.string.dialog_no,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						}).create();

	}

}
