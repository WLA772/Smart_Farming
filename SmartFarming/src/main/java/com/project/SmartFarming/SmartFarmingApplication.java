package com.project.SmartFarming;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.project.SmartFarming.entity.Category;
import com.project.SmartFarming.entity.CategoryForProduct;
import com.project.SmartFarming.entity.User;
import com.project.SmartFarming.entity.UserRole;
import com.project.SmartFarming.entity.UserStatus;
import com.project.SmartFarming.service.CategoryForProductService;
import com.project.SmartFarming.service.CategoryService;
import com.project.SmartFarming.service.StorageService;
import com.project.SmartFarming.service.UserService;

@SpringBootApplication
public class SmartFarmingApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SmartFarmingApplication.class, args);
	}
		
		@Autowired
		StorageService storageService;

		@Autowired
		CategoryService categoryService;

		@Autowired
		UserService userService;
  
		
		
		
		@Autowired
		CategoryForProductService categoryForProductService;
		
		
		@Value("${spring.jpa.hibernate.ddl-auto}")
		private String ddlMode;

		@Value("${custom.delete.files}")
		private String deleteFiles;
		
		@Override
		public void run(String... args) throws Exception {

			if (ddlMode.equals("create")) {
			
				
				userService.create(
						new User(
								1, "Admin", "1111", "admin@gmail.com",
								UserStatus.active, UserRole.admin, LocalDate.now(),
								null,  LocalDateTime.now(), null ,null
						)
				);
				
				categoryService.create(new Category(1, "Soil"));
				categoryService.create(new Category(2, "Cultivation"));
				categoryService.create(new Category(3, "Nutrition"));
				categoryService.create(new Category(4, "Diseases"));
				
				
				
				categoryForProductService.create(new CategoryForProduct(1, "Seeds and Plants"));
				categoryForProductService.create(new CategoryForProduct(2, "Pesticides"));
				categoryForProductService.create(new CategoryForProduct(3, "Fertilizers"));
				
			}
			if (deleteFiles.equals("true")) {
				storageService.clearAll();
			}
		
		}
	

}
