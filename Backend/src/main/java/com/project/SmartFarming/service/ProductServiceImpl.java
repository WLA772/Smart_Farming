package com.project.SmartFarming.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.SmartFarming.entity.CategoryForProduct;
import com.project.SmartFarming.entity.Product;
import com.project.SmartFarming.repository.ProductRepo;

@Service
public class ProductServiceImpl implements ProductService{

	
	@Autowired
	ProductRepo productRepo;

	@Override
	public Product add(Product product) {
		return productRepo.save(product);
	}

	@Override
	public Product getById(int prod_id) {
		return productRepo.findById(prod_id).orElse(null);
	}

	@Override
	public List<Product> getAll() {
		return productRepo.findAll();
	}

	@Override
	public Product update(int prod_id,Product product) {
		Product toUpdateProduct = this.getById(prod_id);
		if(toUpdateProduct == null) {
			return null;
		}
		toUpdateProduct.setProdName(product.getProdName());
		toUpdateProduct.setBrandName(product.getBrandName());
		toUpdateProduct.setDescription(product.getDescription());
		toUpdateProduct.setUnit_price(product.getUnit_price());
		toUpdateProduct.setStatus(product.getStatus());
		toUpdateProduct.setCategory(product.getCategory());
		toUpdateProduct.setImagePath(product.getImagePath());
		productRepo.save(toUpdateProduct);
		return toUpdateProduct;
	}

	@Override
	public boolean delete(int prod_id) {
		Product product = this.getById(prod_id);
		if (product == null) {
			return false;
		}
		productRepo.deleteById(prod_id);
		return true;

}

	@Override
	public List<Product> getAllByCategoryForProduct(CategoryForProduct category) {
		return productRepo.findByCategory(category);
}

	@Override
	public Product getByName(String prod_name) {
		return productRepo.findByProdName(prod_name);
}

	@Override
	public List<Product> getByBrandName(String brandName) {
		return productRepo.findByBrandName(brandName);
}	

	
}
