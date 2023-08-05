package com.project.SmartFarming.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShoppingCartItemDetail {

private int id;
	
	private int quantity;
	
	private Product product;
	
	private int total;
	
}
