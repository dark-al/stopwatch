package com.antonbelka.stopwatch;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	protected static final String TABLE_NAME = "results";
	protected static final String COLUMN_NAME_TIME = "time";
	private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "stopwatch.sqlite";
	private static final String DB_CREATE_QUERY = "CREATE TABLE " + TABLE_NAME
			+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, " + "time TEXT NOT NULL,"
			+ "comment TEXT);";
	private static final String DB_SELECT_TIME_QUERY = "SELECT time FROM " + TABLE_NAME;
	private static final String DB_DELETE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
	
	private SQLiteDatabase db;
        
	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		db = this.getReadableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DB_CREATE_QUERY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DB_DELETE_QUERY);
        onCreate(db);		
	}
	
	public void addTime(String time) {
		ContentValues values = new ContentValues();
		values.put(DbHelper.COLUMN_NAME_TIME, time);
		db.insert(DbHelper.TABLE_NAME, "time", values);
	}
	
	public ArrayList<String> getTime() {
		ArrayList<String> timerValues = new ArrayList<String>();
		Cursor cursor = db.rawQuery(DB_SELECT_TIME_QUERY, null);

	    if (cursor.moveToFirst()) {
	        do {
	            timerValues.add(cursor.getString(0));
	        } while (cursor.moveToNext());
	    }
	    
	    return timerValues;
	}
	
}
