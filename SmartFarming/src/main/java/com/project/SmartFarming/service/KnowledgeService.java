package com.project.SmartFarming.service;

import java.util.List;

import com.project.SmartFarming.entity.Category;
import com.project.SmartFarming.entity.Knowledge;
import com.project.SmartFarming.entity.User;

public interface KnowledgeService {

	public List<Knowledge> getAll();

	public Knowledge get(int knowledge_id);

	public Knowledge create(Knowledge knowledge);

	public Knowledge update(int knowledge_id, Knowledge knowledge);

	public boolean delete(int knowledge_id);

	public List<Knowledge> getAllByCategory(Category category);

	public Knowledge getByPlantName(String plantName);
	
	
//	public void incrementUserCount(int knowledge_id);
//	
//    public void decrementUserCount(int knowledge_id);
	
	  public void likeKnowledge(int knowledge_id, int userId);
	  public void unlikeKnowledge(int knowledge_id, int userId);
	
}
