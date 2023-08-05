package com.project.SmartFarming.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.SmartFarming.entity.ShoppingCartItem;
import com.project.SmartFarming.repository.ShoppingCartItemRepo;

@Service
public class ShoppingCartItemServiceImpl implements ShoppingCartItemService{
	
	@Autowired
	ShoppingCartItemRepo shoppingCartItemRepo;
	
	@Autowired
	ProductService productService;
	
	@Override
	public ShoppingCartItem addTo(ShoppingCartItem shoppingCartItem) {
		return shoppingCartItemRepo.save(shoppingCartItem);
	}


	@Override
	public List<ShoppingCartItem> getShoppingCartItem() {
		return shoppingCartItemRepo.findAll();
	}

	@Override
	public ShoppingCartItem getByShoppingCartItemId(int id) {
		return shoppingCartItemRepo.findById(id).orElse(null);
	}

	@Override
	public void removeItem(int id) {
		Optional<ShoppingCartItem> optionalShoppingCartItem = shoppingCartItemRepo.findById(id);
		if (optionalShoppingCartItem == null) {
			System.out.println("Cart item Id is valid");
		}
		ShoppingCartItem shoppingCartItem = optionalShoppingCartItem.get();
		shoppingCartItemRepo.deleteById(id);

	}
	
	/*
	 * @Override public int calTotalAmt() { List<ShoppingCartItem> shoppingCartItems
	 * = shoppingCartItemRepo.findAll(); int totalAmount = 0; for(ShoppingCartItem
	 * cartItem : shoppingCartItems) { totalAmount += cartItem.getQuantity() *
	 * cartItem.getProduct().getUnit_price(); } return 0; }
	 */

//	@Override
//	public int calTotalAmount(ShoppingCart shoppingCart) {
//		List<ShoppingCartItem> shoppingCartItems = new ArrayList<ShoppingCartItem>();
//		int totalAmount = 0;
//		if (shoppingCart != null && shoppingCart.getShoppingCartItems() != null) {
//			for (ShoppingCartItem shoppingCartItem : shoppingCart.getShoppingCartItems()) {
//				// ShoppingCart cart = new ShoppingCart();
//				totalAmount += (shoppingCartItem.getQuantity() * shoppingCartItem.getProduct().getUnit_price());
//			}
//		}
//		return totalAmount;
//	}
//
//	@Override
//	public ShoppingCart getShoppingCartByUser(User user) {
//		return shoppingCartRepo.findByUser(user);
//	}
//
//	@Override
//	public void clearShoppingCart(ShoppingCart shoppingCart) {
//		if (shoppingCart != null && shoppingCart.getShoppingCartItems() != null) {
//			shoppingCart.getShoppingCartItems().clear();
//		}
//
//	}


	@Override
	public void clearAllItems() {
		shoppingCartItemRepo.deleteAll();
		
	}

}
