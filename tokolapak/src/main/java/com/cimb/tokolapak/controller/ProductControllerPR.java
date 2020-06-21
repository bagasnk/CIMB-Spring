package com.cimb.tokolapak.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cimb.tokolapak.dao.ProductRepoPR;
import com.cimb.tokolapak.entity.ProductPR;
import com.cimb.tokolapak.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/ProductsPR")
@CrossOrigin
public class ProductControllerPR {
	
	private String uploadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\";
	
	@Autowired
	private ProductRepoPR productRepoPR;
	
	@GetMapping
	public Iterable<ProductPR> getAllProject(){
		return productRepoPR.findAll();
	}
	
	@PostMapping("/addProduct")
	public ProductPR addProduct(@RequestBody ProductPR productPR) {
		return productRepoPR.save(productPR);
	}
	
	@PostMapping
	public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("productData") String productPRString) throws JsonMappingException, JsonProcessingException {
		Date date = new Date();
		ProductPR productPR = new ObjectMapper().readValue(productPRString , ProductPR.class);
		
		String fileExtension = file.getContentType().split("/")[1];
		System.out.println(fileExtension);
		String newFileName = "PROD-" + date.getTime() + "." +  fileExtension;
		
		String fileName = StringUtils.cleanPath(newFileName);
		
		Path path = Paths.get(StringUtils.cleanPath(uploadPath)  + fileName);
		
		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/documents/download/")
				.path(fileName).toUriString();
		
		productPR.setProfilePicture(fileDownloadUri);
		productRepoPR.save(productPR);
		return fileDownloadUri;
		//return fileName + " has been uploaded!";
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteProductById(@PathVariable int id) {
		Optional<ProductPR> findProduct = productRepoPR.findById(id);

		if(findProduct.toString() == "Optional.empty")
			throw new RuntimeException("Product with id " + id + " does not exist");
		
		productRepoPR.deleteById(id);
	}
	
	@PutMapping("/{id}")
		public ProductPR updateProduct(@PathVariable int id, @RequestBody ProductPR productPR) {
		Optional<ProductPR> findProduct = productRepoPR.findById(id);
		//Optional<ProductPR> findProduct = productRepoPR.findById(productPR.getId());
		
		if(findProduct.toString() == "Optional.empty")
			throw new RuntimeException("Product with id " + productPR.getId() + " does not exist");
		
		return productRepoPR.save(productPR);
	}
}
