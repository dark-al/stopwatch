package com.antonbelka.stopwatch;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "stopwatch.sqlite";
	protected static final String TABLE_NAME = "results";
	protected static final String KEY_TIME = "time";

	private SQLiteDatabase db;

	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		db = this.getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NAME
				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "time TEXT NOT NULL)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

	public void addTime(String time) {
		ContentValues values = new ContentValues();
		values.put(DbHelper.KEY_TIME, time);
		db.insert(DbHelper.TABLE_NAME, KEY_TIME, values);
	}

	public void deleteTime(String time) {
		db.delete(DbHelper.TABLE_NAME, KEY_TIME + "='" + time + "'", null);
	}

	public ArrayList<String> getTime() {
		ArrayList<String> timerValues = new ArrayList<String>();
		Cursor cursor = db.rawQuery("SELECT time FROM " + TABLE_NAME, null);

		if (cursor.moveToFirst()) {
			do {
				timerValues.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}

		return timerValues;
	}

}
