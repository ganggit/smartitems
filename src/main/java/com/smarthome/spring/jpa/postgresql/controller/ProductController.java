package com.smarthome.spring.jpa.postgresql.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smarthome.spring.jpa.postgresql.model.Product;
import com.smarthome.spring.jpa.postgresql.repository.ProductRepository;

@RestController
@RequestMapping("/api")
public class ProductController {
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
		Optional<Product> productData = productRepository.findById(id);

		if (productData.isPresent()) {
			return new ResponseEntity<>(productData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/product")
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		try {
			Product _product = productRepository
					.save(new Product(product.getProductName(), product.getCategory(), product.getProductCode(), product.getQuantity(), product.getPrice(),product.getBrand(), product.getLocation(),product.getUserId(), product.getImage()));
			return new ResponseEntity<>(_product, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public List<Product> getProductByUserId(String userId) {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Product> cr = cb.createQuery(Product.class);
        Root<Product> root = cr.from(Product.class);
        cr.select(root).where(cb.equal(root.get("userId"), userId));  //here you pass a class field, not a table column (in this example they are called the same)

        Query<Product> query = session.createQuery(cr);
        List<Product> result = query.getResultList();
        session.close();
        return result;
	}
	
	@PutMapping("/product/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
		Optional<Product> productData = productRepository.findById(id);

		if (productData.isPresent()) {
			Product _product = productData.get();
			_product.setProductName(product.getProductName());
			_product.setProductCode(product.getProductCode());
			_product.setDate(product.getDate());
			_product.setImage(product.getImage());
			_product.setLocation(product.getLocation());
			_product.setPrice(product.getPrice());
			_product.setBrand(product.getBrand());
			_product.setQuantity(product.getQuantity());
			
			return new ResponseEntity<>(productRepository.save(_product), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/product/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
		try {
		    productRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/product/{userId}")
	public ResponseEntity<HttpStatus> deleteProductByUserId(String userId) {
		try {
			// TO DO: add permission check
			List<Product> products = productRepository.findByUserId(userId);
			for(Product prod: products)
				productRepository.deleteById(prod.getId());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@DeleteMapping("/product")
	public ResponseEntity<HttpStatus> deleteAllProducts() {
		try {
			// TO DO: add permission check
			productRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
