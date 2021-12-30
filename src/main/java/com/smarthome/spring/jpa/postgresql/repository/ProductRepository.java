package com.smarthome.spring.jpa.postgresql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarthome.spring.jpa.postgresql.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	public List<Product> getProductByUserId(String userId);
	public List<Product> findByUserId(String userId); // works as well as above
}
