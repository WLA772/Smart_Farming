package com.project.SmartFarming.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.SmartFarming.entity.Category;
import com.project.SmartFarming.entity.Knowledge;
import com.project.SmartFarming.entity.User;
import com.project.SmartFarming.repository.KnowledgeRepo;
import com.project.SmartFarming.service.CategoryService;
import com.project.SmartFarming.service.KnowledgeService;
import com.project.SmartFarming.service.StorageService;
import com.project.SmartFarming.service.UserService;

@RestController
public class KnowledgeController {

	@Autowired
	KnowledgeService knowledgeService;

	@Autowired
	UserService userService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	StorageService storageService;
	
	@Autowired
	KnowledgeRepo knowledgeRepo;

	@GetMapping("/knowledge")
	public List<Knowledge> getKnowledges() {
		return knowledgeService.getAll();
	}
	
	
	@PostMapping("/like/{knowledge_id}/{userId}")
    public void likeKnowledge(@PathVariable int knowledge_id, @PathVariable int userId) {
        knowledgeService.likeKnowledge(knowledge_id, userId);
    }

    @PostMapping("/unlike/{knowledge_id}/{userId}")
    public void unlikeKnowledge(@PathVariable int knowledge_id, @PathVariable int userId) {
        knowledgeService.unlikeKnowledge(knowledge_id, userId);
    }

    
    
    
    
   
       

//        @GetMapping("/knowledge/likeCount/{knowledgeId}")
//        public ResponseEntity<Integer> getLikeCount(@PathVariable int knowledgeId) {
//            Knowledge knowledge = knowledgeRepo.findById(knowledgeId);
//            if (knowledge == null) {
//                return ResponseEntity.notFound().build();
//            }
//
//            return ResponseEntity.ok(knowledge.getLikeCount());
//        }
    

//	@PostMapping("/like/{knowledgeId}")
//	public ResponseEntity<?> likePost(@PathVariable int knowledgeId) {
//		knowledgeService.incrementUserCount(knowledgeId);
//		return ResponseEntity.ok().build();
//	}
//
//	@PostMapping("/unlike/{knowledgeId}")
//	public ResponseEntity<?> unlikePost(@PathVariable int knowledgeId) {
//		knowledgeService.decrementUserCount(knowledgeId);
//		return ResponseEntity.ok().build();
//	}

//	 @PostMapping("/{knowledgeId}users/{userId}")
//	    public ResponseEntity<?> addKnowledgeAnduser(@PathVariable int knowledgeId, @PathVariable int userId) {
//	        Optional<Knowledge> knowledgeOptional = knowledgeRepo.findById(knowledgeId);
//	        Optional<User> userOptional = userRepo.findById(userId);
//
//	        if (knowledgeOptional.isPresent() && userOptional.isPresent()) {
//	            Knowledge knowledge = knowledgeOptional.get();
//	            User user = userOptional.get();
//	            
//	            knowledge.getUsers().add(user);
//	            knowledgeRepository.save(knowledge);
//	            
//	            return ResponseEntity.ok().build();
//	        }
//	        
//	        return ResponseEntity.notFound().build();
//	    }

//	@GetMapping("/knowledge/{plantName}")
//	public ResponseEntity<?> getKnowledgesByPlantName(
//			@PathVariable("plantName") String plantNAME
//	){
//		Knowledge knowledgePlantName = knowledgeService.getName(plantNAME);
//		if(knowledgePlantName == null) {
//			return ResponseEntity.badRequest().body("Plant Name is invalid");
//		}
//		List<Knowledge> knowledgeAbout = knowledgeService.getByPlantName(plantNAME);
//		return ResponseEntity.ok().body(knowledgeAbout);
//	}

	@GetMapping("/knowledge/category/{category_id}")
	public ResponseEntity<?> getKnowledgesByCategory(@PathVariable("category_id") int categoryID) {
		Category category = categoryService.get(categoryID);
		if (category == null) {
			return ResponseEntity.badRequest().body("Category ID is invalid");
		}
		List<Knowledge> knowledgeList = knowledgeService.getAllByCategory(category);
		return ResponseEntity.ok().body(knowledgeList);
	}

//	@GetMapping("/knowledge/user/{Userid}")
//	public ResponseEntity<?> getKnowledgesByUser(
//			@PathVariable("Userid") int userID
//	) {
//		User user = userService.get(userID);
//		if (user == null) {
//			return ResponseEntity.badRequest().body("user ID is invalid");
//		}
//		List<Knowledge> knowledgeList = knowledgeService.getAllbyUser(user);
//		return ResponseEntity.ok().body(knowledgeList);
//	}

//	For ALL PLANT NAME

//	@GetMapping("/knowledge/allplantname/{plant_name}")
//	public ResponseEntity<?> getAllPlantName(
//			@PathVariable("plant_name") int knowledgeID
//	) {
//		Category category = categoryService.get(knowledgeID);
//		if (category == null) {
//			return ResponseEntity.badRequest().body("Category ID is invalid");
//		}
//		List<Knowledge> knowledgeList = knowledgeService.getAllByCategory(category);
//		return ResponseEntity.ok().body(knowledgeList);
//	}
//	
//	
//	
//	

	@GetMapping("/knowledge/{knowledgeid}")
	public ResponseEntity<Knowledge> getKnowledge(@PathVariable("knowledgeid") int knowledgeID) {
		Knowledge knowledge = knowledgeService.get(knowledgeID);
		if (knowledge == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(knowledge);
	}

//	FOR FAV PLANTS

//	@GetMapping("/knowledgefavname/{plantfavname}")
//	public ResponseEntity<Knowledge> getKnowledge(
//			@PathVariable("plantfavname") String knowledgeNAME
//	) {
//		Knowledge knowledge = knowledgeService.getByPlantName(knowledgeNAME);
//		if (knowledge == null) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok().body(knowledge);
//	}

//	@PostMapping("/{knowledge_id}/like")
//	public ResponseEntity<Knowledge> likeKnowledge (
//			@PathVariable int id , @Valid @RequestBody Knowledge knowledge) {
//		Knowledge knowledges = knowledgeService.incrementLikeCount( id,knowledge);
//		return ResponseEntity.ok(knowledges);
//	}
//		
//	@PostMapping("/{knowledge_id}/unlike")
//	public ResponseEntity<Knowledge> unlikeKnowledge (
//			@PathVariable int id ,  @Valid @RequestBody Knowledge knowledge) {
//		Knowledge knowledgess = knowledgeService.decrementLikeCount(id, knowledge);
//		return ResponseEntity.ok(knowledgess);
//	}

	@GetMapping("/media/{fileType}/{fileName}")
	public ResponseEntity<?> getPoster(@PathVariable("fileType") String fileType,
			@PathVariable("fileName") String fileName) throws IOException {
		MediaType contentType = MediaType.IMAGE_PNG;
		switch (fileType) {
		case "mp4":
			contentType = MediaType.APPLICATION_OCTET_STREAM;
			break;
		case "jpg":
			contentType = MediaType.IMAGE_JPEG;
			break;
		case "png":
			contentType = MediaType.IMAGE_PNG;
			break;
		default:
			return ResponseEntity.badRequest().body("Unsupported File Type");
		}
		byte[] fileBytes = storageService.load(fileName);
		if (fileBytes == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().contentType(contentType).body(fileBytes);
	}

}
