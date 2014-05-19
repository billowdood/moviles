package com.trucha.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.trucha.Category;
import com.trucha.R;
import com.trucha.utils.db.HostHelper;
import com.trucha.utils.webService.ServiceHandler;
import com.trucha.utils.webService.URLConstants;


public class CategoriesActivity extends ListActivity {

	private ProgressDialog pDialog;
	ArrayList<HashMap<String,String>> categories;
	HashMap<String,String> nameMap = new HashMap<String,String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);
		categories = new ArrayList<HashMap<String,String>>();
		HostHelper dbHostHelper= new HostHelper(this);
		new GetCategories().execute(dbHostHelper);
		Log.d("d","Post asynctask");
	}
	
	private class GetCategories extends AsyncTask<HostHelper,Void,Void>{
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(CategoriesActivity.this);
			pDialog.setMessage("Please wait,retrieveng data from server...");
			pDialog.setCancelable(false);
            pDialog.show();
		}
		
		@Override
		protected Void doInBackground(HostHelper... helper){
			ServiceHandler sh = new ServiceHandler();
			String url = getCompleteUrlCategoriesIndex((HostHelper)helper[0]);
			String json = sh.makeGetServiceCall(url);
			Log.d("d","Json: "+json);
			Gson gson = new Gson();
			JsonParser parser = new JsonParser();
			JsonArray jsonArray = parser.parse(json).getAsJsonArray();
			Iterator<JsonElement> it = jsonArray.iterator();
			//String category;
			Category category;
			String name;
			int id;
			while(it.hasNext()){
				Log.d("i","Creating category object");
				category = gson.fromJson(it.next(), Category.class);
				Log.d("i","Creating string for name");
				name = category.getName();
				Log.d("i","Creating int for id");
				id = category.getId();
				Log.d("i","Temporary hashmap");
				HashMap<String,String> tmp = new HashMap<String,String>();
				tmp.put("name",name);
				categories.add(tmp);
				Log.d("i","Add to hashmap id=>name");
				nameMap.put(String.valueOf(id-1),name);
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result){
			super.onPostExecute(result);
			ListAdapter adapter = 
					//(context,List<extends Map<String,?>>,layout,keys(from),to(textview id))
					new SimpleAdapter(CategoriesActivity.this,categories,android.R.layout.simple_list_item_1,new String[] {"name"},new int[] {android.R.id.text1});
			setListAdapter(adapter);
			CategoriesActivity.this.getListView().setOnItemClickListener(mMessageClickedHandler);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
		}
		
		private OnItemClickListener mMessageClickedHandler = new OnItemClickListener(){
			public void onItemClick(AdapterView parent,View v,int position,long id){
				Log.i("i", "Listener");
				Log.d("d",nameMap.get(String.valueOf(position)));
				Intent dishes = new Intent(CategoriesActivity.this,DishesActivity.class);
				dishes.putExtra("id", String.valueOf(position+1));
				Log.i("i", "Start dishes activity");
				startActivity(dishes);
			}
		};
		
		private String getCompleteUrlCategoriesIndex(HostHelper helper){
			SQLiteDatabase db = helper.getReadableDatabase();
			String query = "SELECT * FROM host";
			Cursor c = db.rawQuery(query, null);
			c.moveToFirst();
			db.close();
			return c.getString(1)+URLConstants.URL_CATEGORY_INDEX;
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.categories, menu);
		return true;
	}

}
