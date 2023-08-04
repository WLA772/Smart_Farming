package com.project.SmartFarming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.SmartFarming.entity.CategoryForProduct;
import com.project.SmartFarming.repository.CategoryForProductRepo;

@Service
public class CategoryForProductServiceImpl implements CategoryForProductService{
	
	@Autowired
	CategoryForProductRepo categoryForProductRepo;
	
	@Override
	public List<CategoryForProduct> getAll() {
		return categoryForProductRepo.findAll();
	}

	@Override
	public CategoryForProduct getById(int cate_id) {
		return categoryForProductRepo.findById(cate_id).orElse(null);
	}

	@Override
	public CategoryForProduct create(CategoryForProduct category) {
		return categoryForProductRepo.save(category);
	}

	@Override
	public CategoryForProduct update(int cate_id, CategoryForProduct category) {
		CategoryForProduct toUpdateCategoryforproduct = this.getById(cate_id);
		if (toUpdateCategoryforproduct == null) {
			return null;
		}
		toUpdateCategoryforproduct.setCate_id(cate_id);
		toUpdateCategoryforproduct.setCate_name(category.getCate_name());
		return categoryForProductRepo.save(category);
	}

	@Override
	public boolean delete(int id) {
		CategoryForProduct categoryforproduct = this.getById(id);
		if (categoryforproduct == null) {
			return false;
		}
		categoryForProductRepo.deleteById(id);
		return true;
	}
	

	
	
}
