package com.project.SmartFarming.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.SmartFarming.entity.Category;
import com.project.SmartFarming.entity.CategoryForProduct;
import com.project.SmartFarming.entity.Knowledge;
import com.project.SmartFarming.entity.Product;
import com.project.SmartFarming.entity.User;
import com.project.SmartFarming.service.CategoryForProductService;
import com.project.SmartFarming.service.CategoryService;
import com.project.SmartFarming.service.KnowledgeService;
import com.project.SmartFarming.service.ProductService;
import com.project.SmartFarming.service.StorageService;
import com.project.SmartFarming.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	CategoryService categoryService;

	@Autowired
	StorageService storageService;

	@Autowired
	KnowledgeService knowledgeService;
	
	@Autowired
	UserService userService;
	
	
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryForProductService categoryForProductService;

	
	
	@PostMapping("/knowledge/create")
	public ResponseEntity<?> createKnowledge(@Valid @RequestBody Knowledge knowledge) {
		if (knowledge.getCategory() == null) {
			return ResponseEntity.badRequest().body("Category is empty");
		}
		if (knowledge.getCategory().getId() == 0) {
			return ResponseEntity.badRequest().body("Category ID is invalid");
		}
		if (!storageService.check(knowledge.getPhoto())) {
			return ResponseEntity.badRequest().body("Photo is invalid");
		}
		return ResponseEntity.ok(knowledgeService.create(knowledge));
	}

	@PostMapping("/file/create")
	public ResponseEntity<String> createFile(@RequestParam("file") MultipartFile file,
			@RequestParam("fileType") String fileType) {
		String filePath = storageService.create(file, fileType);
		if (filePath == null) {
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok(filePath);
	}

	@PutMapping("/file/update")
	public ResponseEntity<String> updateFile(@RequestParam("file") MultipartFile file,
			@RequestParam("fileType") String fileType, @RequestParam("filePath") String filePath) {
		String newFilePath = storageService.update(file, fileType, filePath);
		if (newFilePath == null) {
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok(newFilePath);
	}

	@PutMapping("/knowledge/update/{knowledge_id}")
	public ResponseEntity<Knowledge> updateKnowledge(@PathVariable int knowledge_id,
			@Valid @RequestBody Knowledge knowledge) {
		Knowledge updatedKnowledge = knowledgeService.update(knowledge_id, knowledge);
		if (updatedKnowledge == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updatedKnowledge);
	}

	@DeleteMapping("/knowledge/delete/{knowledge_id}")
	public ResponseEntity<?> deleteKnowledge(@PathVariable int knowledge_id) {
		Knowledge knowledge = knowledgeService.get(knowledge_id);
		if (knowledge == null) {
			return ResponseEntity.notFound().build();
		}

		// Delete Knowledge
		boolean isDeleted = knowledgeService.delete(knowledge_id);
		if (!isDeleted) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		// Delete Photo
		storageService.delete(knowledge.getPhoto());
		return ResponseEntity.ok().build();
	}

	@GetMapping("/knowledge/plantName/{plantName}")
	public ResponseEntity<?> findKnowledgeByPlantName(@PathVariable("plantName") String plantName) {
		Knowledge knowledge = knowledgeService.getByPlantName(plantName);
		if (knowledge == null) {
			return ResponseEntity.ok().body(false);
		}
		return ResponseEntity.ok().body(knowledge);

	}
	
	
	
	
	
	// ------------------- User

	@GetMapping("/user")
	public List<User> listUser() {
		return userService.getAll();
	}

	@PutMapping("/user/update_status")
	public ResponseEntity<?> updateUserStatus(
			@RequestParam int id, @RequestParam String status
	) {
		User user = userService.updateStatus(id, status);
		if (user == null) {
			return ResponseEntity.badRequest()
					.body("User is invalid, Status is invalid");
		}
		return ResponseEntity.ok(user);
	}

	@GetMapping("/user_status")
	public List<String> listUserStatus() {
		return userService.getAllStatus();
	}
	
	
	
	

	// ------------------- Category

	@GetMapping("/category")
	public List<Category> listCategory() {
		return categoryService.getAll();
	}

	@PostMapping("/category")
	public Category createCategory(@RequestBody Category category) {
		return categoryService.create(category);
	}

	
	
	
	
	
	
	
	
	
	
	
	@PostMapping("/product/add")
	public ResponseEntity<?> addProduct(@Valid @RequestBody Product product){
		System.out.println("p name"+product);
		if(product.getCategory() == null) {
			return ResponseEntity.badRequest().body("Categoryforproduct is empty");
		}
		if(product.getCategory().getCate_id() == 0) {
			return ResponseEntity.badRequest().body("Categoryforproduct ID is invalid");
		}
		if(!storageService.check(product.getImagePath())) {
			return ResponseEntity.badRequest().body("Image is invalid");
		}
		return ResponseEntity.ok(productService.add(product));
	}
	
	
	@PutMapping("/product/update/{prod_id}")
	public ResponseEntity<Product>updateProduct(@PathVariable int prod_id,
			@Valid @RequestBody Product product){
		Product updatedProduct = productService.update(prod_id, product);
		if(updatedProduct == null) {
			return ResponseEntity.notFound().build();
		}
		else {
			return ResponseEntity.ok().body(updatedProduct);
		}
	}


	@DeleteMapping(value = "/product/delete/{prod_id}")
	public ResponseEntity<?> delete(@PathVariable int prod_id) {
		Product product = productService.getById(prod_id);
		if (product == null) {
			return ResponseEntity.notFound().build();
		}

		boolean isDeleted = productService.delete(prod_id);
		if (!isDeleted) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		
		storageService.delete(product.getImagePath());
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/product")
	public ResponseEntity<List<Product>>getAllProduct(){
		List<Product>products = productService.getAll();
		if(products.size() <= 0) {
			return new ResponseEntity<List<Product>>(HttpStatus.BAD_REQUEST);
		}else {
		return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
		}
		
	}
	
	@GetMapping("/product/name/{prodName}")
	public ResponseEntity<Boolean> findProductByName(
			@PathVariable("prodName") String prodName
	) {
		System.out.println("name" + prodName);
		Product product = productService.getByName(prodName);
		if (product == null) {
			return ResponseEntity.ok().body(false);
		}
		return ResponseEntity.ok().body(true);
	}
	
	@GetMapping("/product/brandName/{brandName}")
	public ResponseEntity<Boolean> findProductBybrandName(
			@PathVariable("brandName") String brandName
	) {
		Product product = productService.getByName(brandName);
		if (product == null) {
			return ResponseEntity.ok().body(false);
		}
		return ResponseEntity.ok().body(true);
	}	
	
	@GetMapping("/categoryforproduct")
	public ResponseEntity<List<CategoryForProduct>>getAll(){
		List<CategoryForProduct>categories = categoryForProductService.getAll();
		if(categories.size() <= 0) {
			return new ResponseEntity<List<CategoryForProduct>>(HttpStatus.BAD_REQUEST);
		}else {
		return new ResponseEntity<List<CategoryForProduct>>(categories,HttpStatus.OK);
		}
	}
	
	@GetMapping("/categoryforproduct/{cate_id}")
	public ResponseEntity<CategoryForProduct>getById(@PathVariable int cate_id){
		CategoryForProduct category = categoryForProductService.getById(cate_id);
		if(category == null) {
			return new ResponseEntity<CategoryForProduct>(HttpStatus.BAD_REQUEST);
		}
		else {
			return new ResponseEntity<CategoryForProduct>(category,HttpStatus.OK);
		}
	}
	
//	@PostMapping(value = "/save",consumes = MediaType.APPLICATION_JSON_VALUE,
//			produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Category>save(@RequestBody Category category){
//		Category new_category = categoryService.create(category);
//		if(new_category == null) {
//			return new ResponseEntity<Category>(HttpStatus.BAD_REQUEST);
//		}
//		else {
//			return new ResponseEntity<Category>(new_category,HttpStatus.OK);
//		}
//	}
	
	@PostMapping("/categoryforproduct")
	public CategoryForProduct createCategoryForProduct(@RequestBody CategoryForProduct category) {
		return categoryForProductService.create(category);
	}
	
}
