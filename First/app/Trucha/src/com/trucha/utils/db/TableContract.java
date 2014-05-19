package com.trucha.utils.db;

import android.provider.BaseColumns;

public final class TableContract {
	
	public TableContract() {}
	
	public static abstract class Table implements BaseColumns{
		
		public static final String TABLE_NAME = "tabl";
		public static final String COLUMN_NAME_TABLE_NUMBER = "tableNumber";
	}

}
