package com.trucha.utils.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.trucha.utils.db.HostContract.Host;

public class DatabaseHandler {

	public static long add(SQLiteOpenHelper helper,String tableName,ContentValues values){
		SQLiteDatabase db = helper.getWritableDatabase();
		Log.i("i","Create operation");
		long id = db.insert(tableName, null, values);
		db.close();
		return id;
	}
	
	public static Cursor read(SQLiteOpenHelper helper,String tableName,String[] columns,
			String selection,String[] selectionArgs){
		Log.i("i","Read operation");
		SQLiteDatabase db = helper.getReadableDatabase();
		Log.d("d","Columns: "+columns);
		Log.d("d","Selection: "+selection);
		Log.d("d","SelectionArgs: "+selectionArgs);
		Cursor cursor = db.query(tableName, columns, selection, selectionArgs, null, null, null);
		// index -1 requested,with a size of 0
		cursor.moveToFirst();
		Log.i("i","Database read");
		db.close();
		return cursor;
	}
	
	public static int update(SQLiteOpenHelper helper,String tableName,ContentValues values){
		Log.i("i","Update operation");
		SQLiteDatabase db = helper.getWritableDatabase();
		String selection = Host._ID + " = ?";
		Log.d("d","Selection: "+selection);
		String[] selectionArgs = {String.valueOf(1)};
		int count = 
				db.update(tableName, values, selection, selectionArgs);
		db.close();
		return count;
	}
	
	public static boolean isEmpty(SQLiteOpenHelper helper,String tableName){
		SQLiteDatabase db = helper.getReadableDatabase();
		String query = "SELECT * FROM "+tableName;
		Log.d("i","Select query: "+query);
		Cursor c = db.rawQuery(query, null);
		if(c.getCount() == 0){
			db.close();
			return true;
		}
		else{
			db.close();
			return false;
		}
	}
	
	public static Cursor lastRow(SQLiteOpenHelper helper,String tableName,String orderBy){
		SQLiteDatabase db = helper.getReadableDatabase();
		String query = "SELECT * FROM "+tableName+" ORDER BY "+orderBy+" DESC LIMIT 1";
		Log.d("d","lastRow query: "+query);
		Cursor cursor = db.rawQuery(query, null);
		cursor.moveToFirst();
		db.close();
		return cursor;
	}
	
	public static Cursor allRows(SQLiteOpenHelper helper,String tableName){
		SQLiteDatabase db = helper.getReadableDatabase();
		String query = "SELECT * FROM "+tableName;
		Log.d("d","allRows query: "+query);
		Cursor cursor = db.rawQuery(query,null);
		cursor.moveToFirst();
		db.close();
		return cursor;
	}
	
}
