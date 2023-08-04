package com.project.SmartFarming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.project.SmartFarming.entity.User;
@EnableJpaRepositories
public interface UserRepo extends JpaRepository<User,Integer>{

	public User findByGmail(String gmail);

	public User findByName(String name);
	
	
}
