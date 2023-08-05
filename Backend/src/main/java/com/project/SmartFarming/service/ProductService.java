package com.project.SmartFarming.service;

import java.util.List;

import com.project.SmartFarming.entity.CategoryForProduct;
import com.project.SmartFarming.entity.Product;

public interface ProductService {

public Product add(Product product);
	
	public Product getById(int prod_id);
	
	public List<Product>getAll();
	
	public Product update(int prod_id,Product product);
	
	public boolean delete(int prod_id);
	
	public List<Product> getAllByCategoryForProduct(CategoryForProduct category);
	
	public Product getByName(String prod_name);
	
	public List<Product> getByBrandName(String brandName);
	
}
