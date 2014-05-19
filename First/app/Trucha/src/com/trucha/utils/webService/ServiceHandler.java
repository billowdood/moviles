package com.trucha.utils.webService;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.trucha.DishOrder;
import com.trucha.Order;

import android.util.Log;
 
public class ServiceHandler {
 
    static String response = null;
 
    public ServiceHandler() {
 
    }
 
    public String makeGetServiceCall(String url){
    	return this.makeGetServiceCall(url,null);
    }
    
    public String makeGetServiceCall(String url,String params){
    	try{
    	Log.i("i", "Starting makeGetServiceCall");
    	DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = null;
        HttpEntity httpEntity = null;
        Log.i("i", "Starting if for params");
        if(params!=null){
    		url += "/"+params;
    	}
    	Log.d("d", url);
    	Log.i("i","Starting get");
    	HttpGet httpGet = new HttpGet(url);
    	Log.i("i","Executing get");
    	httpResponse = httpClient.execute(httpGet);
    	httpEntity = httpResponse.getEntity();
    	response = EntityUtils.toString(httpEntity);
		Log.i("i","Finished get");
		Log.d("d","Value response: "+response);
    	}catch(UnsupportedEncodingException e){
    		e.printStackTrace();
    	}catch(ClientProtocolException e){
    		e.printStackTrace();
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    	return response;
    }
    
    public String makePostServiceCall(String url,Order order){
    	try{
    		Log.i("i","Starting makePostServiceCall");
    		Log.i("i","Create gson object");
    		Gson gson = new Gson();
    		Log.i("i","Create url");
    		URL address = new URL(url);
    		Log.i("i","Create connection");
    		HttpURLConnection conn = (HttpURLConnection) address.openConnection();
    		conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			Log.i("i","Create json");
			String pJson = gson.toJson(order);
			JsonObject orderJsonObject = new JsonObject();
			JsonParser parser = new JsonParser();
			Log.i("i","Parse json");
			orderJsonObject.add("order",parser.parse(pJson).getAsJsonObject());
			Log.i("i","Outputsream");
			OutputStream os = conn.getOutputStream();
			os.write(gson.toJson(orderJsonObject).getBytes("utf-8"));
			os.flush();
			Log.i("i","Disconnect");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			String output;
			while((output = br.readLine()) != null){
				Log.i("i",output);
				response = output;
			}
			conn.disconnect();
    	}catch(MalformedURLException e){
    		e.printStackTrace();
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    	return response;
    }

    public String makePostServiceCall(String url,JsonObject jsonO){
    	try{
    		Log.i("i","Starting makePostServiceCall");
    		Log.i("i","Create gson object");
    		Gson gson = new Gson();
    		Log.i("i","Create url");
    		URL address = new URL(url);
    		Log.i("i","Create connection");
    		HttpURLConnection conn = (HttpURLConnection) address.openConnection();
    		conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("POSTAttempt", "application/json");
			Log.i("i","Outputsream");
			OutputStream os = conn.getOutputStream();
			//os.write(gson.toJson(jsonO).getBytes("utf-8"));
			os.write(gson.toJson(jsonO).toString().getBytes());
			os.flush();
			Log.i("i","Disconnect");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			String output;
			while((output = br.readLine()) != null){
				Log.i("i",output);
				response = output;
			}
			conn.disconnect();
    	}catch(MalformedURLException e){
    		e.printStackTrace();
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    	return response;
    }
    
    public String makePostServiceCall(String[] urls,ArrayList<Object> objects){
    	this.makePostOrder(urls[0],(Order) objects.get(0));
    	this.makePostDishOrder(urls[1],(DishOrder[]) objects.get(1));
    	return response;
    }
    
    private void makePostOrder(String url,Order order){
    	try{
    		Log.i("i","Starting makePostOrder");
    		Log.i("i","Create gson object");
    		Gson gson = new Gson();
    		Log.i("i","Create json object");
    		JsonObject object = new JsonObject();
    		JsonParser parser = new JsonParser();
    		object.add("order",parser.parse(gson.toJson(order)).getAsJsonObject());
    		Log.i("i","Create url");
    		URL address = new URL(url);
    		Log.i("i","Create connection");
    		HttpURLConnection conn = (HttpURLConnection) address.openConnection();
    		conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("POSTAttempt", "application/json");
			Log.i("i","Outputsream");
			OutputStream os = conn.getOutputStream();
			os.write(gson.toJson(object).toString().getBytes());
			os.flush();
			Log.i("i","Disconnect");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			String output;
			while((output = br.readLine()) != null){
				Log.i("i",output);
				response = output;
			}
			conn.disconnect();
    	}catch(MalformedURLException e){
    		e.printStackTrace();
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }

    private void makePostDishOrder(String url,DishOrder[] dishOrder){
    	try{
    		Log.i("i","Starting makePostDishOrder");
    		Log.i("i","Create gson object");
    		Gson gson = new Gson();
    		JsonObject obj = new JsonObject();
    		JsonParser parser = new JsonParser();
    		Log.i("i","Create url");
    		URL address = new URL(url);
    		Log.i("i","Create connection");
    		HttpURLConnection conn = (HttpURLConnection) address.openConnection();
    		conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("POSTAttempt", "application/json");
			Log.i("i","Outputsream");
			OutputStream os = conn.getOutputStream();
			for(int i = 0;i < dishOrder.length; i++){
				obj.add("dish_order",parser.parse(gson.toJson(dishOrder[i])).getAsJsonObject());
				os.write(gson.toJson(obj).getBytes());
				os.flush();
				obj.remove("dish_order");				
			}
			Log.i("i","Disconnect");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			String output;
			while((output = br.readLine()) != null){
				Log.i("i",output);
				response = output;
			}
			conn.disconnect();
    	}catch(MalformedURLException e){
    		e.printStackTrace();
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }
}