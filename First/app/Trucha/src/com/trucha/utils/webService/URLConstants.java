package com.trucha.utils.webService;

public class URLConstants {

	/*
	 * URL for getting all the categories
	 * Use as it is
	*/
	public static String URL_CATEGORY_INDEX = "/api/v1/categories";
	
	/*
	 *  URL for creating an Order
	 *  Use as it is
	*/
	public static String URL_CREATE_ORDER = "/api/v1/orders";
	
	/*
	 * URL for creating a DishOrder
	 * Use as it is
	 */
	public static String URL_CREATE_DISH_ORDER = "/api/v1/dish_orders";
	
	/*
	 *  URL for getting a Dish with a given :id
	 *  Must add the id to the url
	 *  ex: http://192.168.1.115/api/v1/dishes/:id
	*/
	public static String URL_DISH = "/api/v1/dishes/";
	
	/*
	 * URL for getting the dishes of the Category with the given :id--
	 * URL for getting the dishes of the category with the given :name
	 * Must add the id to the url
	 * ex: http://192.168.1.115/category/dishes/:name
	*/
	public static String URL_FOR_CATEGORY = "/api/v1/category/dishes";
	
	/*
	 * URL for getting the last DishOrder created
	 */
	public static String URL_LAST_DISH_ORDER = "/api/v1/dishOrder/last";
	
	/*
	 * URL for getting the last Order created
	 */
	public static String URL_LAST_ORDER = "/api/v1/orders/last";
	
}
