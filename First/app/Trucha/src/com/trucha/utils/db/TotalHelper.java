package com.trucha.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.trucha.utils.db.TotalContract.Total;

public class TotalHelper extends SQLiteOpenHelper{

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Total.db";
	private static final String FLOAT_TYPE = "REAL";
	private static final String SQL_CREATE_HOST = 
			"CREATE TABLE " + Total.TABLE_NAME + " (" +
			Total._ID + " INTEGER PRIMARY KEY," +
			Total.COLUMN_NAME_PRICE+ " "+FLOAT_TYPE+
			" )"
			;
	private static final String SQL_DELETE_HOST = 
			"DROP TABLE IF EXISTS " + Total.TABLE_NAME;
			;
	
	public TotalHelper(Context context){
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
