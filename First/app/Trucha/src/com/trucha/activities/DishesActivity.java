package com.trucha.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.trucha.Dish;
import com.trucha.R;
import com.trucha.utils.db.DatabaseHandler;
import com.trucha.utils.db.HostHelper;
import com.trucha.utils.db.HostContract.Host;
import com.trucha.utils.webService.ServiceHandler;
import com.trucha.utils.webService.URLConstants;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DishesActivity extends Activity {

	private ProgressDialog pDialog;
	HashMap<String,String> positionToId;
	String[] names;
	Handler handler = new Handler(){
		
		@Override
		public void handleMessage(Message msg){
			Bundle bundle = msg.getData();
			String[] names = bundle.getStringArray("names");
			ArrayAdapter adapter = new ArrayAdapter<String>(DishesActivity.this,R.layout.list_item,names);
			ListView listView = (ListView) findViewById(R.id.lst_view_dishes);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(mMessageClickedHandler); 
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dishes);
		positionToId = new HashMap<String,String>();
		Intent categoryIntent = getIntent();
		String dishId = categoryIntent.getStringExtra("id");
		HostHelper helper = new HostHelper(this);
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(dishId);
		list.add(helper);
		new GetDishes().execute(list);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dishes, menu);
		return true;
	}

	public void makeOrder(View v){
		Log.d("d","Clicked :v");
	}
	
	private OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
	    public void onItemClick(AdapterView parent, View v, int position, long id) {
	    	Log.i("i","Clicked");
	    	String idToSend = positionToId.get(String.valueOf(position));
	    	Log.d("d","position: "+position+" id for the url: "+idToSend);
	    	Intent showDishActivity = new Intent(DishesActivity.this,ShowDishActivity.class);
	    	showDishActivity.putExtra("dishId", idToSend);
	    	startActivity(showDishActivity);
	    }
	};
	
	private class GetDishes extends AsyncTask<ArrayList,Void,String[]>{
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(DishesActivity.this);
			pDialog.setMessage("Please wait,retrieveng data from server...");
			pDialog.setCancelable(false);
            pDialog.show();
		}
		
		@Override
		protected String[] doInBackground(ArrayList... list){
			String url = getCompleteUrl((HostHelper) list[0].get(1));
			ServiceHandler sh = new ServiceHandler();
			String json = sh.makeGetServiceCall(url,(String) list[0].get(0));
			Gson gson = new Gson();
			JsonParser parser = new JsonParser();
			JsonArray array = parser.parse(json).getAsJsonArray();
			names = new String[array.size()];
			Iterator<JsonElement> i = array.iterator();
			Dish dish;
			String name,id;
			int pos = 0;
			while(i.hasNext()){
				dish = gson.fromJson(i.next(), Dish.class);
				name = dish.getName();
				id = String.valueOf(dish.getId());
				Log.d("d","Name: "+name+" id: "+id);
				positionToId.put(String.valueOf(pos), id);
				names[pos] = name;
				pos++;
			}
			return names;
		}
		
		@Override
		protected void onPostExecute(String[] result){
			super.onPostExecute(result);
			Message msg = handler.obtainMessage();
			Bundle data = new Bundle();
			data.putStringArray("names", result);
			msg.setData(data);
			handler.sendMessage(msg);
			 // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
		}
		
		private String getCompleteUrl(HostHelper helper){
			//Cursor c = DatabaseHandler.read(helper, Host.TABLE_NAME, 1);
			String[] columns = {Host._ID,Host.COLUMN_NAME_ADDRESS};
			String selection = Host._ID + " = ?";
			String[] selectionArgs = {String.valueOf(1)};
			Cursor c = DatabaseHandler.read(helper, Host.TABLE_NAME, columns, selection, selectionArgs);
			String url = c.getString(1)+URLConstants.URL_FOR_CATEGORY;
			return url;
		}
		
	}

}
