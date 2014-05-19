package com.trucha.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.trucha.Dish;
import com.trucha.DishOrder;
import com.trucha.Order;
import com.trucha.R;
import com.trucha.utils.db.DatabaseHandler;
import com.trucha.utils.db.DishOrderContract.DishOrderDB;
import com.trucha.utils.db.DishOrderHelper;
import com.trucha.utils.db.HostContract.Host;
import com.trucha.utils.db.HostHelper;
import com.trucha.utils.db.TableContract.Table;
import com.trucha.utils.db.TableHelper;
import com.trucha.utils.db.TotalContract.Total;
import com.trucha.utils.db.TotalHelper;
import com.trucha.utils.webService.ServiceHandler;
import com.trucha.utils.webService.URLConstants;

public class ShowDishActivity extends Activity {
	
	private ProgressDialog pDialog;
	private float dishPrice;
	String dishId;
	Handler handler = new Handler(){
		
		@Override
		public void handleMessage(Message msg){
			Bundle bundle = msg.getData();
			TextView name = (TextView) findViewById(R.id.txt_view_dish_name);
			TextView description = (TextView) findViewById(R.id.txt_view_description);
			TextView price = (TextView) findViewById(R.id.txt_view_price);
			ImageView img = (ImageView) findViewById(R.id.img_dish);
			img.setImageResource(R.drawable.trucha);
			name.setText(bundle.getString("name"));
			description.setText(bundle.getString("description"));
			price.setText("$"+String.valueOf(bundle.getFloat("price")));
			dishPrice = bundle.getFloat("price");
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_dish);
		Intent dishesActivity = getIntent();
		dishId = dishesActivity.getStringExtra("dishId");
		HostHelper helper = new HostHelper(this);
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(helper);
		list.add(dishId);
		Log.d("d","Id for the url"+dishId);
		new GetDish().execute(list);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_dish, menu);
		return true;
	}

	public void addDishToOrder(View v){
		Log.i("i","addDishToOrder");
		DishOrderHelper doHelper = new DishOrderHelper(ShowDishActivity.this);
		TotalHelper totalHelper = new TotalHelper(ShowDishActivity.this);
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(doHelper);
		list.add(totalHelper);
		new AddDishOrder().execute(list);
	}
	
	public void makeOrder(View v){
		Log.i("i","makeOrder");
		DishOrderHelper doHelper = new DishOrderHelper(ShowDishActivity.this);
		HostHelper hostHelper = new HostHelper(ShowDishActivity.this);
		TableHelper tableHelper = new TableHelper(ShowDishActivity.this);
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(doHelper);
		list.add(hostHelper);
		list.add(tableHelper);
		new MakeOrder().execute(list);
	}
	
	private class GetDish extends AsyncTask<ArrayList,Void,Dish>{

		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(ShowDishActivity.this);
			pDialog.setMessage("Please wait,retrieveng data from server...");
			pDialog.setCancelable(false);
            pDialog.show();
		}
		
		@Override
		protected Dish doInBackground(ArrayList... params) {
			Gson gson = new Gson();
			String id = (String) params[0].get(1);
			HostHelper helper = (HostHelper) params[0].get(0);
			ServiceHandler sh = new ServiceHandler();
			String url = getUrl(helper);
			String json = sh.makeGetServiceCall(url, id);
			Dish dish = gson.fromJson(json, Dish.class);
			return dish;
		}
		
		private String getUrl(HostHelper helper){
			//Cursor c = DatabaseHandler.read(helper, Host.TABLE_NAME, 1);
			String[] columns = {Host._ID,Host.COLUMN_NAME_ADDRESS};
			String selection = Host._ID + " = ?";
			String[] selectionArgs = {String.valueOf(1)};
			Cursor c = DatabaseHandler.read(helper, Host.TABLE_NAME, columns, selection, selectionArgs);
			String url = c.getString(1)+URLConstants.URL_DISH;
			return url;
		}
		
		@Override
		protected void onPostExecute(Dish result){
			super.onPostExecute(result);
			Log.d("d","Dish name: "+result.getName());
			Message msg = handler.obtainMessage();
			Bundle data = new Bundle();
			data.putString("name", result.getName());
			data.putString("description",result.getDescription());
			data.putFloat("price", result.getPrice());
			msg.setData(data);
			handler.sendMessage(msg);
			if (pDialog.isShowing())
                pDialog.dismiss();
                
		}
		
	}

	private class AddDishOrder extends AsyncTask<ArrayList,Void,Void>{

		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(ShowDishActivity.this);
			pDialog.setMessage("Please wait,adding dish to order...");
			pDialog.setCancelable(false);
            pDialog.show();
		}
		
		@Override
		protected Void doInBackground(ArrayList... list) {
			DishOrderHelper helper = (DishOrderHelper)list[0].get(0);
			if(DatabaseHandler.isEmpty(helper,DishOrderDB.TABLE_NAME)){
				Log.i("i","DishOrder DB is empty");
				ContentValues values = new ContentValues();
				values.put(DishOrderDB._ID, 1);
				values.put(DishOrderDB.COLUMN_NAME_DISH_ID,Integer.parseInt(dishId));
				values.put(DishOrderDB.COLUMN_NAME_ORDER_ID, 1);
				DatabaseHandler.add(helper,DishOrderDB.TABLE_NAME,values);
				String[] columns = {DishOrderDB._ID,DishOrderDB.COLUMN_NAME_DISH_ID,DishOrderDB.COLUMN_NAME_ORDER_ID};
				String selection = DishOrderDB._ID + " = ?";
				String[] selectionArgs = {String.valueOf(1)};
				Cursor c = DatabaseHandler.read(helper, DishOrderDB.TABLE_NAME, columns, selection, selectionArgs);
				Log.d("d",DishOrderDB._ID+":"+c.getInt(0));
				Log.d("d",DishOrderDB.COLUMN_NAME_DISH_ID+":"+c.getString(1));
				Log.d("d",DishOrderDB.COLUMN_NAME_ORDER_ID+":"+c.getString(2));
			}else{
				Log.i("i","Add values to DishOrder");
				Cursor lastRow = DatabaseHandler.lastRow(helper, DishOrderDB.TABLE_NAME, DishOrderDB._ID);
				int id = lastRow.getInt(0) + 1;
				ContentValues values = new ContentValues();
				values.put(DishOrderDB._ID, id);
				values.put(DishOrderDB.COLUMN_NAME_DISH_ID,Integer.parseInt(dishId));
				values.put(DishOrderDB.COLUMN_NAME_ORDER_ID, 1);
				DatabaseHandler.add(helper,DishOrderDB.TABLE_NAME,values);
				String[] columns = {DishOrderDB._ID,DishOrderDB.COLUMN_NAME_DISH_ID,DishOrderDB.COLUMN_NAME_ORDER_ID};
				String selection = DishOrderDB._ID + " = ?";
				String[] selectionArgs = {String.valueOf(id)};
				Cursor c = DatabaseHandler.read(helper, DishOrderDB.TABLE_NAME, columns, selection, selectionArgs);
				Log.d("d",DishOrderDB._ID+":"+c.getInt(0));
				Log.d("d",DishOrderDB.COLUMN_NAME_DISH_ID+":"+c.getString(1));
				Log.d("d",DishOrderDB.COLUMN_NAME_ORDER_ID+":"+c.getString(2));
			}
			//Create total of the order
			float total = getLastTotal((TotalHelper)list[0].get(1)) + dishPrice;
			Log.d("d","Total: "+total);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result){
			super.onPostExecute(result);
			if (pDialog.isShowing()){
                pDialog.dismiss();
                Context context = getApplicationContext();
				CharSequence text = "Dish added to order";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
		}
		
	
		private float getLastTotal(SQLiteOpenHelper helper){
			Cursor c = DatabaseHandler.allRows(helper, Total.TABLE_NAME);
			if(c.getCount() == 0){
				return 0;
			}else{
				return c.getFloat(1);
			}
		}
	}
	
	private class MakeOrder extends AsyncTask<ArrayList,Void,Void>{

		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(ShowDishActivity.this);
			pDialog.setMessage("Please wait,making order...");
			pDialog.setCancelable(false);
            pDialog.show();
		}
		
		@Override
		protected Void doInBackground(ArrayList... list) {
			Log.i("i","MakeOrder asynctask");
			ArrayList<Object> dataToSend = new ArrayList<Object>();
			ServiceHandler sh = new ServiceHandler();
			HostHelper hostHelper = (HostHelper) list[0].get(1);
			DishOrderHelper doHelper = (DishOrderHelper)list[0].get(0);
			String[] urls = {getNewOrderUrl(hostHelper),getNewDishOrderUrl(hostHelper)};
			int id = getLastOrderId(hostHelper);
			int table_id = getTableId((TableHelper) list[0].get(2));
			// TO-DO el total se debe ir sacando cada que se agrega un platillo
			float total = 0;
			boolean is_payed = false;
			Log.i("i","Create order");
			Order order = new Order(id,table_id,total,is_payed);
			dataToSend.add(order);
			Log.i("i","Query for all the row in DishOrder table");
			Cursor dishOrders = DatabaseHandler.allRows(doHelper, DishOrderDB.TABLE_NAME);
			Log.d("d","dishOrders cursor: "+dishOrders.getCount());
			Log.d("d","Total rows: "+dishOrders.getCount());
			DishOrder[] dishOrder = new DishOrder[dishOrders.getCount()];
			dishOrders.moveToPrevious();
			String dish_id,order_id;
			int lastIdDishOrder = getLastIdDishOrder(hostHelper),count = 0;
			Log.d("d","Last DishOrder id: "+lastIdDishOrder);
			Log.i("i","Iterate over all the dishOrders");
			while(dishOrders.moveToNext()){
				dish_id = dishOrders.getString(1);
				order_id = dishOrders.getString(2) ;
				dishOrder[count] = new DishOrder(lastIdDishOrder,Integer.parseInt(dish_id),Integer.parseInt(order_id));
				Log.d("d","id: "+dishOrder[count].getId());
				Log.d("d","order_id: "+dishOrder[count].getOrderId());
				Log.d("d","dish_id: "+dishOrder[count].getDishId());
				lastIdDishOrder++;	
				count++;
			}
			dataToSend.add(dishOrder);
			sh.makePostServiceCall(urls, dataToSend);
			return null;
		}
		
		private String getNewOrderUrl(SQLiteOpenHelper helper){
			String tableName = Host.TABLE_NAME;
			String[] columns = {Host.COLUMN_NAME_ADDRESS};
			String selection = Host._ID + " = ?";
			String[] selectionArgs = {String.valueOf(1)};
			String url = 
					DatabaseHandler.read(helper, tableName, columns, selection, selectionArgs).getString(0)+
					URLConstants.URL_CREATE_ORDER;
			return url;
		}
		
		private String getNewDishOrderUrl(SQLiteOpenHelper helper){
			String tableName = Host.TABLE_NAME;
			String[] columns = {Host.COLUMN_NAME_ADDRESS};
			String selection = Host._ID + " = ?";
			String[] selectionArgs = {String.valueOf(1)};
			String url = 
					DatabaseHandler.read(helper, tableName, columns, selection, selectionArgs).getString(0)+
					URLConstants.URL_CREATE_DISH_ORDER;
			return url;
		}
		
		private int getLastOrderId(SQLiteOpenHelper helper){
			Gson gson = new Gson();
			ServiceHandler sh = new ServiceHandler();
			String[] columns = {Host.COLUMN_NAME_ADDRESS};
			String selection = Host._ID + " = ?";
			String[] selectionArgs = {String.valueOf(1)};
			String url = 
					DatabaseHandler.read(helper, Host.TABLE_NAME, columns, selection, selectionArgs).getString(0) +
					URLConstants.URL_LAST_ORDER
					;
			int id = gson.fromJson(sh.makeGetServiceCall(url),Order.class).getId() + 1;
			return id;
		}
		
		private int getTableId(SQLiteOpenHelper helper){
			String tableName = Table.TABLE_NAME;
			String[] columns = {Table.COLUMN_NAME_TABLE_NUMBER};
			String selection = Table._ID + " = ?";
			String[] selectionArgs = {String.valueOf(1)};
			int tableId = DatabaseHandler.read(helper, tableName, columns, selection, selectionArgs).getInt(0);
			return tableId;
		}
		
		private int getLastIdDishOrder(SQLiteOpenHelper helper){
			ServiceHandler sh = new ServiceHandler();
			Gson gson = new Gson();
			SQLiteDatabase db = helper.getReadableDatabase();
			String[] columns = {Host.COLUMN_NAME_ADDRESS};
			String selection = Host._ID + " = ?";
			String[] selectionArgs = {String.valueOf(1)};
			String url = 
					DatabaseHandler.read(helper, Host.TABLE_NAME, columns, selection, selectionArgs).getString(0) +
					URLConstants.URL_LAST_DISH_ORDER
					;
			int id = gson.fromJson(sh.makeGetServiceCall(url),DishOrder.class).getId();
			Log.d("d","Url: "+url);
			db.close();
			//id to return is the next one
			return id + 1;
		}
		
		@Override
		protected void onPostExecute(Void result){
			super.onPostExecute(result);
			if (pDialog.isShowing())
                pDialog.dismiss();
		}
		
	}

}

