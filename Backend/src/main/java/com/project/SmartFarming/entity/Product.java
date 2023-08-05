package com.project.SmartFarming.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int prod_id;
	
	@Column(length = 20, nullable = false, unique = true)
	@NotBlank(message = "Required")
	private String prodName;
	
	@Column(length = 500)
	@NotBlank(message = "Required")
	private String description;
	
	@Column(length = 20, nullable = false)
	@NotBlank(message = "Required")
	private String brandName;
	
	@Column(nullable = false)
	private int unit_price;
	
	@Column(length = 20, nullable = false)
	private String status;
	
	
	@ManyToOne
	@JoinColumn(name = "categorys_id", referencedColumnName = "cate_id", nullable = false)
	private CategoryForProduct category;
	
	@Column(length = 200)
	@NotBlank(message = "Required")
	private String imagePath;

	@Override
	public String toString() {
		return "Product [id=" + prod_id + ", name=" + prodName + ", description=" + description + ", brandName=" + brandName
				+ ", unit_price=" + unit_price + ", status=" + status + ", category=" + category + ", imagePath="
				+ imagePath + "]";
	}
	
	
	

}
