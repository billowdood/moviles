package com.trucha.activities;

import com.trucha.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void startSettingsActivity(View v){
		Intent intent = new Intent(this,SettingsActivity.class);
		startActivity(intent);
	}
	
	public void startMenu(View view){
		// Implement validation of existence of host address
		Intent intent = new Intent(this,CategoriesActivity.class);
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds  items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
