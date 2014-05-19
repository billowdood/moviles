package com.trucha;

public class DishOrder {
	
	private int id,dish_id,order_id;
	
	public DishOrder(int id,int dish_id,int order_id){
		this.id = id;
		this.dish_id = dish_id;
		this.order_id = order_id;
	}
	
	public int getId(){
		return this.id;
	}
	
	public int getDishId(){
		return this.dish_id;
	}
	
	public int getOrderId(){
		return this.order_id;
	}

}
