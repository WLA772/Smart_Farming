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
import org.springframework.web.bind.annotation.RestController;

import com.project.SmartFarming.entity.CategoryForProduct;
import com.project.SmartFarming.entity.Product;
import com.project.SmartFarming.service.CategoryForProductService;
import com.project.SmartFarming.service.ProductService;
import com.project.SmartFarming.service.StorageService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryForProductService categoryForProductService;
	
	@Autowired
	StorageService storageService;
	
	
	
	@GetMapping("/product/categoryforproduct/{categoryforproduct_id}")
	public ResponseEntity<?>getProductByCategory(@PathVariable("categoryforproduct_id") int categoryforproductID){
	CategoryForProduct category = categoryForProductService.getById(categoryforproductID);
		if(category == null) {
			return ResponseEntity.badRequest().body("Categoryforproduct ID is invalid");
		}
		List<Product> productList = productService.getAllByCategoryForProduct(category);
		return ResponseEntity.ok().body(productList);
		}
	
	
	@GetMapping("/product/{prod_id}")
	public ResponseEntity<Product>getById(@PathVariable int prod_id){
		Product product = productService.getById(prod_id);
		if(product == null) {
			return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
		}
		else {
			return new ResponseEntity<Product>(product,HttpStatus.OK);
		}
	}
	
	@GetMapping("/product")
	public ResponseEntity<List<Product>>getAll(){
		List<Product>products = productService.getAll();
		if(products.size() <= 0) {
			return new ResponseEntity<List<Product>>(HttpStatus.BAD_REQUEST);
		}else {
		return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
		}
		
	}
	
//	@PostMapping(value = "/save",consumes = MediaType.APPLICATION_JSON_VALUE,
//			produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Product>save(@RequestBody Product product){
//		Product new_product = productService.add(product);
//		if(new_product == null) {
//			return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
//		}
//		else {
//			return new ResponseEntity<Product>(new_product,HttpStatus.OK);
//		}
//	}
	
	
	@PostMapping("/product")
	public Product createProduct(@RequestBody Product product) {
		return productService.add(product);
	}
	
	
	
	@PutMapping("/product/update/{prod_id}")
	public ResponseEntity<Product>updateProduct(@PathVariable int prod_id,
			@Valid @RequestBody Product product){
		Product updatedProduct = productService.update(prod_id, product);
		if(updatedProduct == null) {
			return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
		}
		else {
			return new ResponseEntity<Product>(updatedProduct,HttpStatus.OK);
		}
	}

	@DeleteMapping("/delete{prod_id}")
	public ResponseEntity<String>delete(@PathVariable int prod_id){
		Product product = productService.getById(prod_id);
		if(product == null) {
			return new ResponseEntity<String>("Delete is not Ok",HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<String>("Delete is Ok",HttpStatus.OK);
		}
	}
	
//	@GetMapping("/media/{fileType}/{fileName}")
//	public ResponseEntity<?> getImage(
//			@PathVariable("fileType") String fileType,
//			@PathVariable("fileName") String fileName
//	) throws IOException {
//		MediaType contentType = MediaType.IMAGE_PNG;
//		switch (fileType) {
//			case "mp4" :
//				contentType = MediaType.APPLICATION_OCTET_STREAM;
//				break;
//			case "jpg" :
//				contentType = MediaType.IMAGE_JPEG;
//				break;
//			case "png" :
//				contentType = MediaType.IMAGE_PNG;
//				break;
//			default :
//				return ResponseEntity.badRequest()
//						.body("Unsupported File Type");
//		}
//		byte[] fileBytes = storageService.load(fileName);
//		if (fileBytes == null) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok().contentType(contentType).body(fileBytes);
//	}

}
