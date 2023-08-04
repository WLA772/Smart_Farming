package com.project.SmartFarming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.SmartFarming.entity.Category;
import com.project.SmartFarming.repository.CategoryRepo;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepo categoryRepo;

	@Override
	public List<Category> getAll() {
		return categoryRepo.findAll();
	}

	@Override
	public Category get(int id) {
		return categoryRepo.findById(id).orElse(null);
	}

	@Override
	public Category create(Category category) {
		return categoryRepo.save(category);
	}

	@Override
	public Category update(int id, Category category) {
		Category updateCategory = this.get(id);
		if (updateCategory == null) {
			return null;
		}
		updateCategory.setId(id);
		updateCategory.setName(category.getName());
		return categoryRepo.save(category);

	}

	@Override
	public boolean delete(int id) {
		Category deleteCategory = this.get(id);
		if (deleteCategory == null) {
			return false;
		}
		categoryRepo.deleteById(id);
		return true;
	}
}
