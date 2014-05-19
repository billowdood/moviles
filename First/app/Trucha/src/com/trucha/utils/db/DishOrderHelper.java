package com.trucha.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.trucha.utils.db.DishOrderContract.DishOrderDB;

public class DishOrderHelper extends SQLiteOpenHelper{

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "DishOrder.db";
	private static final String INT_TYPE = "INTEGER";
	private static final String SQL_CREATE_HOST = 
			"CREATE TABLE " + DishOrderDB.TABLE_NAME + " (" +
			DishOrderDB._ID + " INTEGER PRIMARY KEY," +
			DishOrderDB.COLUMN_NAME_DISH_ID + " "+INT_TYPE+","+
			DishOrderDB.COLUMN_NAME_ORDER_ID+ " "+INT_TYPE+
			" )"
			;
	private static final String SQL_DELETE_HOST = 
			"DROP TABLE IF EXISTS " + DishOrderDB.TABLE_NAME;
			;
	
	public DishOrderHelper(Context context){
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
