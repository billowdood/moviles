package com.trucha;

public class Order {
	
	private int id,table_id;
	private float total;
	private boolean is_payed;
	
	public Order(int id,int table_id,float total,boolean is_payed){
		this.id = id;
		this.table_id = table_id;
		this.total = total;
		this.is_payed = is_payed;
	}
	
	public int getId(){
		return this.id;
	}
	
	public int getTableId(){
		return this.table_id;
	}
	
	public float getTotal(){
		return this.total;
	}
	
	public boolean getIsPayed(){
		return this.is_payed;
	}

}
