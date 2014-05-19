package com.trucha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import com.trucha.*;


import com.google.gson.*;


public class GsonUsage {

	public static void main(String[] args){
		//GET example
		try {
			Gson Getgson = new Gson();
			URL url = new URL("http://api.androidhive.info/contacts/");
			//URL url = new URL(URLConstants.URL_DISH_FOR_CATEGORY+"/3");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("GETAttemp", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				//JsonParser parser = new JsonParser();
				//JsonArray array = parser.parse(output).getAsJsonArray();
				//Iterator iterator = array.iterator();
				/*while(iterator.hasNext()){
					System.out.println(Getgson.fromJson(iterator.next().toString(),Dish.class).getId());
				}*/
			}
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//POST example

		/*try {			
			Gson Postgson = new Gson();
			
			URL url = new URL("http://localhost:3000/api/v1/orders");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("POSTAttempt", "application/json");
			
			Order POSTOrder = new Order(5,2,(float)20.5,false);
			String POSTjson = Postgson.toJson(POSTOrder);
			
			JsonObject orderJsonObject = new JsonObject();
			JsonParser parser = new JsonParser();
			orderJsonObject.add("order",parser.parse(POSTjson).getAsJsonObject());
			System.out.println(Postgson.toJson(orderJsonObject));
			/*OutputStream os = conn.getOutputStream();
			os.write(Postgson.toJson(orderJsonObject).getBytes("utf-8"));
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			String output;
			while((output = br.readLine()) != null){
				System.out.println(output);
			}
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

}//end class
