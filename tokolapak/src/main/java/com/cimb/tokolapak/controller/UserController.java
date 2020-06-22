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
import com.cimb.tokolapak.util.EmailUtil;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
	
	@Autowired
	private UserRepo userRepo;
	
	private PasswordEncoder pwEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	private EmailUtil emailUtil;
	
	@PostMapping
	public User registerUser(@RequestBody User user) {	
		Optional<User> findUser = userRepo.findByUsername(user.getUsername());
		
		
		  if(findUser.toString() != "Optional.empty") {
			  throw new RuntimeException("Username exists!");
		  }
		
		
		String encodedPassword = pwEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		emailUtil.sendEmail(user.getEmail(), "Registrasi Akun","<h1>Selamat</h1>\n "
				+ "Anda telah bergabung bersama!\n "
				+ "<a href=\"http://localhost:8080/activate?email=" + user.getEmail() + "\">KLIK DISINI</a> ini untuk verifikasi email");
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
	
	@PostMapping("/sendEmail")
	public String sendEmailTesting() {
		this.emailUtil.sendEmail("bagasyafitrapandji.nk@gmail.com","Testing Email Spring", "<h1>Hey there</h1> APA KABAR??? BAGAS");
		return "Email Sent!";
	}
}
