package com.trucha.utils.db;


import com.trucha.utils.db.HostContract.Host;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HostHelper extends SQLiteOpenHelper{
	
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Host.db";
	private static final String TEXT_TYPE = "TEXT";
	private static final String SQL_CREATE_HOST = 
			"CREATE TABLE " + Host.TABLE_NAME + " (" +
			Host._ID + " INTEGER PRIMARY KEY," +
			Host.COLUMN_NAME_ADDRESS + " "+ TEXT_TYPE +
			" )"
			;
	private static final String SQL_DELETE_HOST = 
			"DROP TABLE IF EXISTS " + Host.TABLE_NAME;
			;
	
	public HostHelper(Context context){
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}
	
	public void onCreate(SQLiteDatabase db){
		Log.i("i","Creating database with sql: "+SQL_CREATE_HOST);
		db.execSQL(SQL_CREATE_HOST);
	}
	
	public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
		/*
		 * Not the correct implementation because it drops the table
		 * and then create a new one
		 */
		Log.i("i","Updating database with sql: "+SQL_DELETE_HOST);
		db.execSQL(SQL_DELETE_HOST);
		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db,int oldVersion,int newVersion){
		onUpgrade(db,oldVersion,newVersion);
	}
	
}
