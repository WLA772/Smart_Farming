package com.project.SmartFarming.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.project.SmartFarming.entity.Category;
import com.project.SmartFarming.entity.Knowledge;


@EnableJpaRepositories
public interface KnowledgeRepo extends JpaRepository<Knowledge, Integer>{

	public List<Knowledge> findByCategory(Category category);
	public Knowledge findByPlantName (String plantName);
    public Knowledge findById(int knowledge_id);
	
	

	
}
