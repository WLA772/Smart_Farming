package com.project.SmartFarming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.project.SmartFarming.entity.ShoppingCartItem;

@EnableJpaRepositories
public interface ShoppingCartItemRepo extends JpaRepository<ShoppingCartItem, Integer>{

}
