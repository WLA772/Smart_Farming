package com.project.SmartFarming.service;

import java.util.List;

import com.project.SmartFarming.entity.ShoppingCartItem;

public interface ShoppingCartItemService {

public ShoppingCartItem addTo(ShoppingCartItem shoppingCartItem);
	
	public List<ShoppingCartItem> getShoppingCartItem();
	
	public ShoppingCartItem getByShoppingCartItemId(int id);
	
	public void removeItem(int id);
	
	//public int calTotalAmt();
	
//	public int calTotalAmount(ShoppingCart shoppingCart);
	
	//public int calTotal();
	
//	public ShoppingCart getShoppingCartByUser(User user);
	
	//public ShoppingCart getShoppingCart();
	
//	public void clearShoppingCart(ShoppingCart shoppingCart);
	
	public void clearAllItems();
}
