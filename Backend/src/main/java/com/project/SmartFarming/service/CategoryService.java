package com.project.SmartFarming.service;

import java.util.List;
import com.project.SmartFarming.entity.Category;


public interface CategoryService {

	public List<Category> getAll();

	public Category get(int id);

	public Category create(Category category);

	public Category update(int id, Category category);

	public boolean delete(int id);

}
