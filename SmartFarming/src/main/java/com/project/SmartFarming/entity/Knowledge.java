

package com.project.SmartFarming.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

public class Knowledge {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int knowledge_id;

	@Column(length = 50, nullable = false)
	@NotBlank(message = "Required")
	private String plantName;

	@Column(length = 200)
	@NotBlank(message = "Required")
	private String photo;

	@Column(length=1000000, nullable = false)
	@NotBlank(message = "Required")
	private String about_detail;
	
	
	
	@NotBlank(message = "Required")
	private String movie;


	@Column(nullable = false)
//	@ColumnDefault("CURRENT_TIMESTAMP")
	private LocalDate createdAt;

	private LocalDateTime updatedAt;

	private int likeCount ;
	
//	private int userLikeCountIncrease;
	
//	private int userUnlikeCountDecrease;

	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
	private Category category;

	@JsonIgnore
	@ManyToMany(mappedBy = "knowledges")
	private List<User> users;

	@Override
	public String toString() {
		return "Knowledge [knowledge_id=" + knowledge_id + ", plantName=" + plantName + ", photo=" + photo
				+ ", about_detail=" + about_detail + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", category=" + category + "]";
	}

}
