package com.example.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteUtil extends SQLiteOpenHelper{


	public SQLiteUtil(Context context) {
		super(context, "test_notepad", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table notepad(id integer PRIMARY KEY autoincrement, title text, content text, time text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}

}
