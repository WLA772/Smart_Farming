package com.project.SmartFarming.entity;


import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartDTO {

private int id;

private List<ShoppingCartItem> detail;

private int totalAmount;

private LocalDate orderCreateDate;
	
	
	
}
