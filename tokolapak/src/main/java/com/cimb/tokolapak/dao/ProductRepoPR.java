package com.cimb.tokolapak.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.tokolapak.entity.ProductPR;

public interface ProductRepoPR extends JpaRepository <ProductPR,Integer>{
	
}
