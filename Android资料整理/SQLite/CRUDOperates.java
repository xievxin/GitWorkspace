package com.ckjr.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 增删改查封装
 * Created by xin on 2016/2/1.
 */
public class DBUtil {

	private static SQLiteDatabase sqLiteDatabase;

	/**
	 * 增
	 * @param tableName
	 * @param nullColumnHack if(values==null) {	sql=insert into tableName(nullColumnHack) values(NULL)}
	 * @param values
	 * @return true:success  false:failed
	 */
	public static boolean doInsert(Context context, String tableName, String nullColumnHack, ContentValues values) {
		sqLiteDatabase = DBHelper.getInstance(context);
		sqLiteDatabase.insert(tableName, nullColumnHack, values);
		sqLiteDatabase.close();
		return true;
	}


	/**
	 * 查
	 * @param context
	 * @param tableName
	 * @param cols
	 * @param whereSql 不用where
	 * @param whereArgs '?'对应参数
	 * @return
	 */
	public static List<JsonObject> doFind(Context context, String tableName, String[] cols, String whereSql, String[] whereArgs) {
		if(context==null || CommonUtil.isEmpty(tableName))
			return null;
		sqLiteDatabase = DBHelper.getInstance(context);
		List<JsonObject> list = new ArrayList<JsonObject>();
		Cursor cursor = sqLiteDatabase.query(true, tableName, cols, whereSql, whereArgs, null, null, null, null);
//		Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			JsonObject jo = new JsonObject();
			for(String col:cols) {
				jo.addProperty(col, cursor.getString(cursor.getColumnIndex(col)));
			}
			list.add(jo);
			cursor.moveToNext();
		}
		sqLiteDatabase.close();
		return list;
	}

	/**
	 * 删
	 * @param context
	 * @param tableName
	 * @param whereSql
	 * @param whereArgs
	 * @return
	 */
	public static int doDel(Context context, String tableName, String whereSql, String[] whereArgs) {
		if(context==null || CommonUtil.isEmpty(tableName))
			return 0;
		int rows = 0;
		sqLiteDatabase = DBHelper.getInstance(context);
		rows = sqLiteDatabase.delete(tableName, whereSql, whereArgs);
		sqLiteDatabase.close();
		return rows;
	}

	/**
	 * 改
	 * @param context
	 * @param tableName
	 * @param values
	 * @param whereSql
	 * @param whereArgs
	 * @return
	 */
	public static int doUpdate(Context context, String tableName, ContentValues values, String whereSql, String[] whereArgs) {
		if(context==null || CommonUtil.isEmpty(tableName))
			return 0;
		int rows = 0;
		sqLiteDatabase = DBHelper.getInstance(context);
		rows = sqLiteDatabase.update(tableName, values, whereSql, whereArgs);
		sqLiteDatabase.close();
		return rows;
	}
}
