package com.cimb.tokolapak.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.tokolapak.dao.UserRepo;
import com.cimb.tokolapak.entity.User;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
	
	@Autowired
	private UserRepo userRepo;
	
	private PasswordEncoder pwEncoder = new BCryptPasswordEncoder();
	
	@PostMapping
	public User registerUser(@RequestBody User user) {
		Optional<User> findUser = userRepo.findByUsername(user.getUsername());
		
		
		  if(findUser.toString() != "Optional.empty") {
			  throw new RuntimeException("Username exists!");
		  }
		
		
		String encodedPassword = pwEncoder.encode(user.getPassword());
		
		user.setPassword(encodedPassword);
		User savedUser = userRepo.save(user);
		savedUser.setPassword(null);
		
		return savedUser;
	}
	
	@PostMapping("/login")
	public User loginUser (@RequestBody User user) {
		User findUser = userRepo.findByUsername(user.getUsername()).get();
		
		if(pwEncoder.matches(user.getPassword(), findUser.getPassword())) {
			findUser.setPassword(null);
			return findUser;
		}else{
			return null;
		}
	}	
	
	@GetMapping("/login")
	public User getLoginUser(@RequestParam String username,@RequestParam String password) {
		User findUser = userRepo.findByUsername(username).get();
		
		if(pwEncoder.matches(password, findUser.getPassword())) {
			findUser.setPassword(null);
			return findUser;
		}else{
			return null;
		}
	}
}
