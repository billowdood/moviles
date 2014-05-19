package com.trucha.utils.db;

import android.provider.BaseColumns;

public class DishOrderContract {
	
	public DishOrderContract(){}
	
	public static abstract class DishOrderDB implements BaseColumns{
		
		public static final String TABLE_NAME = "dishorder";
		public static final String COLUMN_NAME_DISH_ID = "dish_id";
		public static final String COLUMN_NAME_ORDER_ID = "order_id";
		
	}

}
