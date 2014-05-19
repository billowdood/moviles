package com.trucha.utils.db;

import android.provider.BaseColumns;

public final class HostContract {

	public HostContract() {}
	
	public static abstract class Host implements BaseColumns{
		
		public static final String TABLE_NAME = "host";
		public static final String COLUMN_NAME_ADDRESS = "address";
		
	}
}
