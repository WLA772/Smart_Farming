package com.project.SmartFarming.service;

import java.util.List;

import com.project.SmartFarming.entity.CategoryForProduct;

public interface CategoryForProductService {

	public List<CategoryForProduct> getAll();

	public CategoryForProduct getById(int cate_id);

	public CategoryForProduct create(CategoryForProduct category);

	public CategoryForProduct update(int cate_id, CategoryForProduct category);

	public boolean delete(int cate_id);
	
}
