
package com.project.SmartFarming.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.SmartFarming.entity.Category;
import com.project.SmartFarming.entity.Knowledge;
import com.project.SmartFarming.entity.User;
import com.project.SmartFarming.repository.KnowledgeRepo;


@Service
public class KnowledgeServiceImpl implements KnowledgeService{
	
	 private KnowledgeRepo knowledgeRepo;
	    private UserService userService;

	    public KnowledgeServiceImpl(KnowledgeRepo knowledgeRepo, UserService userService) {
	        this.knowledgeRepo = knowledgeRepo;
	        this.userService = userService;
	    }


//	@Autowired
//	KnowledgeRepo knowledgeRepo;
//	
//	@Autowired
//	UserService userService;
	
	@Override
	public List<Knowledge> getAll() {
		return knowledgeRepo.findAll();
	}

	@Override
	public Knowledge get(int knowledge_id) {
		return knowledgeRepo.findById(knowledge_id);
	}
	
//	public Knowledge getName(String plantName) {
//		return knowledgeRepo.findByName(plantName);
//	}

	@Override
	public Knowledge create(Knowledge knowledge) {
		knowledge.setCreatedAt(LocalDate.now());
		return knowledgeRepo.save(knowledge);
	}

	@Override
	public Knowledge update(int knowledge_id, Knowledge knowledge) {
		Knowledge updateKnowledge = this.get(knowledge_id);
		if(updateKnowledge == null) {
		return null;
		}
		updateKnowledge.setPlantName(knowledge.getPlantName());
		updateKnowledge.setPhoto(knowledge.getPhoto());
		updateKnowledge.setMovie(knowledge.getMovie());;
		updateKnowledge.setCategory(knowledge.getCategory());
		updateKnowledge.setAbout_detail(knowledge.getAbout_detail());
		updateKnowledge.setUpdatedAt(LocalDateTime.now());
		return knowledgeRepo.save(updateKnowledge);
	}

	@Override
	public boolean delete(int knowledge_id) {
		Knowledge deleteKnowledge = this.get(knowledge_id);
		if(deleteKnowledge == null) {
		return false;
		}
		knowledgeRepo.deleteById(knowledge_id);
		return true;
	}

	@Override
	public List<Knowledge> getAllByCategory(Category category) {
		return knowledgeRepo.findByCategory(category);
	}

	@Override
	public Knowledge getByPlantName(String plantName) {
		return knowledgeRepo.findByPlantName(plantName);
	}

	@Override
	public void likeKnowledge(int knowledge_id, int userId) {
		 User user = userService.get(userId);
	        Knowledge knowledge = knowledgeRepo.findById(knowledge_id);
	        knowledge.setLikeCount(knowledge.getLikeCount() + 1);
	        knowledgeRepo.save(knowledge);
		
	}


	@Override
	public void unlikeKnowledge(int knowledge_id, int userId) {
	
		User user = userService.get(userId);
        Knowledge knowledge = knowledgeRepo.findById(knowledge_id);
        knowledge.setLikeCount(knowledge.getLikeCount() - 1);
        knowledgeRepo.save(knowledge);
		
	}
	
	
	

	
//	@Override
//	public void incrementUserCount(int knowledge_id) {
//		 Knowledge knowledge = knowledgeRepo.findById(knowledge_id)
//	                .orElseThrow(() -> new IllegalArgumentException("Knowledge not found with ID: " + knowledge_id));
//	        knowledge.setUserLikeCountIncrease(knowledge.getUserLikeCountIncrease() + 1);
//	        knowledgeRepo.save(knowledge);	
//	}

//	@Override
//	public void decrementUserCount(int knowledge_id) {
//		Knowledge knowledge = knowledgeRepo.findById(knowledge_id)
//                .orElseThrow(() -> new IllegalArgumentException("Knowledge not found with ID: " + knowledge_id));
////        int userCountDecrease = knowledge.getUserCountDecrease();
////        if (userCountDecrease > 0) {
//            knowledge.setUserUnlikeCountDecrease(knowledge.getUserUnlikeCountDecrease() + 1);
//            knowledgeRepo.save(knowledge);
////        }
//    }
//		
	
	

//	@Override
//	public List<Knowledge> getAllbyUser(User user) {
//		// TODO Auto-generated method stub
//		return knowledgeRepo.findByUser(user);
//	}
	
	
	
	

//	@Override
//	public List<Knowledge> getAllByPlantName(String plantName) {
//		return knowledgeRepo.findByAllPlantName(plantName);
//	}
	
	
	

//	@Override
//	public Knowledge incrementLikeCount(int id, Knowledge knowledge) {
//		Knowledge knowledges = this.get(id);
//		knowledges.setLikeUnlikeCount(knowledges.getLikeUnlikeCount() + 1);
//		return knowledgeRepo.save(knowledges);
//	}
//
//	@Override
//	public Knowledge decrementLikeCount(int id, Knowledge knowledge) {
//		Knowledge knowledgess = this.get(id);
//		knowledgess.setLikeUnlikeCount(knowledgess.getLikeUnlikeCount() -1);
//		return knowledgeRepo.save(knowledgess);
//	}
//	
	

}
