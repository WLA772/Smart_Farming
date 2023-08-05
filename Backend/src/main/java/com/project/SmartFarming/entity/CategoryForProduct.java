package com.project.SmartFarming.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryForProduct {
	
	
	@Id
	private int cate_id;

	@Column(length = 30, nullable = false)
	private String cate_name;

}
