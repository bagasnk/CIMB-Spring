package com.cimb.tokolapak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.tokolapak.dao.UserRepo;
import com.cimb.tokolapak.entity.User;

@RestController
public class VerifikasiController {
	
	@Autowired
	private UserRepo userRepo;
	
    @PreAuthorize("permitAll()")
    @GetMapping("/activate")
    public String activate (@RequestParam (value="email")String email){
        User user = userRepo.findByEmail(email);
        
        user.setVerified(true);			
        userRepo.save(user);
        return "Selamat anda berhasil verifikasi email, Silahkan Login ";
	}
	

}
