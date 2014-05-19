package com.trucha.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.trucha.R;
import com.trucha.utils.db.DatabaseHandler;
import com.trucha.utils.db.HostContract.Host;
import com.trucha.utils.db.HostHelper;
import com.trucha.utils.db.TableContract.Table;
import com.trucha.utils.db.TableHelper;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
	}

	public void saveHost(View v){
		Log.i("i","Entered saveHost");
		EditText hostEt = (EditText) this.findViewById(R.id.edt_txt_host);
		EditText tableEt = (EditText) this.findViewById(R.id.edt_txt_table_number);
		String address = hostEt.getText().toString();
		String tableNumber = tableEt.getText().toString();
		HostHelper dbHostHelper= new HostHelper(this);
		TableHelper dbTableHelper = new TableHelper(this);
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(address);
		list.add(tableNumber);
		list.add(dbHostHelper);
		list.add(dbTableHelper);
		new dbAsyncTask().execute(list);
	}
	
	private class dbAsyncTask extends AsyncTask<ArrayList<Object>,Void,Void>{
		
		@Override
		protected Void doInBackground(ArrayList<Object>... list){
			Log.i("i","Entered asynctask");
			/* Process for Host.db */
			HostHelper hostHelper = (HostHelper)list[0].get(2);
			if(DatabaseHandler.isEmpty(hostHelper,Host.TABLE_NAME)){
				Log.i("i","Host DB is empty");
				ContentValues hostValues = new ContentValues();
				hostValues.put(Host._ID, 1);
				hostValues.put(Host.COLUMN_NAME_ADDRESS,(String) list[0].get(0));
				DatabaseHandler.add((HostHelper)list[0].get(2), Host.TABLE_NAME, hostValues);
				//Cursor c = DatabaseHandler.read(hostHelper, Host.TABLE_NAME, 1);
				String[] columns = {Host._ID,Host.COLUMN_NAME_ADDRESS};
				String selection = Host._ID + " = ?";
				String[] selectionArgs = {String.valueOf(1)};
				Cursor c = DatabaseHandler.read(hostHelper, Host.TABLE_NAME, columns, selection, selectionArgs);
				Log.d("d",Host._ID+":"+c.getInt(0));
				Log.d("d",Host.COLUMN_NAME_ADDRESS+":"+c.getString(1));
			}else{
				Log.i("i","Updating values in Host");
				ContentValues hostValue = new ContentValues();
				hostValue.put(Host.COLUMN_NAME_ADDRESS,(String) list[0].get(0));
				DatabaseHandler.update((HostHelper)list[0].get(2),Host.TABLE_NAME,hostValue);
				//Cursor c = DatabaseHandler.read(hostHelper, Host.TABLE_NAME, 1);
				String[] columns = {Host._ID,Host.COLUMN_NAME_ADDRESS};
				String selection = Host._ID + " = ?";
				String[] selectionArgs = {String.valueOf(1)};
				Cursor c = DatabaseHandler.read(hostHelper, Host.TABLE_NAME, columns, selection, selectionArgs);
				Log.d("d",Host._ID+":"+c.getInt(0));
				Log.d("d",Host.COLUMN_NAME_ADDRESS+":"+c.getString(1));
			}
			/* Process for Table.db */
			TableHelper tableHelper = (TableHelper) list[0].get(3);
			if(DatabaseHandler.isEmpty(tableHelper, Table.TABLE_NAME)){
				Log.i("i","Table DB is empty");
				ContentValues tableValues = new ContentValues();
				tableValues.put(Table._ID,1);
				tableValues.put(Table.COLUMN_NAME_TABLE_NUMBER,(String) list[0].get(1));
				DatabaseHandler.add(tableHelper, Table.TABLE_NAME, tableValues);
				String[] columns = {Table._ID,Table.COLUMN_NAME_TABLE_NUMBER};
				String selection = Table._ID + " = ?";
				String[] selectionArgs = {String.valueOf(1)};
				Cursor c = DatabaseHandler.read(tableHelper, Table.TABLE_NAME, columns, selection, selectionArgs);
				Log.d("d",Table._ID+":"+c.getInt(0));
				Log.d("d",Table.COLUMN_NAME_TABLE_NUMBER+":"+c.getString(1));
			}
			else{
				Log.i("i","Updating values in Table");
				ContentValues tableValue = new ContentValues();
				tableValue.put(Table.COLUMN_NAME_TABLE_NUMBER,(String) list[0].get(1));
				DatabaseHandler.update(tableHelper,Table.TABLE_NAME,tableValue);
				String[] columns = {Table._ID,Table.COLUMN_NAME_TABLE_NUMBER};
				String selection = Table._ID + " = ?";
				String[] selectionArgs = {String.valueOf(1)};
				Cursor c = DatabaseHandler.read(tableHelper, Table.TABLE_NAME, columns, selection, selectionArgs);
				Log.d("d",Table._ID+":"+c.getInt(0));
				Log.d("d",Table.COLUMN_NAME_TABLE_NUMBER+":"+c.getString(1));
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result){
			super.onPostExecute(result);
			Context context = getApplicationContext();
			CharSequence text = "Data was saved :)";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			finish();
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

}
