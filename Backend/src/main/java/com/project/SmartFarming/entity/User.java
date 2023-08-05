package com.project.SmartFarming.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.ColumnDefault;

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
@ToString
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(length = 30, nullable = false)
	@NotBlank(message = "Required")
	private String name;

	@Column(length = 100, nullable = false)
	@NotBlank(message = "Required")
	private String password;

	@Column(length = 100, nullable = false, unique = true)
	@NotBlank(message = "Required")
	private String gmail;

	@Column(columnDefinition = "ENUM('active', 'deactivated', 'banned', 'violated') NOT NULL")
	@Enumerated(EnumType.STRING)
	private UserStatus status;

	@Column(columnDefinition = "ENUM('admin', 'user') NOT NULL")
	@Enumerated(EnumType.STRING)
	private UserRole role;

	@Column(nullable = false)
	private LocalDate startJoinDate;

	private LocalDate lastJoinDate;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	@ManyToMany
	@JoinTable(name = "user_favpost", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "knowledge_id"))
	private List<Knowledge> knowledges;

}
