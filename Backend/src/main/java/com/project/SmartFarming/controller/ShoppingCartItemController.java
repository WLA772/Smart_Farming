package com.project.SmartFarming.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.SmartFarming.entity.CartDTO;
import com.project.SmartFarming.entity.ShoppingCartItem;
import com.project.SmartFarming.entity.ShoppingCartItemDetail;
import com.project.SmartFarming.entity.User;
import com.project.SmartFarming.repository.UserRepo;
import com.project.SmartFarming.service.ShoppingCartItemService;
import com.project.SmartFarming.service.UserService;

@RestController
@RequestMapping("/cart")
public class ShoppingCartItemController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ShoppingCartItemService shoppingCartService;
	
	@Autowired
	UserRepo userRepo;
	


	@PostMapping("/add")
	public ResponseEntity<?> addToCart(@RequestBody ShoppingCartItem shoppingCartItem) {
		// User user = userService.findByUsername(principal.getName());
		// cartService.addTo(cartItem, user);
		return ResponseEntity.ok(shoppingCartService.addTo(shoppingCartItem));
	}

	/*
	 * @PostMapping("/addTo") public ResponseEntity<?>
	 * addToShoppingCart(@RequestBody CartItem cartItem){ return
	 * ResponseEntity.ok(cartService.addToCart(cartItem)); }
	 */

	/*
	 * @GetMapping("/") public ResponseEntity<CartDetail> getCartItems(){ //User
	 * user = userService.findByUsername(principal.getName()); //List<CartDetail>
	 * cartDetailList = cartService.getCartItem(); CartDetail cartDetail =
	 * cartService.getCartItem(); return new ResponseEntity<>(cartDetail,
	 * HttpStatus.OK); }
	 */

	@DeleteMapping(value = "/remove/{id}")
	public ResponseEntity<?> removeItemFromCart(@PathVariable int id) {
		ShoppingCartItem shoppingCartItem = shoppingCartService.getByShoppingCartItemId(id);
		if (shoppingCartItem == null) {
			return ResponseEntity.notFound().build();
		}
		shoppingCartService.removeItem(id);
		//return new ResponseEntity<>(HttpStatus.OK);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/get")
	public ResponseEntity<List<ShoppingCartItemDetail>> getShoppingCartItems() {
		List<ShoppingCartItem> shoppingCartItemList = shoppingCartService.getShoppingCartItem();
		List<ShoppingCartItemDetail> shoppingCartItemDetailList = new ArrayList<ShoppingCartItemDetail>();
		for (ShoppingCartItem item : shoppingCartItemList) {
			ShoppingCartItemDetail obj = new ShoppingCartItemDetail();
			obj.setId(item.getId());
			obj.setProduct(item.getProduct());
			obj.setQuantity(item.getQuantity());
			obj.setTotal(item.getQuantity() * item.getProduct().getUnit_price());
			shoppingCartItemDetailList.add(obj);
		}
		if (shoppingCartItemDetailList.size() <= 0) {
			return new ResponseEntity<List<ShoppingCartItemDetail>>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<List<ShoppingCartItemDetail>>(shoppingCartItemDetailList, HttpStatus.OK);
		}
	}
	
	@GetMapping("/getVoc")
	public ResponseEntity <CartDTO> cartDTO() {
		List<ShoppingCartItem> shoppingCartItemList = shoppingCartService.getShoppingCartItem();
		List<CartDTO> cartDTOList = new ArrayList<CartDTO>();
		int totalAmount = 0;
		CartDTO dto = new CartDTO();
		String detail = "";
		
		for (ShoppingCartItem item : shoppingCartItemList) {
						/*
			 * dto.setId(item.getId()); dto.setProduct(item.getProduct());
			 * dto.setQuantity(item.getQuantity());
			 * dto.setTotal(item.getProduct().getUnit_price() * item.getQuantity());
			 * totalAmount += dto.getTotal(); dto.setTotalAmount(totalAmount);
			 * dto.getOrderCreateDate(); cartDTOList.add(dto);
			 */
			int total = item.getQuantity() * item.getProduct().getUnit_price();
			/*
			 * detail += item.getId() + "." + item.getProduct().getName() + "," +
			 * item.getQuantity() + "." + item.getProduct().getUnit_price() + "."+ total
			 * +"<br>";
			 */
			totalAmount += total;
			
		}
		dto.setDetail(shoppingCartItemList);
		dto.setTotalAmount(totalAmount);
		dto.setOrderCreateDate(LocalDate.now());
		 System.out.println(dto);
			return new ResponseEntity<CartDTO>(dto, HttpStatus.OK);
		
	}

	
	@DeleteMapping("/clearAllItems")
	public ResponseEntity<String> clearAllCartItems(){
		shoppingCartService.clearAllItems();
		return ResponseEntity.ok("All items deleted successfully");
	}

	
	
	
}

