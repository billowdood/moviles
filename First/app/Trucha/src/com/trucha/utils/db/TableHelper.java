package com.trucha.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.trucha.utils.db.TableContract.Table;

public class TableHelper extends SQLiteOpenHelper{
	
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Table.db";
	private static final String TEXT_TYPE = "TEXT";
	private static final String SQL_CREATE_TABLE = 
			"CREATE TABLE " + Table.TABLE_NAME + " (" +
			Table._ID + " INTEGER PRIMARY KEY," +
			Table.COLUMN_NAME_TABLE_NUMBER + " "+ TEXT_TYPE +
			" )"
			;
	private static final String SQL_DELETE_TABLE = 
			"DROP TABLE IF EXISTS " + Table.TABLE_NAME;
			;
	
	public TableHelper(Context context){
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}
	
	public void onCreate(SQLiteDatabase db){
		Log.i("i","Creating database with sql: "+SQL_CREATE_TABLE);
		db.execSQL(SQL_CREATE_TABLE);
	}
	
	public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
		/*
		 * Not the correct implementation because it drops the table
		 * and then create a new one
		 */
		Log.i("i","Updating database with sql: "+SQL_DELETE_TABLE);
		db.execSQL(SQL_DELETE_TABLE);
		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db,int oldVersion,int newVersion){
		onUpgrade(db,oldVersion,newVersion);
	}

}
