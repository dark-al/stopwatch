package com.antonbelka.stopwatch;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ResultsActivity extends Activity {
	private ArrayAdapter<String> resultsAdapter;
	private ArrayList<String> timeValues, resultsArrayList;
	private ListView resultsListView;
	private DbHelper mDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		
		mDbHelper = new DbHelper(this);
		resultsListView = (ListView) findViewById(R.id.resultsList);
		
		resultsArrayList = new ArrayList<String>();
		resultsAdapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_list_item_1, resultsArrayList);
		resultsListView.setAdapter(resultsAdapter);
		
		timeValues = mDbHelper.getTime();
		for (String time : timeValues) {
			resultsAdapter.add(time);
		}
	
	}

}
