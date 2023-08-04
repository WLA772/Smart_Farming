package com.project.SmartFarming.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import com.project.SmartFarming.entity.CategoryForProduct;
import com.project.SmartFarming.entity.Product;

@EnableJpaRepositories
public interface ProductRepo extends JpaRepository<Product, Integer>{

public List<Product> findByCategory(CategoryForProduct category);
	
	public Product findByProdName(String prod_name);
	
	public List<Product> findByBrandName(String brandName);
	

}
