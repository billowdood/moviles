package com.trucha.utils.db;

import android.provider.BaseColumns;

public class TotalContract {
	
	public TotalContract(){}
	
	public static abstract class Total implements BaseColumns{

		public static final String TABLE_NAME = "total";
		public static final String COLUMN_NAME_PRICE = "price";
		
	}

}
