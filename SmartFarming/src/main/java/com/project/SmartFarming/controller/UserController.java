package com.project.SmartFarming.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.SmartFarming.entity.Category;
import com.project.SmartFarming.entity.ChangePassword;
import com.project.SmartFarming.entity.Knowledge;
import com.project.SmartFarming.entity.LoginRequest;
import com.project.SmartFarming.entity.User;
import com.project.SmartFarming.repository.KnowledgeRepo;
import com.project.SmartFarming.repository.UserRepo;
import com.project.SmartFarming.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	private final UserRepo userRepo;
	private final KnowledgeRepo knowledgeRepo;

	public UserController(UserRepo userRepo, KnowledgeRepo knowledgeRepo) {
		this.userRepo = userRepo;
		this.knowledgeRepo = knowledgeRepo;
	}

	@PostMapping("/knowledge/{Id}/{knowledgeId}")
	public ResponseEntity<String> addUserKnowledge(@PathVariable int Id, @PathVariable int knowledgeId) {
		User user = userRepo.findById(Id).orElseThrow();

//	        System.out.println(user);

		Knowledge knowledge = knowledgeRepo.findById(knowledgeId);

//	        System.out.println(knowledge);
		user.getKnowledges().add(knowledge);
		userRepo.save(user);
		return ResponseEntity.ok("Knowledge  added to user successfully");
	}

	@GetMapping("/knowledges/{userId}")
	public List<Knowledge> getKnowledgesByUser(@PathVariable int userId) {
		Optional<User> optionalUser = userRepo.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
//			System.out.println(user.getKnowledges());
			return user.getKnowledges();
		} else {
			throw new EntityNotFoundException("user not found");
		}
	}

	@PostMapping("/login")
	public ResponseEntity<User> login(@Valid @RequestBody LoginRequest lognReq) {
		User user = userService.checkLoginUser(lognReq.getGmail(), lognReq.getPassword());
		if (user == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok().body(user);
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody User user) {
		User createdUser = userService.create(user);
		if (createdUser == null) {
			return ResponseEntity.badRequest().body("User with same gmail already exists!");
		}
		return ResponseEntity.ok().body(createdUser);
	}

	@GetMapping("/logout")
	public Boolean logout(HttpSession session) throws IOException {
		session.invalidate();
		return true;
	}

	@GetMapping("/profile")
	public ResponseEntity<User> getProfile(@RequestParam int id) {
		User user = userService.get(id);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(user);
	}

	@PostMapping("/changePwd")
	public ResponseEntity<Object> changePassword(@Valid @RequestBody ChangePassword changePwd) throws IOException {
		User user = userService.get(changePwd.getOri_id());
		if (user == null) {
			return new ResponseEntity<Object>(new Exception("Something wrong"), HttpStatus.CONFLICT);
		}
		if (!changePwd.getCon_new_pwd().equals(changePwd.getNew_pwd())) {
			return new ResponseEntity<Object>(new Exception("Confirm Password does not match"), HttpStatus.CONFLICT);
		}

		userService.updatePwd(user.getId(), changePwd.getNew_pwd());
		return ResponseEntity.ok().body(user);
	}

	@PutMapping("/profile/update")
	public ResponseEntity<User> updateProfile(@Valid @RequestBody User user) {
		if (user.getId() <= 0) {
			return ResponseEntity.badRequest().build();
		}
		User updatedUser = userService.update(user.getId(), user);
		if (updatedUser == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updatedUser);
	}
}
