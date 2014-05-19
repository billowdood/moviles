package com.trucha;

public class Dish {

	private int id,category_id;
	private float price;
	private String name,description;
	
	public int getId(){
		return this.id;
	}
	
	public int getCategoryId(){
		return this.category_id;
	}
	
	public float getPrice(){
		return this.price;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getDescription(){
		return this.description;
	}
}
